package edu.nju.software.util;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import edu.nju.software.pojo.Admin;

public class CoHashMap extends HashMap<String, Object>{

	private static Logger logger = LoggerFactory.getLogger(CoHashMap.class);
	
	private static final long serialVersionUID = -2882821927335974480L;
	
	private static Gson gson = null;
	
	public CoHashMap() {
		super();
		if(null == gson) {
			gson = new Gson();
		}
	}
	
	public CoHashMap(HttpServletRequest request) {
		this();
		// for normal environment
/*		if(null == gson) {
			gson = new Gson();
		}
		Admin admin = null;
		try {
			admin = gson.fromJson(URLDecoder.decode(CoUtils.getCookie(request, "currentAdmin"), "UTF-8"), Admin.class);
			super.put("admin", admin);
		} catch (JsonSyntaxException e) {
			logger.error(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}*/
		String sessionId = request.getSession().getId();
		Admin admin = (Admin) UserInfoStorage.getAdmin(sessionId);
		System.out.println("coHashMap sessionId: " + sessionId);
		if(null != admin) {
			logger.debug("admin not null");
			super.put("admin", admin);
		}else {
			logger.debug("admin null");
		}
	}
}

	
