package cn.berserker.model;

import cn.berserker.utils.MD5;

public class UserInfo
{
	//device's unique id.
	String uuid = "";
	// user unique id.
	String uid = "";
	// user register type
	int regType = -1;
	// user name.
	String userName = "";
	// user's password
	String passWord = "";
	// who could link to him.
	String linkId = "";
	// user's mobile.
	String mobile = "";
	// user's nickname.
	String nickName = "";
	// user's current token id.
	String tokenId = "";
	// check whether is vistor mode.
	boolean isVisitor = false;

	public UserInfo(DeviceInfo device) {
		this.uuid = setUuid(device);
	}

	public String getUuid() { return this.uuid; } 
	public String getUid() { return this.uid; } 
	public int getRegType() { return this.regType; } 
	public String getUserName() { return this.userName; } 
	public String getPassWord() { return this.passWord; } 
	public String getLinkId() { return this.linkId; } 
	public String getMobile() { return this.mobile; } 
	public String getNickName() { return this.nickName; } 
	public String getTokenId() { return this.tokenId; }
	public boolean isVisitor() { return this.isVisitor; }

	public void setUid(String Uid) { uid = Uid; }
	public void setUserName(String UserName) { userName = UserName; }
	public void setPassWord(String PassWord) { passWord = PassWord; }
	public void setLinkId(String LinkId) { linkId = LinkId; }
	public void setMobile(String Mobile) { mobile = Mobile; }
	public void setRegType(int RegType) { regType = RegType; }
	public void setNickName(String Name) { nickName = Name; }
	public void setTokenId(String Token) { tokenId = Token; }
	public void setVisitor(boolean visitor) { isVisitor = visitor; }

	//we use wifimac, cpuid, imei, fix string to make uuid.
	private String setUuid(DeviceInfo device) {
		StringBuilder sb = new StringBuilder();
		sb.append(device.getWifiMac());
		sb.append("&" + device.getCpuId());
		sb.append("&" + device.getImei());
		sb.append("&" + "vilota");
		return MD5.getMD5(sb.toString());
	}
	// this should not public to others.
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(uuid +"\t&uid:");
		sb.append(uid+"\t&regType:");
		sb.append(regType+"\t&userName:");
		sb.append(userName+"\t&passWord:");
		sb.append(passWord+"\t&linkId:");
		sb.append(linkId+"\t&mobile:");
		sb.append(mobile+"\t&nickName:");
		sb.append(nickName+"\t&tokenId:");
		sb.append(tokenId);
		return sb.toString();
	}
	public static class RegisterType {
		public static final int NO_REG = 1;
		public static final int SELF_REG = 2;
		public static final int OTH_REG = 3;
	}

}
