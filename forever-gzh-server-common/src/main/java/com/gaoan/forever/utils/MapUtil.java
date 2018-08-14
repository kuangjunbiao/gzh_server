package com.gaoan.forever.utils;

import java.beans.PropertyDescriptor;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtilsBean;

import com.alibaba.fastjson.JSONObject;
import com.gaoan.forever.utils.date.DateUtil;

@SuppressWarnings("rawtypes")
public class MapUtil {
	
	/**
	 * 将Map<String,Object> 转换为Map<String,String>
	 * @param map	Map<String,Object>
	 * @return
	 */
	public static Map<String,String> formatMapStr(Map<String,Object> map){
		return formatMapStr(map, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 将Map<String,Object> 转换为Map<String,String>
	 * @param map Map<String,Object>
	 * @param format  格式化时间
	 * @return
	 */
	public static Map<String,String> formatMapStr(Map<String,Object> map, String format){
		/*String  formatF = "%.2f";
		String  formatD = "yyyy-mm-dd HH:mm:ss";
		if(format != null && !"".equals(format.trim())){
			for(String f :format.trim().split(",")){
				if(f.contains("%") && f.contains("f")){
					formatF = f;
				}else if(f.contains("%") || f.contains("f")){
					formatD = f;
				}
			}
		}	*/	
		Map<String,String> result = new HashMap<String, String>();
		Object value = null;
		for(String key : map.keySet()){
			value = map.get(key); 
			if(value instanceof Date){
				result.put(key, DateUtil.dateFormat((Date) value, format));
			}else{
				if(value == null){
					result.put(key, "");					
				}else{
					result.put(key, value.toString());
				}
			}
		}		
		return result;
	}
	
	/**
	 * 将Map<String,Object> 转换为JSONObject
	 * @param map
	 * @return
	 */
	public static JSONObject formatJson(Map<String,Object> map){
		return formatJson(map, "yyyy-mm-dd HH:mm:ss");
	}

	/**
	 * 将Map<String,Object> 转换为JSONObject
	 * @param map Map<String,Object>
	 * @param format  格式化时间
	 * @return
	 */
	public static JSONObject formatJson(Map<String,Object> map, String format){
		JSONObject result = new JSONObject();
		
		Object value = null;
		for(String key : map.keySet()){
			value = map.get(key); 
			if(value instanceof Date){
				result.put(key, DateUtil.dateFormat((Date) value, format));
			}else{
				if(value == null){
					result.put(key, "");					
				}else{
					result.put(key, value);
				}
			}
			
		}		
		return result;
	}
	
	/**
	 * 获取Map值的类型
	 * @param map
	 * @return
	 */
	
	public static Map<String, String> getMapValClass(Map map){
		Map<String,String> result = new HashMap<String, String>();
		Object value = null;
		for(Object key : map.keySet()){
			value = map.get(key); 
			if(value != null){
				result.put(key.toString(), value.getClass().getSimpleName());
			}
		}
		return result;
	}

	//将javabean实体类转为map类型，然后返回一个map类型的值
	public static Map<String, Object> beanToMap(Object obj) {
		Map<String, Object> params = new HashMap<String, Object>(0);
		try {
			PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
			PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
			for (int i = 0; i < descriptors.length; i++) {
				String name = descriptors[i].getName();
				if (!"class".equals(name)) {
					params.put(name, propertyUtilsBean.getNestedProperty(obj, name));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return params;
	}
}
