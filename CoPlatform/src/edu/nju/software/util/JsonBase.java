package edu.nju.software.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonBase {
	protected static Gson gson = new GsonBuilder().setDateFormat("yyyyMM-dd").create();
	
	public String toJson() {
		return gson.toJson(this);
	}
}
