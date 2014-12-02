package cn.berserker.platform;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.app.Activity;

import cn.berserker.model.DeviceInfo;
import cn.berserker.model.UserInfo;
import cn.berserker.model.Notification;
import cn.berserker.model.ResultCode;
import cn.berserker.http.HttpRequest;
import cn.berserker.http.HttpContent;
import cn.berserker.utils.Tools;
import org.json.JSONObject;
import org.json.JSONException;

import android.text.TextUtils;

public class AccountManager {
	//Account should be saved as MD5(psw) or other encype type.
	UserInfo userInfo = null;
	AccountManager(ControlCenter center) {
		userInfo = new UserInfo(center.getDeviceInfo());
	}

	UserInfo getUserInfo() {
		return userInfo;
	}

}
