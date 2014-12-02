package cn.berserker.model;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeviceInfo {
	String imei;
	String imsi;
	String mobile;
	String wifiMac;
	String cpuId;
	String model;
	String os;
	String manufacturer;

	public DeviceInfo(Context ctx) {
		TelephonyManager mTp = (TelephonyManager)ctx.getSystemService(Context.TELEPHONY_SERVICE);

		String value = mTp.getDeviceId();
		imei = (TextUtils.isEmpty(value)? "": value);
		value = mTp.getSubscriberId();
		imsi = (TextUtils.isEmpty(value)? "": value);
		value = mTp.getLine1Number();
		mobile = (TextUtils.isEmpty(value)? "": value);

		value = readCpuId();

		model = (TextUtils.isEmpty(Build.MODEL)? "": Build.MODEL);
		os = (TextUtils.isEmpty(Build.VERSION.RELEASE)? "": Build.VERSION.RELEASE);
		manufacturer = (TextUtils.isEmpty(Build.MANUFACTURER)? "": Build.MANUFACTURER);

		WifiManager wifi = (WifiManager)ctx.getSystemService(Context.WIFI_SERVICE);
		value = wifi.getConnectionInfo().getMacAddress();
		wifiMac = (TextUtils.isEmpty(value)? "": value);
	}

	public String getImei() { return imei; }
	public String getImsi() { return imsi; }
	public String getMobile() { return mobile; }
	public String getWifiMac() { return wifiMac; }
	public String getCpuId() { return cpuId; }
	public String getModel() { return model; }
	public String getOs() { return os; }
	public String getManufacturer() { return manufacturer; }

	public void setMobile(String m) { mobile = m;}

	//use command to read process output.
	private static String readCpuId() {
		try {
			Process pp = Runtime.getRuntime().exec("cat /proc/cpuinfo");
			StringBuffer text = new StringBuffer("");
			BufferedReader in = new BufferedReader(new InputStreamReader(pp.getInputStream()));
			String str;
			while ((str = in.readLine()) != null) {
				text.append(str);
			}
			in.close();
			Pattern p = Pattern.compile("Serial\\s*:\\s*?(\\w+)");
			Matcher m = p.matcher(text);
			if (m.find()) {
				return m.group(1);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
