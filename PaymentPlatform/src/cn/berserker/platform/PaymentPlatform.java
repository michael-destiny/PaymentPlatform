package cn.berserker.platform;

public class PaymentPlatform {
	  public static final int SDK_VERSION = 0x023457;
	  static final String BASE_URL = "http://../";
	  static final String REQUEST_UPDATE =		BASE_URL + "query/versionquery.php";
	  static final String REPORT_TIMELOG  =		BASE_URL + "report/reportonlinetime.php";

	  static final String ACCOUNT_BASE_URL = "http://../";
	  static final String REQUEST_REGISTER =	ACCOUNT_BASE_URL + "user/userreg.php";
	  static final String REQUEST_LOGIN =		ACCOUNT_BASE_URL + "user/userlogin.php";

	  static final String REQUEST_LOGOUT =		ACCOUNT_BASE_URL + "user/userlogoff.php";
	  static final String REQUEST_LOGINLOG =	ACCOUNT_BASE_URL + "user/loginlog.php";

}
