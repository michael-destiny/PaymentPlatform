package cn.berserker.http;

public class HttpStatusCodes {

	  /** Status code for a successful request. */
	  public static final int STATUS_CODE_OK = 200;

	  /** Status code for a resource corresponding to any one of a set of representations. */
	  public static final int STATUS_CODE_MULTIPLE_CHOICES = 300;

	  /** Status code for a resource that has permanently moved to a new URI. */
	  public static final int STATUS_CODE_MOVED_PERMANENTLY = 301;

	  /** Status code for a resource that has temporarily moved to a new URI. */
	  public static final int STATUS_CODE_FOUND = 302;

	  /** Status code for a resource that has moved to a new URI and should be retrieved using GET. */
	  public static final int STATUS_CODE_SEE_OTHER = 303;

	  /** Status code for a resource that has temporarily moved to a new URI. */
	  public static final int STATUS_CODE_TEMPORARY_REDIRECT = 307;

	  /** Status code for a request that requires user authentication. */
	  public static final int STATUS_CODE_UNAUTHORIZED = 401;

	  /** Status code for a server that understood the request, but is refusing to fulfill it. */
	  public static final int STATUS_CODE_FORBIDDEN = 403;

	  /** Status code for a server that has not found anything matching the Request-URI. */
	  public static final int STATUS_CODE_NOT_FOUND = 404;

	  /**
	   * Returns whether the given HTTP response status code is a success code {@code >= 200 and < 300}.
	   */
	  public static boolean isSuccess(int statusCode) {
	    return statusCode >= STATUS_CODE_OK && statusCode < STATUS_CODE_MULTIPLE_CHOICES;
	  }
}
