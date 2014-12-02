package cn.berserker.payment;

import android.content.Context;
import android.text.TextUtils;
import cn.berserker.model.AppInfo;
import cn.berserker.model.DeviceInfo;
import cn.berserker.model.PurchaseInfo;
import cn.berserker.model.UserInfo;
import cn.berserker.utils.MD5;
import cn.berserker.utils.Tools;
import cn.berserker.platform.PaymentPlatform;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

class ContentMaker {
	static final int CONTENT_ORDER_REQUEST = 2;
	static final int CONTENT_SYSTEM_REPORT = 3;
	static final int CONTENT_USER_REPORT = 4;

	static String getContent(int contentType, PurchaseInfo orderInfo, UserInfo userInfo
			, AppInfo appInfo, DeviceInfo deviceInfo, Context context) {
		String content = null;
		if ((contentType == CONTENT_ORDER_REQUEST) || (contentType == CONTENT_SYSTEM_REPORT ) || (contentType == CONTENT_USER_REPORT)) {
			int msgType = 0;
			//msgtype check it in document.
			switch (contentType) {
			case CONTENT_ORDER_REQUEST:
				msgType = 102;
				break;
			case CONTENT_SYSTEM_REPORT:
				msgType = 101;
				break;
			case CONTENT_USER_REPORT:
				msgType = 103;
			}
			try {
				List params = new LinkedList();
				JSONObject order = new JSONObject();
				JSONObject head = new JSONObject();
				head.put("msgtype", msgType);
				head.put("sendtime", Tools.getTime4Server());
				head.put("sdkver", PaymentPlatform.SDK_VERSION);
				order.put("msghead", head);
				JSONObject app = new JSONObject();
				app.put("chid", appInfo.getChannelId());
				params.add(new BasicNameValuePair("chid", appInfo.getChannelId()));
				app.put("appid", appInfo.getAppId());
				params.add(new BasicNameValuePair("appid", appInfo.getAppId()));
				app.put("appver", appInfo.getAppVersion());
				order.put("app", app);
				JSONObject user = new JSONObject();
				user.put("uuid", userInfo.getUuid());
				params.add(new BasicNameValuePair("uuid", userInfo.getUuid()));
				user.put("uid", userInfo.getUid());
				params.add(new BasicNameValuePair("uid", userInfo.getUid()));
				//user.put("payplugin", checkThirdPayment(context) ? 1 : 0);
				order.put("user", user);
				JSONObject device = new JSONObject();
				device.put("imei", deviceInfo.getImei());
				device.put("imsi", deviceInfo.getImsi());
				device.put("mobile", deviceInfo.getMobile());
				device.put("model", deviceInfo.getModel());
				device.put("osversion", deviceInfo.getOs());
				device.put("manufacturer", deviceInfo.getManufacturer());
				order.put("device", device);
				JSONObject data = new JSONObject();
				data.put("paytype", orderInfo.getPurchaseType());
				params.add(new BasicNameValuePair("paytype", orderInfo.getPurchaseType() + ""));
				data.put("orderid", orderInfo.getPurchaseId());
				params.add(new BasicNameValuePair("orderid", orderInfo.getPurchaseId()));
				data.put("itemid", orderInfo.getItemId());
				params.add(new BasicNameValuePair("itemid", orderInfo.getItemId() + ""));
				data.put("itemname", orderInfo.getItemSubject());
				params.add(new BasicNameValuePair("itemname", orderInfo.getItemSubject()));
				data.put("price", orderInfo.getAmount());
				params.add(new BasicNameValuePair("price", orderInfo.getAmount() + ""));
				//then user report ,it means tools was successful sent.
				order.put("data", data);
				JSONObject others = new JSONObject();
				others.put("mchid", appInfo.getMMChannelId());
				order.put("others", others);
				String signValue = makeSign(params, appInfo.getAppKey());
				order.put("sign", signValue);
				content = order.toString();
			} catch (JSONException e) {
			}
		} else {
			return null;
		}
		return content;
	}

	static String getQueryOrderStatusContent(String orderId, int payType, AppInfo appInfo) {
		List params = new LinkedList();
		String content = null;
		try {
			JSONObject order = new JSONObject();
			JSONObject head = new JSONObject();
			head.put("msgtype", 660);
			head.put("sendtime", Tools.getTime4Server());
			head.put("sdkver", PaymentPlatform.SDK_VERSION);
			order.put("msghead", head);
			JSONObject data = new JSONObject();
			data.put("orderid", orderId);
			params.add(new BasicNameValuePair("orderid", orderId));
			data.put("paytype", payType);
			params.add(new BasicNameValuePair("paytype", payType + ""));
			data.put("appid", appInfo.getAppId());
			params.add(new BasicNameValuePair("appid", appInfo.getAppId()));
			order.put("data", data);
			String signValue = makeSign(params, appInfo.getAppKey());
			order.put("sign", signValue);
			content = order.toString();
		} catch (JSONException e) {
		}
		return content;
	}

	static String getOrderSuccessMessage(PurchaseInfo info) {
		String content = "";
		if (null != info) {
			try {
				JSONObject obj = new JSONObject();
				obj.put("purchaseId", info.getPurchaseId());
				//obj.put("purchaseType", info.getPurchaseType());
				obj.put("itemId", info.getItemId());
				obj.put("itemAmount", info.getAmount());
				content = obj.toString();
			} catch (JSONException e) {
			}
		}
		return content;
	}

	//sort params first, then make string. use md5 to encrype on it.
	static String makeSign(List<BasicNameValuePair> params, String appKey) {
		Collections.sort(params, new Comparator<BasicNameValuePair>() {
			public int compare(BasicNameValuePair arg0, BasicNameValuePair arg1) {
				return arg0.getName().compareTo(arg1.getName());
			}
		});

		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < params.size(); i++) {
			if (i > 0) {
				//buf.append(" ");
				buf.append("&");
			}
			BasicNameValuePair pair = (BasicNameValuePair)params.get(i);
			buf.append(pair.getName());
			buf.append("=" + (TextUtils.isEmpty(pair.getValue()) ? "" : pair.getValue()));
		}

		//buf.append(" " + appKey);
		buf.append("&" + appKey);
		Tools.LOG("before:" + buf.toString());
		return MD5.getMD5(buf.toString());
	}
}
