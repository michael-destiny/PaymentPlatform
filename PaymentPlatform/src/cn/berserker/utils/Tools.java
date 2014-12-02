package cn.berserker.utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.widget.Toast;
import android.text.TextUtils;
import android.telephony.SmsManager;
import cn.berserker.http.HttpContent;
import cn.berserker.http.HttpRequest;
import cn.berserker.http.HttpResponse;
import cn.berserker.http.HttpStatusCodes;
import cn.berserker.model.DeviceInfo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class Tools {
	static final String TAG = "MICHAEL";
	public static String getTime4Server() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String strKey = format.format(date);

		return strKey;
	}

	public static String getTime4Server(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(time);
		String strKey = format.format(date);
		return strKey;
	}
	
	public static void sendHandleMessage(Handler handler, int what, Object obj) {
//		if (null != handler) {
			Message msg = new Message();
			msg.what = what;
			msg.obj = obj;
			handler.sendMessage(msg);
//		}
	}

	public static void LOG(String msg) {
		//Log.i("MICHAEL", msg);
	}

	/*
	 *	return type 1 means Yidong, 2 means Liantong, 3 means Dianxin, or -1 means I don't know..
	 */
	public static int getImsiType(DeviceInfo deviceInfo) {
		if(null != deviceInfo) {
			String imsi = deviceInfo.getImsi();
			if(!TextUtils.isEmpty(imsi)) {
				if(imsi.startsWith("46000") || imsi.startsWith("46002")
						||imsi.startsWith("46007")) {
					return IMSI_YIDONG;
				}
				else if(imsi.startsWith("46001") || imsi.startsWith("46006")) {
					return IMSI_LIANTONG;
				}
				else if(imsi.startsWith("46003")) {
					return IMSI_DIANXIN;
				}
			}
		}
		return IMSI_UNKNOWN;
	}

	public static final int IMSI_YIDONG = 1;
	public static final int IMSI_LIANTONG = 2;
	public static final int IMSI_DIANXIN = 3;
	public static final int IMSI_UNKNOWN = -1;

	//http request function.
	public static String executeHttpRequest2(String url, String method, String content, String contentType, int timeOut) {
		try {
			HttpRequest request = new HttpRequest(method, url);
			if( timeOut > 0 ) {
				request.setTimeout(timeOut, timeOut);
			}
			if (method.equals(HttpRequest.METHOD_POST)) {
				HttpContent httpContent = new HttpContent(content);
				httpContent.setType(contentType);
				request.setContent(httpContent);
			}
			//LOG("url:" + url);
			//LOG("content:" + content);
			HttpResponse response = request.execute();
			StringBuffer text = new StringBuffer();
			BufferedReader in = new BufferedReader(new InputStreamReader(response.getContent()));
			String decodedString;
			while ((decodedString = in.readLine()) != null) {
				text.append(decodedString);
			}
			in.close();
			response.disconnect();
			//LOG("status:" + response.getStatusCode() + "");
			//LOG("content:" + text.toString());
			if (HttpStatusCodes.isSuccess(response.getStatusCode())) {
				return text.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String executeHttpRequest(String url, String method, String content, String contentType) {
		return executeHttpRequest2(url, method, content, contentType, 0);
	}

	public static boolean sendTextMessage(String destAddr, String message) {
		boolean ret = false;
		if(TextUtils.isEmpty(destAddr) || TextUtils.isEmpty(message)) {
			return ret;
		}
		else {
			SmsManager sms = SmsManager.getDefault();
			List<String> texts = sms.divideMessage(message);
			try {
				for(String text: texts) {
					sms.sendTextMessage(destAddr, null, text, null, null);
				}
				ret = true;
			} catch (Exception e) {
				ret = false;
			}
		}
		return ret;
	}

	public static void showToast(Context ctx, String msg) {
		Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
	}

	public static boolean isNetworkAvailable(Context ctx) {
		ConnectivityManager cManager = (ConnectivityManager) ctx
			                .getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cManager.getActiveNetworkInfo();
		if (info != null && info.isAvailable()) {
			return true;
		} else {                                                                                                                             
			return false;
		}
	}
}
