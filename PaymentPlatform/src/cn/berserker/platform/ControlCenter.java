package cn.berserker.platform;

import java.util.HashMap;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import org.json.JSONObject;
import org.json.JSONException;

import cn.berserker.model.AppInfo;
import cn.berserker.model.DeviceInfo;
import cn.berserker.model.UserInfo;
import cn.berserker.model.Item;

import cn.berserker.model.Notification;
import cn.berserker.model.ResultCode;

import cn.berserker.http.HttpRequest;
import cn.berserker.http.HttpContent;

import cn.berserker.utils.WorkerThread;
import cn.berserker.utils.Tools;
import cn.berserker.payment.PaymentInterface;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.graphics.Bitmap;

import android.view.WindowManager;
import android.widget.ImageView;
import android.content.Context;
import android.content.res.Configuration;
import android.view.Gravity;

// main controller in PaymentPlatform. All things proceed through by this.
public class ControlCenter {

	private AppInfo appInfo = null;
	private DeviceInfo deviceInfo = null;
	private AccountManager accMgr = null;

	Notification mClientListener = null;
	WorkerThread mWorkFlow = null;
	PaymentInterface mPayment = null;
	Activity mEnv;

	//michael add 2014-08-18 add loadingview 
	ImageView loadingView = null;
	WindowManager mWm = null;
	Bitmap loadingImg = null;
	//michael add end

	ControlCenter(Activity activity, String appId, String appKey, Notification notify) {
		mEnv = activity;
		appInfo = new AppInfo(activity, appId, appKey);
		deviceInfo = new DeviceInfo(activity);
		accMgr = new AccountManager(this);
		mClientListener = notify;
		mWorkFlow = new WorkerThread();
		mWorkFlow.start();
	}

	//michael add 2014-08-18 add loadingview 
	void showLoadingView(Activity activity) {
		String loadingResource = null;
		Configuration config = activity.getResources().getConfiguration();
		if ( config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			loadingResource = ResourceTable.drawable.mm_loading_view_land;
		}
		else {
			loadingResource = ResourceTable.drawable.mm_loading_view_port;
		}
		mWm = (WindowManager)activity.getSystemService(Context.WINDOW_SERVICE);
		WindowManager.LayoutParams mWindowParams = new WindowManager.LayoutParams();
		mWindowParams.gravity = Gravity.TOP | Gravity.LEFT;
		mWindowParams.height = WindowManager.LayoutParams.MATCH_PARENT;
		mWindowParams.width = WindowManager.LayoutParams.MATCH_PARENT;

		mWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|
			WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE|
			WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
			WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN|
			WindowManager.LayoutParams.FLAG_FULLSCREEN;
		
		loadingView = new ImageView(activity);
		loadingView.setScaleType(ImageView.ScaleType.FIT_XY);

		//check customized picture first. If no pic, just use default.
		//default flash picture path: /res/drawable-hdpi/payment_customize_flash.png
		loadingImg = ImageCache.getJarBitmap(activity, "payment_customize_flash.png");
		if(null == loadingImg) {
			loadingImg = ImageCache.getJarBitmap(activity, loadingResource);
		}
		loadingView.setImageBitmap(loadingImg);
		mWm.addView(loadingView, mWindowParams);
		
		loadingView.postDelayed(new Runnable(){
			@Override
			public void run() {
				hideLoadingView();
			}
		}, 2 * 1000);
	}

	void hideLoadingView() {
		if( null != mWm && null != loadingView) {
			mWm.removeView(loadingView);
			if( null != loadingImg) {
				loadingImg.recycle();
				loadingImg = null;
			}
			loadingView = null;
			mWm = null;
		}
	}
	//michael add end

	// just loading the payment.
	void loadPlugin(final Activity activity) {
		mPayment = new PaymentInterface(this);

	}

	public void addWorkElement(WorkerThread.Worker e) {
		if(null != mWorkFlow) {
			e.setContext(mEnv.getApplicationContext());
			mWorkFlow.addElement(e);
		}
	}

	public AppInfo getAppInfo() {
		return appInfo;
	}

	public DeviceInfo getDeviceInfo() {
		return deviceInfo;
	}

	public UserInfo getUserInfo() {
		return accMgr.getUserInfo();
	}

	void startPurchase(Activity activity, Item info) {
		//check network
		if(!Tools.isNetworkAvailable(activity)) {
			Tools.showToast(activity, ResourceTable.getLocaleString(activity, ResourceTable.string.STR_PAYMENT_DIALOG_TOAST_NETWORK_UNAVAILABLE));
			notifyMessage(Notification.PAY, ResultCode.PAY_CANCEL, "");
			return ;
		}

		//check itemInfo value.
		if(info == null) {
			Tools.showToast(activity, ResourceTable.getLocaleString(activity, ResourceTable.string.STR_PAYMENT_DIALOG_TOAST_ITEM_NULL));
			notifyMessage(Notification.PAY, ResultCode.PAY_CANCEL, "");
			return ;
		}

		if(info.getAmount() < 1 || info.getAmount() > 500 * 100) {
			Tools.showToast(activity, ResourceTable.getLocaleString(activity, ResourceTable.string.STR_PAYMENT_DIALOG_TOAST_INVALID_AMOUNT));
			notifyMessage(Notification.PAY, ResultCode.PAY_CANCEL, "");
			return ;
		}

		if (TextUtils.isEmpty(info.getItemSubject()) || TextUtils.isEmpty(info.getDescription())) {
			Tools.showToast(activity, ResourceTable.getLocaleString(activity, ResourceTable.string.STR_PAYMENT_DIALOG_TOAST_ITEM_CONTENT_EMPTY));
			notifyMessage(Notification.PAY, ResultCode.PAY_CANCEL, "");
			return ;
		}

		mEnv = activity;
		mPayment.startPurchase(info);
	}

	void onDestroy(Activity activity, boolean isSystem) {
		//michael add on 2014.08.18 for mm loading view.
		if (null != loadingView) {
			WindowManager wm = (WindowManager)activity.getSystemService(Context.WINDOW_SERVICE);
			wm.removeView(loadingView);
			loadingView = null;
			if( null != loadingImg) {
				loadingImg.recycle();
				loadingImg = null;
			}
		}
		//destroy the job collection.
		if(null != mWorkFlow) {
			mWorkFlow.destroy();
			mWorkFlow = null;
		}
		if( null != mPayment) {
			mPayment.onDestroy();
		}
		mEnv = null;
	}

	public Activity getRuntimeEnv() {
		return mEnv;
	}

	public void notifyMessage(int resultType, int resultCode, String msg) {
		if(null != mClientListener) {
			mClientListener.notifyMessage(resultType,resultCode, msg);
		}
	}
}
