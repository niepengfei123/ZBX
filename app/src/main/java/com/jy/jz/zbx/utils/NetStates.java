/**
 * 
 */
package com.jy.jz.zbx.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

/**
 * @author Administrator
 * 
 */
public class NetStates {
	public static String netState(int stateCode) {
		switch (stateCode) {
			case 0 :
				return null;
			case 1 :
				return "超时";
			case 2 :
				return "网络不正常";
			case 3 :
				return "请求方法不正确";
			case 4 :
				return "没有找到资源";
			case 5 :
				return "网络不正常，请检查网络";
			case -1 :
				return "网络不正常，请检查网络";
			default :
				return "其它错误，请联系工程师";
		}
	}

	/**
	 * 网络判断工具，只能在当前activity下使用
	 * 
	 * @return true:
	 */
	public static boolean trueNet(Activity at) {
		if (at == null) {
			return false;
		}
		boolean isConnect = false;
		ConnectivityManager cm = (ConnectivityManager) at
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null) {
			isConnect = false;
		}
		if (cm.getActiveNetworkInfo() != null) {
			if (cm.getActiveNetworkInfo().isConnected()
					|| cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
							.isConnected()) {
				isConnect = true;
			} else {
				isConnect = false;
			}

		}
		return isConnect;
	}
	
	public static boolean trueNet(Context at) {
		if (at == null) {
			return false;
		}
		boolean isConnect = false;
		ConnectivityManager cm = (ConnectivityManager) at
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null) {
			isConnect = false;
		}
		if (null != cm.getActiveNetworkInfo()) {
			if (cm.getActiveNetworkInfo().isConnected()
					|| cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
							.isConnected()) {
				isConnect = true;
			} else {
				isConnect = false;
			}

		}
		return isConnect;
	}

	/**
	 * 判断是否连接上wifi
	 * 
	 * @param at
	 * @return
	 */
	public static boolean connByWifi(Activity at) {
		if (at == null) {
			return false;
		}
		boolean isConnect = false;
		ConnectivityManager cm = (ConnectivityManager) at
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null) {
			isConnect = false;
		}
		if (cm.getActiveNetworkInfo() != null) {
			if (cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()) {
				isConnect = true;
			} else {
				isConnect = false;
			}

		}
		return isConnect;
	}
	
	/**
	 * 检查是否能连接网络
	 */
	public static boolean check_connect_net(Context con) {
		ConnectivityManager cwjManager = (ConnectivityManager) con
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		try {
			if (cwjManager.getActiveNetworkInfo() != null
					&& cwjManager.getActiveNetworkInfo().isAvailable()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
