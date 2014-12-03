package cn.berserker.platform;

import android.app.Activity;
import cn.berserker.model.Notification;
import cn.berserker.model.ResultCode;
import cn.berserker.model.Item;
import cn.berserker.utils.Tools;

public class PaymentCenter {
	static PaymentCenter paymentCenter = null;
	ControlCenter mCenter = null;

	/*
	 *	Use this to initialize PaymentCenter.
	 *	If you has your own server to generate orderId, you may need to create appid and appkey for each one.
	 *	Notification was global callback, all message could transfer by it. You could change this arch to create 
	 *	notify callback for every payment request. 
	 *	For initialize need to cast much time, so it should be intialize asap, when you open the app. 
	 *	If you only intialize it before payment, it may spend much time, and let user feel bad...
	 */
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

	/*
	 *	User need to implement item, and platform would read it, and prepare for the payment.
	 */
	public void startPurchase(Activity activity, Item info) {
		mCenter.startPurchase(activity, info);
	}

	/*
	 * free all resource when you leave.
	 */
	public void destroy(Activity activity) {
		mCenter.onDestroy(activity, false);
		mCenter = null;
		paymentCenter = null;
	}
}
