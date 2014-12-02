package cn.berserker.platform;
/*
 *	This is really ugly...and just lazy to change...
 *	We just jar all resource files into a jar file, it would extract in apk's file set.
 *	It would convenice for user to integrate..but unconvience for developer, I think we would 
 *	find a easy way to automatic package them and create resource index file..
 */

import java.util.HashMap;
import android.content.Context;

public final class ResourceTable {
	//save all resources here.

	static HashMap<Integer, String> strMap = new HashMap<Integer, String>();

	public static final class drawable {
		public static String payment_dialog_close = "payment_dialog_close.9.png";
		public static String payment_dialog_content_bg = "payment_dialog_content_bg.9.png";
		public static String payment_dialog_content_bg_land = "payment_dialog_content_bg_land.9.png";
		public static String payment_dialog_quickbill_bg_land = "payment_dialog_quickbill_bg_land.9.png";
		public static String payment_dialog_default_item = "payment_dialog_default_item.png";
		public static String payment_dialog_direct_icon = "payment_dialog_direct_icon.9.png";
		public static String payment_dialog_list_item_hover = "payment_dialog_list_item_hover.9.png";
		public static String payment_dialog_list_item_normal = "payment_dialog_list_item_normal.9.png";
		public static String payment_dialog_logo = "payment_dialog_logo.9.png";
		public static String payment_dialog_title_bg = "payment_dialog_title_bg.9.png";
		public static String payment_dialog_title_bg_land = "payment_dialog_title_bg_land.9.png";
		public static String payment_dialog_pay_alipay = "payment_dialog_pay_alipay.png";
		public static String payment_dialog_pay_tenpay = "payment_dialog_pay_tenpay.png";
		public static String payment_dialog_pay_yidong = "payment_dialog_pay_yidong.png";
		public static String payment_dialog_pay_liantong = "payment_dialog_pay_liantong.png";
		public static String payment_dialog_pay_dianxin = "payment_dialog_pay_dianxin.png";
		public static String payment_dialog_pay_mm = "payment_dialog_pay_mm.png";
		public static String payment_dialog_pay_prepaycard = "payment_dialog_pay_prepaycard.png";
		public static String payment_btn_submit = "payment_btn_submit.9.png";
		public static String payment_spinner_dropdown = "payment_dropdown_normal.9.png";
		//michael add on 2014-08-18 for mm loading view.
		public static String mm_loading_view_port = "mm_loadingview_port.jpg";
		public static String mm_loading_view_land = "mm_loadingview_land.jpg";
		//michael add end
	}

	public static final class string {

		public static final int STR_PAYMENT_DIALOG_PAYLAYOUT_TYPE_TXT =							0x00020001;
		public static final int STR_PAYMENT_DIALOG_PHONELAYOUT_TXT =							0x00030001;
		public static final int STR_PAYMENT_DIALOG_TOAST_PAYLOCK_TXT =							0x00040001;

		public static final int STR_PAYMENT_DIALOG_TOAST_INVALID_AMOUNT =						0x00050001;
		public static final int STR_PAYMENT_DIALOG_TOAST_NETWORK_UNAVAILABLE =					0x00050002;
		public static final int STR_PAYMENT_DIALOG_TOAST_ITEM_NULL =							0x00050003;
		public static final int STR_PAYMENT_DIALOG_TOAST_ITEM_CONTENT_EMPTY =					0x00050004;
		public static final int STR_PAYMENT_DIALOG_TOAST_MM_UNAVAILABLE =						0x00050005;
		public static final int STR_PAYMENT_DIALOG_TOAST_ORDER_UNAVAILABLE =					0x00050006;

		public static final int STR_PAYMENT_DIALOG_QUICKBILL_TIP_TXT =							0x00060001;
		public static final int STR_PAYMENT_DIALOG_QUICKBILL_TYPE_TXT =							0x00060002;
		public static final int STR_PAYMENT_DIALOG_QUICKBILL_VALUE_TXT =						0x00060003;
		public static final int STR_PAYMENT_DIALOG_QUICKBILL_CARDNUM_TXT =						0x00060004;
		public static final int STR_PAYMENT_DIALOG_QUICKBILL_CARDPSW_TXT =						0x00060005;
		public static final int STR_PAYMENT_DIALOG_QUICKBILL_SUBMIT_TXT =						0x00060006;

