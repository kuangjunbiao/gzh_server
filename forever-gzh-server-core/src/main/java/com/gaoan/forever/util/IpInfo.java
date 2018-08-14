package com.gaoan.forever.util;

/**
 * ip信息
 */
public class IpInfo {
	/**
	 * 中国
	 */
	private String country;
	/**
	 * 广东省
	 */
	private String region;
	/**
	 * 深圳市
	 */
	private String city;
	/**
	 * 电信
	 */
	private String isp;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getIsp() {
		return isp;
	}

	public void setIsp(String isp) {
		this.isp = isp;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String result() {
		return getStr(country) + getStr(region) + getStr(city) + getStr(" ") + getStr(isp);
	}

	private String getStr(String src) {
		if (src == null) {
			return "";
		}
		return src;
	}

	@Override
	public String toString() {
		return "IpInfo{" + "city='" + city + '\'' + ", country='" + country + '\'' + ", region='" + region + '\''
				+ ", isp='" + isp + '\'' + '}';
	}
}