package com.speed.gank.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;
import com.speed.gank.configs.UrlConfigs;
import com.speed.gank.models.RequestModel;

public class HttpOperation {

	private static final int MAX_REQUEST_THREAD = 5;
	private static HttpOperation instance = null;
	private HttpUtils http;

	public static synchronized HttpOperation shareHttpOperation() {
		if (instance == null) {
			instance = new HttpOperation();
		}
		return instance;
	}

	public HttpOperation() {
		http = new HttpUtils();
		http.configRequestThreadPoolSize(MAX_REQUEST_THREAD);
	}

	public void setTimeOut(int timeout) {
		http.configTimeout(timeout);
	}

	private void get(String urlString, String s1, String s2, String s3, final RequestCallBack<String> callBack) {
		final String url = String.format(urlString,s1 , s2, s3);
		RequestParams params = new RequestParams();
		RequestCallBack<String> requestCallBack = new RequestCallBack<String>() {
			private long startTime;

			@Override
			public void onStart() {
				startTime = System.currentTimeMillis();
				if (callBack != null)
					callBack.onStart();
				super.onStart();
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				LogUtils.i("调用接口成功:" + "url:" + url + ",返回值:"
						+ responseInfo.result + ",耗时:"
						+ (System.currentTimeMillis() - startTime) + "ms");
				if (callBack != null)
					callBack.onSuccess(responseInfo);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				LogUtils.e("调用接口失败:" + "url:" + url + ",失败原因:" + msg + ",耗时:"
						+ (System.currentTimeMillis() - startTime) + "ms");
				error.printStackTrace();
				if (callBack != null)
					callBack.onFailure(error, msg);
			}

			@Override
			public void onCancelled() {
				if (callBack != null)
					callBack.onCancelled();
				super.onCancelled();
			}

			@Override
			public void onLoading(long total, long current, boolean isUploading) {
				if (callBack != null)
					callBack.onLoading(total, current, isUploading);
				super.onLoading(total, current, isUploading);
			}

		};
		http.send(HttpMethod.GET, url, params, requestCallBack);
	}
	
	public void getDatas(RequestModel model, RequestCallBack<String> callBack) {
		get(UrlConfigs.BASE_URL, String.valueOf(model.dataType), String.valueOf(model.pageSize), String.valueOf(model.page), callBack);
	}
	
	public void getCurrentDatas(RequestCallBack<String> callBack) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = (calendar.get(Calendar.MONTH)) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		get(UrlConfigs.DAY_URL, String.valueOf(year), String.valueOf(month), String.valueOf(day), callBack);
	}

	public void getLastDatas(RequestCallBack<String> callBack) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = (calendar.get(Calendar.MONTH)) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH) - 1;
		get(UrlConfigs.DAY_URL, String.valueOf(year), String.valueOf(month), String.valueOf(day), callBack);
	}
}
