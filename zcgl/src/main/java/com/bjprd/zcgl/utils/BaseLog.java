package com.bjprd.zcgl.utils;

import android.util.Log;

import com.bjprd.zcgl.BuildConfig;

public class BaseLog {
	
	public static String TAG_Prefix = "BASEUI-";
	public static String TAG_HTTP = TAG_Prefix + "HTTP";
	public static String TAG_DEBUG = TAG_Prefix + "DEBUG";
	public static String TAG_INFO =  TAG_Prefix + "INFO";
	public static String TAG_ERROR = TAG_Prefix + "ERROR";

	public static boolean ON_ALL = BuildConfig.DEBUG;

	public static boolean ON_DEBUG = true;
	public static boolean ON_INFO = true; 
	public static boolean ON_ERROR = true;

	public static void d(Object msg){
		if(!ON_ALL) return;
		if(ON_DEBUG){
			Log.d(TAG_DEBUG," --- "+ msg + "");
		}
	}
	public static void i(Object msg){
		if(!ON_ALL) return;
		if(ON_INFO){
			Log.i(TAG_INFO," --- "+ msg + "");
		}
	}
	public static void e(Object msg){
		if(!ON_ALL) return;
		if(ON_ERROR){
			Log.e(TAG_ERROR," --- "+ msg + "");
		}
	}
}
