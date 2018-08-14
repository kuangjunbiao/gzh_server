package com.gaoan.forever.util;

import java.net.InetAddress;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gaoan.forever.shiro.bean.ServerInfo;

/**
 * 系统信息
 *
 * @author longshengtang
 * @since 2017-04-23 13:16
 **/
public class SysInfoUtil {
	private static final Logger logger = LoggerFactory.getLogger(SysInfoUtil.class);

	private SysInfoUtil() {
	}

	private static ServerInfo serverInfo;

	public static ServerInfo getSysConfig() {
		if (serverInfo == null) {
			synchronized (SysInfoUtil.class) {
				if (serverInfo == null) {
					serverInfo = initServerInfo();
				}
			}
		}
		return serverInfo;
	}

	public static String getSysIp() {
		return getSysIp("");
	}

	public static String getSysIp(String defaultIp) {
		ServerInfo sysConfig = getSysConfig();
		if (sysConfig == null) {
			return defaultIp;
		}
		return sysConfig.getServerIp();
	}

	private static AtomicInteger initCount = new AtomicInteger(0);

	// 得到计算机的ip,名称,操作系统名称,操作系统版本
	public static ServerInfo initServerInfo() {
		try {
			logger.info("开始获取服务器信息");
			// 如果大于5次，证明获取时候出错，最多只能5次
			if (initCount.getAndAdd(1) > 5) {
				return new ServerInfo();
			}
			InetAddress addr = InetAddress.getLocalHost();
			String ip = addr.getHostAddress().toString(); // 获取本机ip
			String hostName = addr.getHostName().toString(); // 获取本机计算机名称
			System.out.println("本机IP：" + ip + "\n本机名称:" + hostName);
			Properties props = System.getProperties();
			System.out.println("操作系统的名称：" + props.getProperty("os.name"));
			System.out.println("操作系统的版本：" + props.getProperty("os.version"));
			ServerInfo serverInfo = new ServerInfo();
			serverInfo.setServerIp(ip);
			serverInfo.setServerName(hostName);
			serverInfo.setServerSystem(props.getProperty("os.name"));
			logger.info("获取服务服务器信息完成！！！ " + serverInfo);
			return serverInfo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
