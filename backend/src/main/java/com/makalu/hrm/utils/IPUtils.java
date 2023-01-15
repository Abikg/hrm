package com.makalu.hrm.utils;

import lombok.Value;

import javax.servlet.http.HttpServletRequest;

public class IPUtils {

	public static String getClientIp(HttpServletRequest request) {

		String remoteAddr = "";
		try {
			if (request != null) {

			}
			remoteAddr = request.getHeader("X-FORWARDED-FOR");

			if (remoteAddr != null) {
				String ips[] = remoteAddr.split(",");
				if (ips.length > 0) {
					remoteAddr = ips[0];
				}
			}
			if (remoteAddr == null || "".equals(remoteAddr)) {
				remoteAddr = request.getRemoteAddr();
			}
			try {
				System.out.println("Xforwarded : " + remoteAddr + " --- GetRemot: " + request.getRemoteAddr());
			} catch (Exception e) {

			}

		} catch (Exception e) {

		}

		return remoteAddr;
	}

	public static String getRequestedUrl(HttpServletRequest request) {

		String requestedUrl = "";
		try {
			requestedUrl = request.getRequestURL().toString();
		} catch (Exception e) {
		}

		return requestedUrl;
	}


	public static boolean ismakaluNetwork( String clientNetworkIp){
//		@Value("${your.path.yml.string}") String aa;

		if(clientNetworkIp.equals("0:0:0:0:0:0:0:1"))
			return  true;
		return false;
}

}


