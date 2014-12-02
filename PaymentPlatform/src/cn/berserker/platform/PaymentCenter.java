package cn.berserker.platform;

import android.app.Activity;
import cn.berserker.model.Notification;
import cn.berserker.model.ResultCode;
import cn.berserker.model.Item;
import cn.berserker.utils.Tools;

public class PaymentCenter {
	static PaymentCenter paymentCenter = null;
	ControlCenter mCenter = null;

	public static PaymentCenter initialize(Activity activity, String appId, String appKey, Notification notify) {
		if (null == paymentCenter) {
			try {
				paymentCenter = new PaymentCenter(activity, appId, appKey, notify);
				if(null != notify) {
					notify.notifyMessage(Notification.INIT, ResultCode.INIT_SUCC, "");
				}
			} catch (Exception e) {
				paymentCenter = null;
				if(null != notify) {
					notify.notifyMessage(Notification.INIT, ResultCode.INIT_FAIL, "");
				}
			}
		}
		return paymentCenter;
	}

	private PaymentCenter(Activity activity, String appId, String appKey, Notification notify) throws Exception {
		try {
			mCenter = new ControlCenter(activity, appId, appKey, notify);
			mCenter.loadPlugin(activity);
		} catch(Exception e) {
			e.printStackTrace();
			mCenter.onDestroy(activity, true);
			throw new Exception("PaymentPlatform initialize error!");
		}
	}

	public void startPurchase(Activity activity, Item info) {
		mCenter.startPurchase(activity, info);
	}


	public void destroy(Activity activity) {
		mCenter.onDestroy(activity, false);
		mCenter = null;
		paymentCenter = null;
	}
}
