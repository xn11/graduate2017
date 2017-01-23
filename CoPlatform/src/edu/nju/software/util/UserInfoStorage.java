package edu.nju.software.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class UserInfoStorage {
	private static Map<String, Object> adminMap = new HashMap<String, Object>();
	private static Map<String, Object> memberMap = new HashMap<String, Object>();
	private static Map<String, Object> systemAdminMap = new HashMap<String, Object>();
	private static Map<String, Object> outEmployeeMap = new HashMap<String, Object>();
	
	public static Object getAdmin(String key) {
		if(StringUtils.isBlank(key)) {
			throw new IllegalArgumentException();
		}
		
		if(null == adminMap) {
			adminMap = new HashMap<String, Object>();
		}
		
		return adminMap.get(key);
	}
	
	public static void putAdmin(String key, Object value) {
		if(StringUtils.isBlank(key)) {
			throw new IllegalArgumentException();
		}
		
		if(null == adminMap) {
			adminMap = new HashMap<String, Object>();
		}
		
		adminMap.put(key, value);
	}
	
	public static Object getMember(String key) {
		if(StringUtils.isBlank(key)) {
			throw new IllegalArgumentException();
		}
		
		if(null == memberMap) {
			memberMap = new HashMap<String, Object>();
		}
		
		return memberMap.get(key);
	}
	
	public static void putMember(String key, Object value) {
		if(StringUtils.isBlank(key)) {
			throw new IllegalArgumentException();
		}
		
		if(null == memberMap) {
			memberMap = new HashMap<String, Object>();
		}
		
		memberMap.put(key, value);
	}
	
	public static Object getSystemAdmin(String key) {
		if(StringUtils.isBlank(key)) {
			throw new IllegalArgumentException();
		}
		
		if(null == systemAdminMap) {
			systemAdminMap = new HashMap<String, Object>();
		}
		
		return systemAdminMap.get(key);
	}
	
	public static void putSystemAdmin(String key, Object value) {
		if(StringUtils.isBlank(key)) {
			throw new IllegalArgumentException();
		}
		
		if(null == systemAdminMap) {
			systemAdminMap = new HashMap<String, Object>();
		}
		
		systemAdminMap.put(key, value);
	}
	
	public static Object getOutEmployee(String key){
		if(StringUtils.isBlank(key)) {
			throw new IllegalArgumentException();
		}
		
		if(null == outEmployeeMap) {
			outEmployeeMap = new HashMap<String, Object>();
		}
		
		return outEmployeeMap.get(key);
	}
	
	public static void putOutEmployee(String key, Object value) {
		if(StringUtils.isBlank(key)) {
			throw new IllegalArgumentException();
		}
		
		if(null == outEmployeeMap) {
			outEmployeeMap = new HashMap<String, Object>();
		}
		
		outEmployeeMap.put(key, value);
	}
}
