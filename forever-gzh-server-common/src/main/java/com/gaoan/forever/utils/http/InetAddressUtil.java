package com.gaoan.forever.utils.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InetAddressUtil {

	/**
	 * 获取本机IP字符串
	 * @return
	 */
	public static String getIPStr(){
		String ipStr = "";
		try {
			InetAddress addr  = InetAddress.getLocalHost();
			ipStr = addr.getHostAddress();//获得本机IP
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		ipStr = ipStr.replace(".", "_");
		return ipStr;
	}
	
	/**
	 * 根据IP地址获取客户机信息
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String getIPStr(String ip) {
		String ipStr = "";
		InetAddress addr = null;
		try {
			if("localhost".equals(ip)){
				addr  = InetAddress.getLocalHost();
			}else{
				addr  = InetAddress.getByName(ip);
			}
			ipStr = addr.getHostAddress();//获得本机IP
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		ipStr = ipStr.replace(".", "_");
		return ipStr;
	}
	/**
	 * 
	 * 判断当前操作是否windows
	 * 
	 * @return true---是Windows操作系統
	 */

	public static boolean isWindowsOS() {

		boolean isWindowsOS = false;
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().indexOf("windows") > -1) {
			isWindowsOS = true;
		}
		return isWindowsOS;
	}

	/**
	 * 
	 * 獲取本機IP地址，並自動區分Windows還是Linux操作系統 获取本机IP地址，并自动区分windows还是Linux操作系统
	 * 
	 * @return String
	 */

	public static String getLocalIP() {
		String sIP = "";
		InetAddress ip = null;
		try {
			// 如果是Windows操作系統
			if (isWindowsOS()) {
				ip = InetAddress.getLocalHost();
			}
			// 如果是Linux操作系統
			else {
				boolean bFindIP = false;
				Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface.getNetworkInterfaces();
				while (netInterfaces.hasMoreElements()) {
					if (bFindIP) {
						break;
					}
					NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
					// ----------特定情況，可以考虑用ni.getName判断
					// 遍历所有ip
					Enumeration<InetAddress> ips = ni.getInetAddresses();
					while (ips.hasMoreElements()) {
						ip = (InetAddress) ips.nextElement();
						if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() // 127.开头的都是lookback地址
								&& ip.getHostAddress().indexOf(":") == -1) {
							bFindIP = true;
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null != ip) {
			sIP = ip.getHostAddress();
		}
		return sIP;
	}

	/**
	 * 获取本机外网IP
	 * @return
	 */
	public static String getV4IP(){
		String ip = "";
		String chinaz = "http://ip.chinaz.com";

		StringBuilder inputLine = new StringBuilder();
		String read = "";
		URL url = null;
		HttpURLConnection urlConnection = null;
		BufferedReader in = null;
		try {
			url = new URL(chinaz);
			urlConnection = (HttpURLConnection) url.openConnection();
			in = new BufferedReader( new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
			while((read=in.readLine())!=null){
				inputLine.append(read+"\r\n");
			}
			//System.out.println(inputLine.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}


		Pattern p = Pattern.compile("\\<dd class\\=\"fz24\">(.*?)\\<\\/dd>");
		Matcher m = p.matcher(inputLine.toString());
		if(m.find()){
			String ipstr = m.group(1);
			ip = ipstr;
			//System.out.println(ipstr);
		}
		return ip;

	}
}