		public static final int STR_PAYMENT_TOAST_PURCHASE_WAIT_TXT =							0x00070001;
		public static final int STR_PAYMENT_TOAST_QUICKBILL_VALUE_LESS =						0x00070002;
		public static final int STR_PAYMENT_TOAST_QUICKBILL_FORM_INVALID =						0x00070003;

		public static final int STR_PAYMENT_DIALOG_SMSPAY_TIP_TXT =								0x00080001;

		public static final int STR_ACCOUNT_DIALOG_USERNAME_TXT =								0x000a0001;
		public static final int STR_ACCOUNT_DIALOG_PASSWORD_TXT =								0x000a0002;
		public static final int STR_ACCOUNT_DIALOG_MOBILE_TXT =									0x000a0003;
		public static final int STR_ACCOUNT_DIALOG_BTN_SUBMIT_TXT =								0x000a0004;
		public static final int STR_ACCOUNT_DIALOG_BTN_LOGIN_TXT =								0x000a0005;
		public static final int STR_ACCOUNT_DIALOG_BTN_REGISTER_TXT =							0x000a0006;
		public static final int STR_ACCOUNT_DIALOG_LOGIN_SUBJECT_TXT =							0x000a0007;
		public static final int STR_ACCOUNT_DIALOG_REGISTER_SUBJECT_TXT =						0x000a0008;

		public static final int STR_ACCOUNT_TOAST_USERNAME_INVALID_TXT =						0x000b0001;
		public static final int STR_ACCOUNT_TOAST_PASSWORD_INVALID_TXT =						0x000b0002;
		public static final int STR_ACCOUNT_TOAST_MOBILE_INVALID_TXT =							0x000b0003;

		public static final int STR_SYSTEM_TOAST_NETWORK_UNAVAILABLE_TXT =						0x000f0000;
		public static final int STR_SYSTEM_TOAST_USERNAME_PASSWORD_INVALID_TXT =				0x000f0001;
		public static final int STR_SYSTEM_TOAST_USERNAME_EXISTED_TXT =							0x000f0002;

		private static final int STR_MASK =														0x10000000;




	}

	public static final class id {

		public static final int ID_DIALOG_EXIT =						0x00010006 ;
		public static final int ID_DIALOG_ITEM_THUMB =					0x00010007 ;
		public static final int ID_DIALOG_ITEM_SUBJECT =				0x00010008 ;
		public static final int ID_DIALOG_ITEM_PRICE =					0x00010009 ;
		public static final int ID_DIALOG_ITEM_DESC =					0x0001000a ;
		public static final int ID_DIALOG_PAY_SMSPAY =					0x00010001 ; 
		public static final int ID_DIALOG_PAY_ALIPAY =					0x00010002 ;
		public static final int ID_DIALOG_PAY_TENPAY =					0x00010003 ;
		public static final int ID_DIALOG_PAY_QCKPAY =					0x00010004 ;
		public static final int ID_DIALOG_PAY_MMPAY =					0x00010005 ;


		public static final int ID_QUICKBILL_PAY_TIP_LAYOUT =			0x00020101 ;
		public static final int ID_QUICKBILL_PAY_TIP_TXT =				0x00020102 ;
		public static final int ID_QUICKBILL_PAY_TYPE_LAYOUT =			0x00020201 ;
		public static final int ID_QUICKBILL_TYPE_SPINNER_TXT	=		0x00020202 ;
		public static final int ID_QUICKBILL_TYPE_SPINNER	=			0x00020203 ;
		public static final int ID_QUICKBILL_VALUE_LAYOUT =				0x00020301 ;
		public static final int ID_QUICKBILL_VALUE_SPINNER_TXT  =		0x00020302 ;
		public static final int ID_QUICKBILL_VALUE_SPINNER  =			0x00020303 ;
		public static final int ID_QUICKBILL_CARDNUM_LAYOUT =			0x00020401 ;
		public static final int ID_QUICKBILL_CARDNUM_INPUT_TXT  =		0x00020402 ;
		public static final int ID_QUICKBILL_CARDNUM_INPUT  =			0x00020403 ;
		public static final int ID_QUICKBILL_CARDPSW_LAYOUT =			0x00020501 ;
		public static final int ID_QUICKBILL_CARDPSW_INPUT_TXT  =		0x00020502 ;
		public static final int ID_QUICKBILL_CARDPSW_INPUT  =			0x00020503 ;
		public static final int ID_QUICKBILL_SUBMIT			=			0x00020601 ;
		public static final int ID_QUICKBILL_EXIT	=					0x00020701 ;
		public static final int ID_QUICKBILL_WAIT_LAYOUT =				0x00020801 ;

