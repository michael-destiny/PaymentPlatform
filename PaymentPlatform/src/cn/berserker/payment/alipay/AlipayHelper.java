package cn.berserker.payment.alipay;

import android.app.Activity;
import android.os.Handler;
import android.text.TextUtils;

import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.berserker.model.PurchaseInfo;
import cn.berserker.payment.PaymentConfig;
import cn.berserker.utils.Tools;

import com.alipay.android.app.sdk.AliPay;
import com.alipay.android.app.lib.ResourceMap;

public class AlipayHelper {
	//default's partner's id.
	private static final String PARTNER = "";
	//default's seller's id.
	private static final String SELLER = "";
	//default's rsa_private.
	private static final String RSA_PRIVATE = "";

	//notify url, check it in document.
	private static final String NOTIFY_URL = "http://.../notify/notify4ali/notify_url.php";

	private AliPay alipay = null;

	//check it on alipay's sdk for detail.
	public static String getPurchaseInfo(PurchaseInfo p) {
		StringBuilder sb = new StringBuilder();
		sb.append("partner=\"");
		sb.append(PARTNER);
		sb.append("\"&seller_id=\"");
		sb.append(SELLER);
		sb.append("\"&out_trade_no=\"");
		sb.append(p.getPurchaseId());
		sb.append("\"&subject=\"");
		sb.append(p.getItemSubject());
		sb.append("\"&body=\"");
		sb.append(p.getItemDesc());
		sb.append("\"&total_fee=\"");
		sb.append(p.getAmount() / 100.0f);
		sb.append("\"&notify_url=\"");
		sb.append(URLEncoder.encode(NOTIFY_URL));
		sb.append("\"&service=\"mobile.securitypay.pay");
		sb.append("\"&payment_type=\"1");
		sb.append("\"&_input_charset=\"utf-8");
		sb.append("\"");

		String sign = getSign(sb.toString());
		sign = URLEncoder.encode(sign);
		sb.append("&sign=\"" + sign + "\"&" + "sign_type=\"RSA\"");

		return sb.toString();
	}

	private static String getSign(String content) {
		return Rsa.sign(content, RSA_PRIVATE);
	}

	public static void startPurchase(PurchaseInfo p, final Activity activity, final Handler handler) {
		final String orderInfo = getPurchaseInfo(p);

		new Thread(new Runnable() {
			@Override
			public void run() {
				ResourceMap.activity = activity;
				AliPay alipay = new AliPay(activity, handler);
				Tools.LOG("alipay::" + orderInfo);
				String result = alipay.pay(orderInfo);
				Pattern p = Pattern.compile("resultStatus=\\{(\\d+)\\}");
				Matcher m = p.matcher(result);
				String value = null;
				if (m.find()) {
					value = m.group(1);
					//succ flag.
					if(value.equals("9000")) {
						Tools.sendHandleMessage(handler, PaymentConfig.HANDLE_PAY_ALIPAY, "");
						return ;
					}
				}
				Tools.sendHandleMessage(handler, PaymentConfig.HANDLE_PAY_PROVIDE_ERROR, "");
				ResourceMap.activity = null;
			}
		}).start();
	}

	public static boolean checkAliExist() {
		return true;
	}
}
