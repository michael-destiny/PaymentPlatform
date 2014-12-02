package cn.berserker.payment.mm;

import android.content.Context;
import android.app.Activity;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import mm.purchasesdk.Purchase;
import mm.purchasesdk.OnPurchaseListener;
import mm.purchasesdk.PurchaseCode;

import cn.berserker.model.PurchaseInfo;
import cn.berserker.platform.ControlCenter;
import cn.berserker.utils.Tools;
import cn.berserker.payment.PaymentConfig;

import java.util.HashMap;

public class MMPayHelper {

	boolean workable = false;

	Purchase mPurchase = null;

	public MMPayHelper(Context context) {
		String mmAppId = null;
		String mmAppKey = null;
		try {
			//use getResources().getIdentifier to get resource id.
			int id = context.getResources().getIdentifier("mm_id", "string", context.getPackageName());
			int key = context.getResources().getIdentifier("mm_pwd", "string", context.getPackageName());
			mmAppId = context.getString(id);
			mmAppKey = context.getString(key);
		} catch (Exception e) {
			//if there is any problem when reading the appid and appkey, just throw the exception.
			e.printStackTrace();
			return ;
		}
		mPurchase = Purchase.getInstance();
		try {
			mPurchase.setAppInfo(mmAppId, mmAppKey);
			mPurchase.init(context, new IAPListener());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//check mm's initialize and paycode.
	public boolean isWorkable(PurchaseInfo p) {
		if(workable && !TextUtils.isEmpty(p.getMMPayCode())) {
			return true;
		}
		else {
			return false;
		}
	}

	// transmit the orderid in mm's purchase.
	public void startPurchase(ControlCenter center, PurchaseInfo p, Handler handler) {
		mPurchase.order(center.getRuntimeEnv(), p.getMMPayCode(), 1, "orderid=" + p.getPurchaseId(), false, new IAPListener(handler));
	}

	//use for test.
	public void startPurchase4Test(Activity activity, String payCode) {
		if(workable) {
			mPurchase.order(activity, payCode, 1, "", false, new IAPListener());
		}
		else {
			Log.i("MICHAEL","mmpayment haven't been intialized successfully. so can't work");
		}
	}

	class IAPListener implements OnPurchaseListener {
		Handler mHandler = null;
		//if isDebug = true, it was in test mode.
		boolean isDebug = false;
		IAPListener() {
			isDebug = true;	
		}

		IAPListener(Handler handler) {
			mHandler = handler;
			isDebug = false;
		}

		@Override
		public void onAfterApply() {

		}

		@Override
		public void onAfterDownload() {

		}

		@Override
		public void onBeforeApply() {

		}

		@Override
		public void onBeforeDownload() {

		}

		@Override
		public void onInitFinish(int code) {
			if(code == 100) {
				workable = true;
			}
			Tools.LOG("MM Purchase: onInitFinish:" + code);
		}

		@Override
		public void onBillingFinish(int code, HashMap arg1) {
			Tools.LOG("MM Purchase: onBillingFinish:" + code);
			if (isDebug) {
				Log.i("MICHAEL", "Test program exit.");
				return ;
			}
			if((code == PurchaseCode.ORDER_OK) || (code == PurchaseCode.AUTH_OK)) {
				Tools.sendHandleMessage(mHandler, PaymentConfig.HANDLE_PAY_MM, "");
			}
			else {
				Tools.sendHandleMessage(mHandler, PaymentConfig.HANDLE_PAY_PROVIDE_ERROR, "");
			}
		}

		@Override
		public void onQueryFinish(int code, HashMap arg1) {

		}

		@Override
		public void onUnsubscribeFinish(int code) {

		}
	}
}
