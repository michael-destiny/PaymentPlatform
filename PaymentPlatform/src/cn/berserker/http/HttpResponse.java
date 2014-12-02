package cn.berserker.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpResponse {
	private final HttpURLConnection connection;
	private final int responseCode;
	private final String responseMessage;
	private final ArrayList<String> headerNames = new ArrayList<String>();
	private final ArrayList<String> headerValues = new ArrayList<String>();

	HttpResponse(HttpURLConnection connection) throws IOException {
		this.connection = connection;
		responseCode = connection.getResponseCode();
		responseMessage = connection.getResponseMessage();
		List<String> headerNames = this.headerNames;
		List<String> headerValues = this.headerValues;
		for (Map.Entry<String, List<String>> entry : connection
				.getHeaderFields().entrySet()) {
			String key = entry.getKey();
			if (key != null) {
				for (String value : entry.getValue()) {
					if (value != null) {
						headerNames.add(key);
						headerValues.add(value);
					}
				}
			}
		}
	}

	public int getStatusCode() {
		return responseCode;
	}

	public InputStream getContent() throws IOException {
		HttpURLConnection connection = this.connection;
		return HttpStatusCodes.isSuccess(responseCode) ? connection
				.getInputStream() : connection.getErrorStream();
	}

	public String getContentEncoding() {
		return connection.getContentEncoding();
	}

	public long getContentLength() {
		String string = connection.getHeaderField("Content-Length");
		return string == null ? -1 : Long.parseLong(string);
	}

	public String getContentType() {
		return connection.getHeaderField("Content-Type");
	}

	public String getStatusLine() {
		String result = connection.getHeaderField(0);
		return result != null && result.startsWith("HTTP/1.") ? result : null;
	}

	public int getHeaderCount() {
		return headerNames.size();
	}

	public String getHeaderName(int index) {
		return headerNames.get(index);
	}

	public String getHeaderValue(int index) {
		return headerValues.get(index);
	}

	public void disconnect() {
		connection.disconnect();
	}

}
