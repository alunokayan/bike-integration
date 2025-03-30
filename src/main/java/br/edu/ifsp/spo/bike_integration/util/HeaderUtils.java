package br.edu.ifsp.spo.bike_integration.util;

import jakarta.servlet.http.HttpServletRequest;

public interface HeaderUtils {

	public static String getAccessToken(HttpServletRequest request) {
		return request.getHeader("access-token");
	}

}
