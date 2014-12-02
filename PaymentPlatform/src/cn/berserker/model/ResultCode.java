package cn.berserker.model;

import java.util.HashMap;

public class ResultCode {
	// ErrorList
	//Notification Type ---->   INIT    1
	public static final int INIT_SUCC = 1000;
	public static final int INIT_FAIL = 1001;
	
	//Notification Type ---->   PAY     6
	public static final int PAY_SUCC = 6000;
	public static final int PAY_CANCEL = 6001;
	public static final int PAY_FAIL = 6002;
	public static final int PAY_WAIT = 6003;

	public static final int DESTROY_SUCC = 8000;
	public static final int DESTROY_FAIL = 8001;

	
}
