package cn.berserker.payment;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import cn.berserker.model.AppInfo;
import cn.berserker.model.DeviceInfo;
import cn.berserker.model.Item;
import cn.berserker.model.Notification;
import cn.berserker.model.ResultCode;
import cn.berserker.model.PurchaseInfo;
import cn.berserker.model.UserInfo;
import cn.berserker.utils.Tools;
import cn.berserker.utils.WorkerThread.Worker;
import cn.berserker.platform.ControlCenter;
import cn.berserker.platform.ResourceTable;
import cn.berserker.database.DatabaseProvider;
import cn.berserker.http.HttpRequest;
import cn.berserker.http.HttpContent;
import cn.berserker.payment.mm.MMPayHelper;
import java.util.Calendar;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

/*
 *  Whole process list below:
 *
 *	1.request for unique orderId each time.
 *	2.ask payment provider to deal with payment.
 *	3.after you got the result from payment provider, you should sync the result with server.
 *	4.deal with the synced result.
 */
public class PaymentInterface {

	//record order in memory.
	HashMap<String, PurchaseInfo> purchaseData = new HashMap<String, PurchaseInfo>();
	PurchaseInfo purchaseInfo = null;
	//controlcenter contains runtime env.
	ControlCenter mCenter = null;
	//used for payment
	PaymentDialog mDialog = null;
	// database.
	DatabaseProvider mDatabase = null;
	//used for mm's payment test.
	MMPayHelper mMMPay = null;

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			if(msg.what == PaymentConfig.HANDLE_PAY_REPORT) {
				//close the face. If exist here, should be close OK.
				//I think there are only three status here. succ, wait, cancel. so just check them.
				// msg = "orderid#result"
				int resultCode = ResultCode.PAY_FAIL;
				String resultMessage = "";
				String result = (String)msg.obj;
				String[] results = result.split("#");
				final String purchaseId = results[0];
				if(results[1].equals("success")) {
					resultCode = ResultCode.PAY_SUCC;
					resultMessage = ContentMaker.getOrderSuccessMessage(purchaseData.get(results[0]));
					PurchaseInfo p = purchaseData.get(purchaseId);
					//when purchase was succ. set it's status = REPORT_USER
					p.setStatus(PurchaseInfo.REPORT_USER);
					mDatabase.saveReportPurchaseInfo(p);
				}
				else if(results[1].equals("wait")) {
					//when met this situation, it means this order's result would come out later.
					resultCode = ResultCode.PAY_WAIT;
					resultMessage = purchaseId;
					PurchaseInfo p = purchaseData.get(purchaseId);
					p.setDealTime(new Date().getTime());
					//when purcahse was wait, set it's status = REPORT_PAY.
					p.setStatus(PurchaseInfo.REPORT_PAY);
					mDatabase.saveReportPurchaseInfo(p);
					//try to request the payment status in server.
					waitReportPurchase(p, 0, 10 * 1000);
					Tools.showToast(mCenter.getRuntimeEnv(), ResourceTable.getLocaleString(mCenter.getRuntimeEnv(), ResourceTable.string.STR_PAYMENT_TOAST_PURCHASE_WAIT_TXT).replace("$$",purchaseId));
				}
				else if(results[1].equals("cancel")) {
					resultCode = ResultCode.PAY_CANCEL;
				}
				else {
					PurchaseInfo p = purchaseData.get(purchaseId);
					p.setStatus(PurchaseInfo.REPORT_SYS);
					resultMessage = purchaseId;
				}

				if(null != mDialog) {
					//close the dialog. here is safeway to close it.
					mDialog.safeDismiss();
				}

				if(null != mCenter) {
					//notify the message.
					mCenter.notifyMessage(Notification.PAY, resultCode, resultMessage);
				}
				return ;
			}
			else if(msg.what == PaymentConfig.HANDLE_ORDER_QUERY) {
				//this was the final result of the order, only succ or fail.
				int resultCode = ResultCode.PAY_FAIL;
				String resultMessage = "";
				String result = (String)msg.obj;
				String[] results = result.split("#");
				PurchaseInfo p = purchaseData.get(results[0]);
				if( null != p) {
					if(results[1].equals("success")) {
						resultCode = ResultCode.PAY_SUCC;
						resultMessage = ContentMaker.getOrderSuccessMessage(purchaseData.get(results[0]));
						p.setStatus(PurchaseInfo.REPORT_USER);
						mDatabase.updateReportPurchaseInfo(p);
					}
					else {
						resultCode = ResultCode.PAY_FAIL;
						resultMessage = results[0];
						p.setStatus(PurchaseInfo.REPORT_SYS);
						mDatabase.delReportPurchaseInfo(p);
					}
					if(null != mCenter) {
						mCenter.notifyMessage(Notification.PAY, resultCode, resultMessage);
					}
				}
				return ;
			}
			else if(msg.what == PaymentConfig.HANDLE_PAY_PROVIDE_ERROR) {
				//reset dialog pause state, cause for user cancel the payment.
				if(null != mDialog) {
					mDialog.resetPayment();
				}
				return ;
			}
			else if(msg.what == PaymentConfig.HANDLE_PAY_ALIPAY) {
				purchaseInfo.setPurchaseType(PurchaseInfo.TYPE_ALI);
			}
			else if(msg.what == PaymentConfig.HANDLE_PAY_MM) {
				purchaseInfo.setPurchaseType(PurchaseInfo.TYPE_YIDONG_MM);
			}
			purchaseData.put(purchaseInfo.getPurchaseId(), purchaseInfo);
			//the order seems successful, we need to verify it on server.
			reportPurchase(purchaseInfo.getPurchaseId());
			purchaseInfo = null;
		}
	};

	public PaymentInterface(ControlCenter center) {
		mCenter = center;
		mDatabase = new DatabaseProvider(center.getRuntimeEnv());
		loadReportPurchase();

		mMMPay = new MMPayHelper(center.getRuntimeEnv());
	}

	//load the orders in db. And deal with the unfinished status.
	private void loadReportPurchase() {
		ArrayList<PurchaseInfo> list = mDatabase.loadReportPurchaseInfo();
		if( null != list && list.size() > 0) {
			for(int i = 0 ; i < list.size(); i++) {
				final PurchaseInfo purchaseInfo = list.get(i);
				Tools.LOG("Id:" + purchaseInfo.getPurchaseId() + "\tstatus:" + purchaseInfo.getStatus());
				if(TextUtils.isEmpty(purchaseInfo.getPurchaseId())) {
					continue;
				}
				purchaseData.put(purchaseInfo.getPurchaseId(), purchaseInfo);
				final int seperate  = (i + 1) * 5 * 1000;
				if(purchaseInfo.getStatus() == PurchaseInfo.REPORT_PAY) {
					//this means the orders need to verify with the server.
					waitReportPurchase(purchaseInfo, seperate, 10 * 1000);
				}
				else if(purchaseInfo.getStatus() == PurchaseInfo.REPORT_USER) {

				}
				else if(purchaseInfo.getStatus() == PurchaseInfo.REPORT_SYS) {
					
					
				}
				else {

				}
			}
		}
	}

	public void startPurchase(Item itemInfo) {
		purchaseInfo = new PurchaseInfo(itemInfo);
		
		mDialog = new PaymentDialog(mCenter, this);
		mDialog.show();
		new Thread(new Runnable() {
			public void run() {
				boolean next = createPurchase();
				Tools.LOG("next:" + next);
				if(next) {
					//start using mm payment.
					String imsi = mCenter.getDeviceInfo().getImsi();
					//imsiType 1 means yidong. we always use mm payment as our best choice.
					if(Tools.getImsiType(mCenter.getDeviceInfo()) == Tools.IMSI_YIDONG && checkMMPurchase()) {
						//using mm payment
						mCenter.getRuntimeEnv().runOnUiThread(new Runnable() {
							@Override
							public void run() {
								startMMPurchase();
							}
						});
						return ;
					}
					//start using 3rd payment.
					mCenter.getRuntimeEnv().runOnUiThread(new Runnable(){
						@Override
						public void run() {
							if(null != mDialog) {
								mDialog.startPaymentFace();
							}
						}
					});
					return ;
				}
				else {
					//add toast to told user we couldn't get the orderid.
					mCenter.getRuntimeEnv().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Tools.showToast(mCenter.getRuntimeEnv(), ResourceTable.getLocaleString(mCenter.getRuntimeEnv(), ResourceTable.string.STR_PAYMENT_DIALOG_TOAST_ORDER_UNAVAILABLE));
						}
					});
				}
				Tools.sendHandleMessage(mHandler, PaymentConfig.HANDLE_PAY_REPORT, "0#cancel#");
			}
		}).start();
	}

	//report the purchase to server.
	private void reportPurchase(final String purchaseId) {
		final PurchaseInfo p = purchaseData.get(purchaseId);
		final String content = ContentMaker.getContent(ContentMaker.CONTENT_SYSTEM_REPORT, 
				p, mCenter.getUserInfo(), mCenter.getAppInfo(), mCenter.getDeviceInfo(), mCenter.getRuntimeEnv());
		new Thread(new Runnable() {	
			@Override
			public void run() {
				int tryCount = 0;
				do {
					String resultContent = Tools.executeHttpRequest(PaymentConfig.REPORT_ORDER,
						HttpRequest.METHOD_POST, content, HttpContent.CONTENT_TYPE_JSON);
					if(!TextUtils.isEmpty(resultContent)) {
						String status = null;
						try {
							JSONObject result = new JSONObject(resultContent);
							int code = result.getJSONObject("state").getInt("code");
							if(code == 0) {
								status = result.getJSONObject("payinfo").getString("status");
							}
						} catch (JSONException e) {
						}
						if(TextUtils.isEmpty(status)) {
							status = "error";
						}
						status = purchaseId + "#" + status + "#";
						Tools.sendHandleMessage(mHandler, PaymentConfig.HANDLE_PAY_REPORT, status);
						return ;
					}
					try {
						Thread.sleep(5 * 1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(p.getPurchaseType() == PurchaseInfo.TYPE_QCK && tryCount > 10) {
						String status = purchaseId + "#" + "wait" + "#";
						Tools.sendHandleMessage(mHandler, PaymentConfig.HANDLE_PAY_REPORT, status);
						return ;
					}
					tryCount++;
				} while(true);// && tryCount < PaymentConfig.MAX_TRY_COUNT);
			}
		}).start();
	}

	//waiting for the purchase. We had told developer that purchase need to be wait.
	private void waitReportPurchase(final PurchaseInfo pInfo, final int preSleep, final int sleepTime) {
		final String content = ContentMaker.getQueryOrderStatusContent( pInfo.getPurchaseId(), pInfo.getPurchaseType(), mCenter.getAppInfo());
		mCenter.addWorkElement(new Worker() {
			@Override
			public void onRun() {
				try {
					Thread.sleep(preSleep);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				do {
					if(sleepTime > 0) {
						try {
							Thread.sleep(sleepTime);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					String resultContent = Tools.executeHttpRequest(PaymentConfig.REQUEST_ORDER_STATUS,
						HttpRequest.METHOD_POST, content, HttpContent.CONTENT_TYPE_JSON);
					if(!TextUtils.isEmpty(resultContent)) {
						String status = null;
						try {
							JSONObject result = new JSONObject(resultContent);
							int code = result.getJSONObject("state").getInt("code");
							if(code == 0 ) {
								status = result.getJSONObject("data").getString("status");
							}
						} catch (JSONException e) {
							status = null;
						}
						if(TextUtils.isEmpty(status) || status.equals("wait")) {

						}
						else {
							status = pInfo.getPurchaseId() + "#" + status + "#";
							if(isAlive()) {
								Tools.sendHandleMessage(mHandler, PaymentConfig.HANDLE_ORDER_QUERY, status);
							}
							return ;
						}
						long now = new Date().getTime();

						long sep = now - pInfo.getDealTime();
						Tools.LOG("time ending... now: " + now + "\tdealTime:" + pInfo.getDealTime() + "\t sepTime:" + sep);
						if(sep > PaymentConfig.QCK_QUERY_SEPTIME && pInfo.getPurchaseType() == PurchaseInfo.TYPE_QCK) {
							if(isAlive()) {
								Tools.sendHandleMessage(mHandler, PaymentConfig.HANDLE_ORDER_QUERY, pInfo.getPurchaseId() + "#fail#");
							}
							return ;
						}
					}

				} while(isAlive());
			}
		});
	}

	public void onDestroy() {
		if(null != mHandler) {

		}
		mHandler = null;
		if(null != mDatabase) {
			mDatabase.close();
			mDatabase = null;
		}
	}

	protected boolean checkMMPurchase() {
		if(null != mMMPay && mMMPay.isWorkable(purchaseInfo)) {
			return true;
		}
		return false;
	}

	protected void startMMPurchase() {
		mMMPay.startPurchase(mCenter, purchaseInfo, mHandler);
	}


	// request for orderid and if sms is available, get sms content.
	private boolean createPurchase() {
		String content = ContentMaker.getContent(ContentMaker.CONTENT_ORDER_REQUEST, purchaseInfo, mCenter.getUserInfo(), mCenter.getAppInfo(), mCenter.getDeviceInfo(),mCenter.getRuntimeEnv());
		String resultContent = Tools.executeHttpRequest2(PaymentConfig.REQUEST_ORDERID,
			   	HttpRequest.METHOD_POST, content, HttpContent.CONTENT_TYPE_JSON, 15 * 1000);
		if(TextUtils.isEmpty(resultContent)) {
			return false;
		}
		try {
			JSONObject result = new JSONObject(resultContent);
			int code = result.getJSONObject("state").getInt("code");
			if(code == 0 ) {
				String value = result.getJSONObject("state").getString("orderid");
				if(!TextUtils.isEmpty(value)) {
					purchaseInfo.setPurchaseId(value);
					return true;
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}

}
