package com.gaoan.forever.apiServer.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {

	public static String call(String urlStr, String method) {
		try {
			URL url = new URL(urlStr);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();

			httpCon.setConnectTimeout(3000);
			httpCon.setDoInput(true);
			httpCon.setRequestMethod(method);

			int code = httpCon.getResponseCode();

			if (code == 200) {
				return streamConvertToSting(httpCon.getInputStream());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String streamConvertToSting(InputStream is) {

		String tempStr = "";
		try {
			if (is == null)
				return null;
			ByteArrayOutputStream arrayOut = new ByteArrayOutputStream();
			byte[] by = new byte[1024];
			int len = 0;
			while ((len = is.read(by)) != -1) {
				arrayOut.write(by, 0, len);
			}
			tempStr = new String(arrayOut.toByteArray());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return tempStr;
	}
}
