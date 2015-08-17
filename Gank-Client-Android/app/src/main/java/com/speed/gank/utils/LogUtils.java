package com.speed.gank.utils;


import android.util.Log;

public class LogUtils {
	private static String className;
	private static String methodName;
	private static int lineNumber;

	private static boolean isDebug = true;

	private LogUtils() {
	}

	private static boolean isDebug() {
		return isDebug;
	}

	public static void setEnable(boolean enable) {
		isDebug = enable;
	}

	private static String createLog(String log) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[").append(methodName).append(":").append(lineNumber)
				.append("]").append(log);
		return buffer.toString();
	}

	private static void getMethodName(StackTraceElement[] stackTraceElements) {
		className = stackTraceElements[1].getFileName();
		methodName = stackTraceElements[1].getMethodName();
		lineNumber = stackTraceElements[1].getLineNumber();
	}

	public static void d(String message) {
		if (!isDebug()) {
			return;
		}
		getMethodName(new Throwable().getStackTrace());
		Log.d(className, createLog(message));
	}

	public static void e(String message) {
		if (!isDebug()) {
			return;
		}
		getMethodName(new Throwable().getStackTrace());
		Log.e(className, createLog(message));
	}

	public static void i(String message) {
		if (!isDebug()) {
			return;
		}
		getMethodName(new Throwable().getStackTrace());
		Log.i(className, createLog(message));
	}

	public static void w(String message) {
		if (!isDebug()) {
			return;
		}
		getMethodName(new Throwable().getStackTrace());
		Log.w(className, createLog(message));
	}
}
