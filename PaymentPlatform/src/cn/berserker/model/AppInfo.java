package cn.berserker.model;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import cn.berserker.utils.MD5;
import cn.berserker.utils.Tools;

public class AppInfo {
	private String appId;
	private String appKey;
	private int appVersion = 1;
	private String channelId;
	private String mmChannelId;
	private String packageName;
	private String signature;
	// default.xml used as default's channelId.
	private static final String CHANNEL_DEFAULT = "default.xml";
	// mmiap.xml used as mm's channelId.
	private static final String CHANNEL_FILE = "mmiap.xml";
	
	public AppInfo(Context ctx, String appid, String appkey) {
		appId = appid;
		appKey = appkey;
		// read apk's package name.
		packageName = ctx.getPackageName();
		//used to read signature.
		try {
			PackageInfo packageInfo = ctx.getPackageManager()
				.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
			appVersion = packageInfo.versionCode;
			Signature[] sign = packageInfo.signatures;
			if(null != sign) {
				signature = sign[0].toCharsString();
				//then md5 signature.
				signature = MD5.getMD5(signature);
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		channelId = loadDefaultChannel(ctx);
		//if channel id has blank char at head or tail, just remove it.
		if(!TextUtils.isEmpty(channelId)) {
			channelId = channelId.trim();
		}
		else {
			channelId = "";
		}
		mmChannelId = loadChannelId(ctx);
		if(TextUtils.isEmpty(mmChannelId)) {
			mmChannelId = "";
		}
		//Tools.LOG("channel:" + channelId);
	}

	public String getAppId() { return this.appId; } 
	public String getAppKey() { return this.appKey; } 
	public int getAppVersion() { return this.appVersion; } 
	public String getChannelId() { return this.channelId; } 
	public String getMMChannelId() { return this.mmChannelId; }
	public String getPackageName() { return this.packageName; } 
	public String getSignature() { return this.signature; }

	//open APK resource place. and open file of "filename", to see the content.
	private static String getResFileContent(Context context, String filename) {
		InputStream is = context.getClass().getClassLoader().getResourceAsStream(filename);
		if (null == is) {
			return null;
		}
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
		StringBuilder builder = new StringBuilder();
		try {
			String content = "";
			while (bufferedReader.ready()) {
				content = bufferedReader.readLine();
				builder.append(content);
			}
			bufferedReader.close();
		} catch (IOException e) {
			return null;
		}
		return builder.toString();
	}

	private static String loadDefaultChannel(Context context) {
		String channelId = null;
		return getResFileContent(context, CHANNEL_DEFAULT);
	}


	// read mm's channel id. MMSDK provide this function. You could check MMSDK for detail.
	private static String loadChannelId(Context context) {
		String channelId = null;
		String channelStr = getResFileContent(context, CHANNEL_FILE);

		XmlPullParserFactory factory;
		try {
			factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			byte[] data = channelStr.getBytes();
			ByteArrayInputStream bais = new ByteArrayInputStream(data);
			parser.setInput(bais, "utf-8");
			int event = parser.getEventType();
			while(event != XmlPullParser.END_DOCUMENT) {
				switch(event) {
				case XmlPullParser.START_DOCUMENT:
					break;
				case XmlPullParser.START_TAG:
					String tag = parser.getName();
					if("channel".equals(tag)) {
						channelId = parser.nextText();
					}
					break;
				case XmlPullParser.END_TAG:
					break;
				}
				event = parser.next();
			}
		} catch(XmlPullParserException e) {
			return null;
		} catch(IOException e ) {
			return null;
		}
		return channelId;
	}
}
