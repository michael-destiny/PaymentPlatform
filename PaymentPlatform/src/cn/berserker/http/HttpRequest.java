package cn.berserker.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest {
	private final HttpURLConnection connection;
	private HttpContent content;
	
	public final static String METHOD_GET = "GET";
	public final static String METHOD_POST = "POST";
	
	public final static int CONNECT_TIMEOUT = 30 * 1000;
	public final static int READ_TIMEOUT = 20 * 1000;

	public HttpRequest(String requestMethod, String url) throws IOException {
		System.setProperty("http.keepAlive", "false");
		HttpURLConnection connection = this.connection = (HttpURLConnection) new URL(
				url).openConnection();
		connection.setRequestMethod(requestMethod);
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(false);
		connection.setConnectTimeout(CONNECT_TIMEOUT);
		connection.setReadTimeout(READ_TIMEOUT);
	}

	public void addHeader(String name, String value) {
		connection.addRequestProperty(name, value);
	}

	public void setTimeout(int connectTimeout, int readTimeout) {
		connection.setReadTimeout(readTimeout);
		connection.setConnectTimeout(connectTimeout);
	}

	public HttpResponse execute() throws IOException {
		HttpURLConnection connection = this.connection;
		// write content
		if (content != null) {
			String contentType = content.getType();
			if (contentType != null) {
				addHeader("Content-Type", contentType);
			}
			String contentEncoding = content.getEncoding();
			if (contentEncoding != null) {
				addHeader("Content-Encoding", contentEncoding);
			}
			long contentLength = content.getLength();
			if (contentLength >= 0) {
				addHeader("Content-Length", Long.toString(contentLength));
			}
			if (contentLength != 0) {
				// setDoOutput(true) will change a GET method to POST, so only
				// if contentLength != 0
				connection.setDoOutput(true);
				// see
				// http://developer.android.com/reference/java/net/HttpURLConnection.html
				if (contentLength >= 0 && contentLength <= Integer.MAX_VALUE) {
					connection.setFixedLengthStreamingMode((int) contentLength);
				} else {
					connection.setChunkedStreamingMode(0);
				}
				content.writeTo(connection.getOutputStream());
			}
		}
		// connect
		connection.connect();
		return new HttpResponse(connection);
	}

	public void setContent(HttpContent content) {
		this.content = content;
	}
}
