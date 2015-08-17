package com.speed.gank.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

public class ScreenUtils {
	
	private static DisplayMetrics getMetric(Activity activity) {
		DisplayMetrics metric = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
		return metric;
	}
	
	public static int getScreenWidth(Activity activity) {
		return getMetric(activity).widthPixels;
	}
	
	public static int getScreenHeight(Activity activity) {
		
		return getMetric(activity).heightPixels;
	}
    
}
