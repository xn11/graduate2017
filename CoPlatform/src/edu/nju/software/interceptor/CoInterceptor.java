package edu.nju.software.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class CoInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest servletRequest,
			HttpServletResponse servletResponse, Object object, Exception exception)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
			Object object, ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
			Object object) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
