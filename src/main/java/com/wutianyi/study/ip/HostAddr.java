package com.wutianyi.study.ip;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class HostAddr {
	public static void main(String[] args) {
		try {
			String tomcatIp = InetAddress.getLocalHost().getHostAddress(); // 获取服务器端IP地址
			System.out.println(tomcatIp);
			InetAddress addr = InetAddress.getByName("www.163.com");
			byte[] ipAddr = addr.getAddress();

			// Convert to dot representation
			String ipAddrStr = "";
			for (int i = 0; i < ipAddr.length; i++) {
				if (i > 0) {
					ipAddrStr += ".";
				}
				ipAddrStr += ipAddr[i] & 0xFF;
			}
			System.out.println(ipAddrStr);
		} catch (UnknownHostException e) {
			System.out.println("UnknownHostException");
		}
	}
}
