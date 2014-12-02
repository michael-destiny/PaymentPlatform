/**
 * 
 */
package com.alipay.android.app.lib;

import android.app.Activity;


/**
 * @author 0ki
 * 
 */
public class ResourceMap {
	public static Activity activity;
	public static int getString_confirm_title() {
		if(null != activity) {
			return activity.getResources().getIdentifier("confirm_title", "string", activity.getPackageName());
		}
		return 0;
	}

	public static int getString_ensure() {
		if(null != activity) {
			return activity.getResources().getIdentifier("ensure", "string", activity.getPackageName());
		}
		return 0;
	}

	public static int getString_cancel() {
		if(null != activity) {
			return activity.getResources().getIdentifier("cancel", "string", activity.getPackageName());
		}
		return 0;
	}

	public static int getString_processing() {
		if(null != activity) {
			return activity.getResources().getIdentifier("processing", "string", activity.getPackageName());
		}
		return 0;
	}

	public static int getString_download() {
		if(null != activity) {
			return activity.getResources().getIdentifier("download", "string", activity.getPackageName());
		}
		return 0;
	}

	public static int getString_cancelInstallTips() {
		if(null != activity) {
			return activity.getResources().getIdentifier("cancel_install_msp", "string", activity.getPackageName());
		}
		return 0;
	}

	public static int getString_cancelInstallAlipayTips() {
		if(null != activity) {
			return activity.getResources().getIdentifier("cancel_install_alipay", "string", activity.getPackageName());
		}
		return 0;
	}

	public static int getString_download_fail() {
		if(null != activity) {
			return activity.getResources().getIdentifier("download_fail", "string", activity.getPackageName());
		}
		return 0;
	}

	public static int getString_redo() {
		if(null != activity) {
			return activity.getResources().getIdentifier("redo", "string", activity.getPackageName());
		}
		return 0;
	}

	public static int getString_install_msp() {
		if(null != activity) {
			return activity.getResources().getIdentifier("install_msp", "string", activity.getPackageName());
		}
		return 0;
	}

	public static int getString_install_alipay() {
		if(null != activity) {
			return activity.getResources().getIdentifier("install_alipay", "string", activity.getPackageName());
		}
		return 0;
	}

	public static int getLayout_pay_main() {
		if(null != activity) {
			return activity.getResources().getIdentifier("alipay", "layout", activity.getPackageName());
		}
		return 0;
	}

	public static int getLayout_alert_dialog() {
		if(null != activity) {
			return activity.getResources().getIdentifier("dialog_alert", "layout", activity.getPackageName());
		}
		return 0;
	}

	public static int getStyle_alert_dialog() {
		if(null != activity) {
			return activity.getResources().getIdentifier("AlertDialog", "style", activity.getPackageName());
		}
		return 0;
	}

	public static int getImage_title() {
		if(null != activity) {
			return activity.getResources().getIdentifier("title", "drawable", activity.getPackageName());
		}
		return 0;
	}

	public static int getImage_title_background() {
		if(null != activity) {
			return activity.getResources().getIdentifier("title_background", "drawable", activity.getPackageName());
		}
		return 0;
	}

	public static int getId_mainView() {
		if(null != activity) {
			return activity.getResources().getIdentifier("mainView", "id", activity.getPackageName());
		}
		return 0;
	}

	public static int getId_webView() {
		if(null != activity) {
			return activity.getResources().getIdentifier("webView", "id", activity.getPackageName());
		}
		return 0;
	}

	public static int getId_btn_refresh() {
		if(null != activity) {
			return activity.getResources().getIdentifier("btn_refresh", "id", activity.getPackageName());
		}
		return 0;
	}

	public static int getId_left_button() {
		if(null != activity) {
			return activity.getResources().getIdentifier("left_button", "id", activity.getPackageName());
		}
		return 0;
	}

	public static int getId_right_button() {
		if(null != activity) {
			return activity.getResources().getIdentifier("right_button", "id", activity.getPackageName());
		}
		return 0;
	}

	public static int getId_dialog_split_v() {
		if(null != activity) {
			return activity.getResources().getIdentifier("dialog_split_v", "id", activity.getPackageName());
		}
		return 0;
	}

	public static int getId_dialog_title() {
		if(null != activity) {
			return activity.getResources().getIdentifier("dialog_title", "id", activity.getPackageName());
		}
		return 0;
	}

	public static int getId_dialog_message() {
		if(null != activity) {
			return activity.getResources().getIdentifier("dialog_message", "id", activity.getPackageName());
		}
		return 0;
	}

	public static int getId_dialog_divider() {
		if(null != activity) {
			return activity.getResources().getIdentifier("dialog_divider", "id", activity.getPackageName());
		}
		return 0;
	}

	public static int getId_dialog_content_view() {
		if(null != activity) {
			return activity.getResources().getIdentifier("dialog_content_view", "id", activity.getPackageName());
		}
		return 0;
	}

	public static int getId_dialog_button_group() {
		if(null != activity) {
			return activity.getResources().getIdentifier("dialog_button_group", "id", activity.getPackageName());
		}
		return 0;
	}

}
