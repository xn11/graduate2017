package edu.nju.software.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import edu.nju.software.util.UserInfoStorage;

public class AuthenticationFilter implements Filter {
	
	private static Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
	
	private static final String[] VALID_URL_SUFFIX = {"Login", ".js", ".css", ".jpg", ".png"};
	
	private static final String WECHAT_URL_PREFIX = "/wechat";
	
	private static final String WECHAT_ENTRANCE_URL_PREFIX = "/weixin";
	
	private static final String SYSTEM_ENTRANCE_URL_PREFIX = "/System";
	
	private static final String SYSTEM_LOGIN_URL_SUFFIX = "Login";
	
	//used for solve sae bug
	public static Map<String, Object> adminMap = new HashMap<String, Object>();
	public static Map<String, Object> memberMap = new HashMap<String, Object>();
	public static Map<String, Object> systemAdminMap = new HashMap<String, Object>();
	
	private Gson gson = null;

	@Override
	public void destroy() {
		logger.info("AuthencationFilter destroy");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		
		String requestURI = httpServletRequest.getRequestURI();
		if(!isValidURI(requestURI) && !requestURI.contains(WECHAT_URL_PREFIX) && !requestURI.contains(WECHAT_ENTRANCE_URL_PREFIX)
				&& !requestURI.contains(SYSTEM_ENTRANCE_URL_PREFIX)) {
//			String adminCookieValue = CoUtils.getCookie(httpServletRequest, "currentAdmin");
/*			if(null == adminCookieValue) {
				httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/Login");
				return;
			}*/
			
			HttpSession session = httpServletRequest.getSession(true);
			String sessionId = session.getId();
			System.out.println("sessionId: " + sessionId);
			if(null == UserInfoStorage.getAdmin(sessionId)) {
				httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/Login");
				return;
			}
		}else if(requestURI.contains(SYSTEM_ENTRANCE_URL_PREFIX) && !requestURI.endsWith(SYSTEM_LOGIN_URL_SUFFIX)) {
			if(null == UserInfoStorage.getSystemAdmin(httpServletRequest.getSession(true).getId())) {
				httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/System/Login");
				return;
			}
		}
		
//		if(requestURI.startsWith(contextPath + WECHAT_URL_PREFIX) && !requestURI.equals(contextPath + WECHAT_URL_PREFIX)) {
//			String memberCookieValue = CoUtils.getCookie(httpServletRequest, "currentMember");
//			if(null == memberCookieValue) {
//				httpServletResponse.sendRedirect(httpServletRequest.nadeigetContextPath() + WECHAT_URL_PREFIX);
//				return;
//			}
//		}
		chain.doFilter(request, response);
		return;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("AuthencationFilter init");
		if(null == gson) {
			gson = new Gson();
		}
	}
	
	private boolean isValidURI(String uri) {
		if(StringUtils.isBlank(uri)) {
			return false;
		}
		
		for(String suffix : VALID_URL_SUFFIX) {
			if(uri.endsWith(suffix)) {
				return true;
			}
		}
		
		return false;
	}

}
