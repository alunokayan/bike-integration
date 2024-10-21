package br.edu.ifsp.spo.bike_integration.util;

import jakarta.servlet.http.HttpServletRequest;

public interface HeaderUtil {

	public static String getAccessToken(HttpServletRequest request) {
		return request.getHeader("access-token");
	}
	
}
