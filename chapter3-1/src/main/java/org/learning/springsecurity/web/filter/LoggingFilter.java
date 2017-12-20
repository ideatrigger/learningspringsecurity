package org.learning.springsecurity.web.filter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class LoggingFilter implements Filter {
	private PrintWriter logger;
	private String prefix;
	
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		prefix = "URI: "; // filterConfig.getInitParameter("prefix");
		String logFileName = "log.txt"; //filterConfig.getInitParameter("logFileName");
		String appPath = "d:\\logs";
		System.out.println("logFileName: " + logFileName);
		try {
			logger = new PrintWriter( new File(appPath, logFileName));
		} catch (FileNotFoundException e) {
			throw new ServletException(e.getMessage());
		}
	}
	
	@Override
	public void destroy() {
		System.out.println("Destroying filter");
		if ( logger != null) {
			logger.close();
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		System.out.println("LoggingFilter.doFilter");
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		logger.println( new Date() + " " + prefix + httpRequest.getRequestURI());
		logger.flush();
		filterChain.doFilter(request, response);
	}

}
