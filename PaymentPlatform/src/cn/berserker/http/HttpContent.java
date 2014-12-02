package cn.berserker.http;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;


public class HttpContent {
	public static final String CONTENT_TYPE_JSON = "application/json:charset=utf-8";
	private String contentType = null;
	private String encoding = "utf-8";

	String content;
	public HttpContent(String value){
		try {
			content =new String(value.getBytes(),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public void writeTo(OutputStream out) throws IOException {
		
		//OutputStreamWriter in = new OutputStreamWriter(out);
		out.write(content.getBytes("utf-8"));
		out.close();
	}

	public long getLength() {
		if(null == content ) {
			return -1L;
		}
		int len;
		try {
			len = content.getBytes("utf-8").length;
			return len;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1L;
	}

	/**
	 * Returns the content encoding (for example {@code "gzip"}) or {@code null}
	 * for none.
	 */
	String getEncoding(){
		return encoding;
	}
	void setEncoding(){
		
	}

	/** Returns the content type or {@code null} for none. */
	public String getType(){
		return contentType;
	}
	public void setType(String type){
		contentType = type;
	}

	/**
	 * Returns whether or not retry is supported on this content type.
	 * 
	 * @since 1.4
	 */
	boolean retrySupported(){
		return true;
	}
}