		public static final int ID_LOGIN_LAYOUT	=						0x00030001 ;
		public static final int ID_LOGIN_SUBJECT_AREA =					0x00030002 ;
		public static final int ID_LOGIN_INPUT_AREA =					0x00030003 ;
		public static final int ID_LOGIN_LAYOUT_USERNAME =				0x00030004 ;
		public static final int ID_LOGIN_LAYOUT_PASSWORD =				0x00030005 ;
		public static final int ID_LOGIN_SUBMIT_AREA =					0x00030006 ;
		public static final int ID_LOGIN_LAYOUT_SUBMIT =				0x00030007 ;
		public static final int ID_LOGIN_LAYOUT_REGISTER =				0x00030008 ;
		public static final int ID_LOGIN_LAYOUT_EXIT =					0x00030009 ;

		public static final int ID_REGISTER_LAYOUT =					0x00040001 ;
		public static final int ID_REGISTER_SUBJECT_AREA =				0x00040002 ;
		public static final int ID_REGISTER_INPUT_AREA =				0x00040003 ;
		public static final int ID_REGISTER_LAYOUT_USERNAME =			0x00040004 ;
		public static final int ID_REGISTER_LAYOUT_PASSWORD =			0x00040005 ;
		public static final int ID_REGISTER_LAYOUT_MOBILE =				0x00040006 ;
		public static final int ID_REGISTER_LAYOUT_LOGIN =				0x00040007 ;
		public static final int ID_REGISTER_LAYOUT_SUBMIT =				0x00040008 ;
		public static final int ID_REGISTER_LAYOUT_EXIT =				0x00040009 ;

		public static final int ID_WAIT_LAYOUT =						0x00050001 ;
	}

	public static final class dimen {
		public static final int btn_width = 11;
		public static final int btn_height = 22;
	}

