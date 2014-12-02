package cn.berserker.model;

public interface Notification
{
	public static final int INIT = 1;
	public static final int UPDATE = 2;
	public static final int LOGIN = 3;
	public static final int LOGOUT = 4;
	public static final int REGISTER = 5;
	public static final int PAY = 6;
	public static final int SYSTEM = 7;

	public static final int DESTROY = 8;

	//use to notify client
	public void notifyMessage(int resultType, int resultCode, String msg);
}
