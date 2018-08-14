package com.gaoan.forever.shiro.bean;

public class ServerInfo {
	private String serverName;
	private String serverIp;
	private String serverSystem;

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getServerSystem() {
		return serverSystem;
	}

	public void setServerSystem(String serverSystem) {
		this.serverSystem = serverSystem;
	}

	@Override
	public String toString() {
		return "ServerInfo{" +
				"serverIp='" + serverIp + '\'' +
				", serverName='" + serverName + '\'' +
				", serverSystem='" + serverSystem + '\'' +
				'}';
	}
}