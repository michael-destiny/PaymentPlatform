package cn.berserker.payment;

public class PaymentConfig {
	static final String BASE_URL = "http://.../";

	static final String REQUEST_ORDERID =					BASE_URL + "pay/prepay.php";
	static final String REPORT_ORDER =						BASE_URL + "pay/prepay.php";
	static final String REPORT_PAYEDORDER =					BASE_URL + "pay/confirmpay.php";
	static final String REQUEST_ORDER_STATUS =				BASE_URL + "query/ordstatusquery.php";


	public static final String QUICKPAY_NOTIFY_URL =		BASE_URL + "pay/notify4kq/notify_url.php";

	public static final int HANDLE_PAY_ALIPAY = 4;
	public static final int HANDLE_PAY_MM = 8;
	
	public static final int HANDLE_PAY_PROVIDE_ERROR = 9;
	public static final int HANDLE_PAY_REPORT = 10;
	static final int HANDLE_ORDER_QUERY = 11;

	static int MAX_TRY_COUNT = 5;

	//used for check expire time for quickbill.
	static final long QCK_QUERY_SEPTIME = 90 * 1000;
}