	static {
		//English
		strMap.put(string.STR_PAYMENT_DIALOG_PAYLAYOUT_TYPE_TXT, "Pay Type:");
		strMap.put(string.STR_PAYMENT_DIALOG_PHONELAYOUT_TXT, "Service Number:021-51758646");
		strMap.put(string.STR_PAYMENT_DIALOG_TOAST_PAYLOCK_TXT, "An purchase is in proceed..");
		strMap.put(string.STR_PAYMENT_DIALOG_QUICKBILL_TIP_TXT, "Pay type:");
		strMap.put(string.STR_PAYMENT_DIALOG_QUICKBILL_TYPE_TXT, "Type:");
		strMap.put(string.STR_PAYMENT_DIALOG_QUICKBILL_VALUE_TXT, "Value:");
		strMap.put(string.STR_PAYMENT_DIALOG_QUICKBILL_CARDNUM_TXT, "Num:");
		strMap.put(string.STR_PAYMENT_DIALOG_QUICKBILL_CARDPSW_TXT, "Psw:");
		strMap.put(string.STR_PAYMENT_DIALOG_QUICKBILL_SUBMIT_TXT, "Submit");
		strMap.put(string.STR_PAYMENT_DIALOG_SMSPAY_TIP_TXT, "Start Using Telco payment now.");
		strMap.put(string.STR_PAYMENT_TOAST_PURCHASE_WAIT_TXT, "Purchase $$ is in sync. We will inform you result later...");
		strMap.put(string.STR_PAYMENT_TOAST_QUICKBILL_VALUE_LESS,"The card was not afford to pay..");
		strMap.put(string.STR_PAYMENT_TOAST_QUICKBILL_FORM_INVALID, "CardNum or CardPsw was invalid, please check them again.");
		strMap.put(string.STR_PAYMENT_DIALOG_TOAST_INVALID_AMOUNT, "Invaid payment amount.Please check.");
		strMap.put(string.STR_PAYMENT_DIALOG_TOAST_NETWORK_UNAVAILABLE, "Network is unavailable. Pls check.");
		strMap.put(string.STR_PAYMENT_DIALOG_TOAST_ITEM_NULL, "Item is null. Pls check.");
		strMap.put(string.STR_PAYMENT_DIALOG_TOAST_ITEM_CONTENT_EMPTY, "Item's subject and description should not be empty.Pls fill it.");
		strMap.put(string.STR_PAYMENT_DIALOG_TOAST_MM_UNAVAILABLE, "Telco payment was unavailable, Pls use other payment.");
		strMap.put(string.STR_PAYMENT_DIALOG_TOAST_ORDER_UNAVAILABLE, "Cannot create order, please try again.");

		strMap.put(string.STR_ACCOUNT_DIALOG_USERNAME_TXT, "UserName:");
		strMap.put(string.STR_ACCOUNT_DIALOG_PASSWORD_TXT, "PassWord:");
		strMap.put(string.STR_ACCOUNT_DIALOG_MOBILE_TXT, "Mobile:");
		strMap.put(string.STR_ACCOUNT_DIALOG_BTN_SUBMIT_TXT,"Submit");
		strMap.put(string.STR_ACCOUNT_DIALOG_BTN_LOGIN_TXT, "Login");
		strMap.put(string.STR_ACCOUNT_DIALOG_BTN_REGISTER_TXT, "Register");
		strMap.put(string.STR_ACCOUNT_DIALOG_LOGIN_SUBJECT_TXT, "Login:");
		strMap.put(string.STR_ACCOUNT_DIALOG_REGISTER_SUBJECT_TXT, "Register:");

		strMap.put(string.STR_ACCOUNT_TOAST_USERNAME_INVALID_TXT, "UserName should be 4~20 leters, numbers!");
		strMap.put(string.STR_ACCOUNT_TOAST_PASSWORD_INVALID_TXT, "PassWord should be 6~12 characters!");
		strMap.put(string.STR_ACCOUNT_TOAST_MOBILE_INVALID_TXT, "Invalid Mobile.");

		strMap.put(string.STR_SYSTEM_TOAST_NETWORK_UNAVAILABLE_TXT, "Network is unavailable.");
		strMap.put(string.STR_SYSTEM_TOAST_USERNAME_PASSWORD_INVALID_TXT, "Username or Password was error.");
		strMap.put(string.STR_SYSTEM_TOAST_USERNAME_EXISTED_TXT, "Username existed.");

		//Chinese
		//strMap.put(string.STR_PAYMENT_DIALOG_BTN_EXIT_TXT | string.STR_MASK,"安易支付平台");
		strMap.put(string.STR_PAYMENT_DIALOG_PAYLAYOUT_TYPE_TXT | string.STR_MASK, "支付方式:");
		strMap.put(string.STR_PAYMENT_DIALOG_PHONELAYOUT_TXT | string.STR_MASK, "客服电话:021-51758646");
		strMap.put(string.STR_PAYMENT_DIALOG_TOAST_PAYLOCK_TXT | string.STR_MASK, "支付正在进行中...");
		strMap.put(string.STR_PAYMENT_DIALOG_QUICKBILL_TIP_TXT| string.STR_MASK, "支付方式");
		strMap.put(string.STR_PAYMENT_DIALOG_QUICKBILL_TYPE_TXT| string.STR_MASK, "类型:");
		strMap.put(string.STR_PAYMENT_DIALOG_QUICKBILL_VALUE_TXT| string.STR_MASK, "面额:");
		strMap.put(string.STR_PAYMENT_DIALOG_QUICKBILL_CARDNUM_TXT| string.STR_MASK, "卡号:");
		strMap.put(string.STR_PAYMENT_DIALOG_QUICKBILL_CARDPSW_TXT| string.STR_MASK, "密码:");
		strMap.put(string.STR_PAYMENT_DIALOG_QUICKBILL_SUBMIT_TXT| string.STR_MASK, "提交");
		strMap.put(string.STR_PAYMENT_DIALOG_SMSPAY_TIP_TXT | string.STR_MASK, "正在使用短信支付，请稍候。");
		strMap.put(string.STR_PAYMENT_TOAST_PURCHASE_WAIT_TXT | string.STR_MASK, "订单$$正在同步，稍后将返回支付结果。");
		strMap.put(string.STR_PAYMENT_TOAST_QUICKBILL_VALUE_LESS | string.STR_MASK, "请选择正确的充值卡支付额度");
		strMap.put(string.STR_PAYMENT_TOAST_QUICKBILL_FORM_INVALID | string.STR_MASK, "帐号密码输入格式有误！");
		strMap.put(string.STR_PAYMENT_DIALOG_TOAST_INVALID_AMOUNT | string.STR_MASK, "请求金额数值非法!");
		strMap.put(string.STR_PAYMENT_DIALOG_TOAST_NETWORK_UNAVAILABLE | string.STR_MASK, "网络状况异常，请检查后重试。");
		strMap.put(string.STR_PAYMENT_DIALOG_TOAST_ITEM_NULL | string.STR_MASK, "物品信息为空。");
		strMap.put(string.STR_PAYMENT_DIALOG_TOAST_ITEM_CONTENT_EMPTY | string.STR_MASK, "物品的名称和描述不能为空。");
		strMap.put(string.STR_PAYMENT_DIALOG_TOAST_MM_UNAVAILABLE | string.STR_MASK, "MM支付暂时不可用，请选择其他支付方式。");
		strMap.put(string.STR_PAYMENT_DIALOG_TOAST_ORDER_UNAVAILABLE| string.STR_MASK, "无法获取订单，请稍后重试。");

		strMap.put(string.STR_ACCOUNT_DIALOG_USERNAME_TXT| string.STR_MASK, "帐号:");
		strMap.put(string.STR_ACCOUNT_DIALOG_PASSWORD_TXT | string.STR_MASK, "密码:");
		strMap.put(string.STR_ACCOUNT_DIALOG_MOBILE_TXT | string.STR_MASK, "手机号:");
		strMap.put(string.STR_ACCOUNT_DIALOG_BTN_SUBMIT_TXT | string.STR_MASK, "提交");
		strMap.put(string.STR_ACCOUNT_DIALOG_BTN_LOGIN_TXT | string.STR_MASK, "登录");
		strMap.put(string.STR_ACCOUNT_DIALOG_BTN_REGISTER_TXT | string.STR_MASK, "注册");
		strMap.put(string.STR_ACCOUNT_DIALOG_LOGIN_SUBJECT_TXT | string.STR_MASK, "登录:");
		strMap.put(string.STR_ACCOUNT_DIALOG_REGISTER_SUBJECT_TXT | string.STR_MASK, "注册:");

		strMap.put(string.STR_ACCOUNT_TOAST_USERNAME_INVALID_TXT | string.STR_MASK, "用户名格式应为: 4-20位字母数字组合。");
		strMap.put(string.STR_ACCOUNT_TOAST_PASSWORD_INVALID_TXT | string.STR_MASK, "密码格式应为: 6-12位字符。");
		strMap.put(string.STR_ACCOUNT_TOAST_MOBILE_INVALID_TXT | string.STR_MASK, "手机号格式有误。");

		strMap.put(string.STR_SYSTEM_TOAST_NETWORK_UNAVAILABLE_TXT | string.STR_MASK, "当前网络不可用。");
		strMap.put(string.STR_SYSTEM_TOAST_USERNAME_PASSWORD_INVALID_TXT | string.STR_MASK, "用户名密码错误。");
		strMap.put(string.STR_SYSTEM_TOAST_USERNAME_EXISTED_TXT | string.STR_MASK, "用户名已存在。"); 
	}

	public static String getLocaleString(Context ctx, int key) {
		String locale = ctx.getResources().getConfiguration().locale.getCountry();
		//if device language was cn or tw, we could regard it in china. Using chinese words.
		if(locale.equalsIgnoreCase("cn") || locale.equalsIgnoreCase("tw")) {
			if(strMap.containsKey(key | string.STR_MASK )) {
				return strMap.get(key | string.STR_MASK );
			}
		}
		if(strMap.containsKey(key)) {
			return strMap.get(key);
		}
		return "";
	}

}
