package com.speed.gank.componets;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment {

	private ProgressDialog progress;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		initData();
	}
	
	public abstract void initView();
	
	public abstract void initData();
	
	public void showLoading() {
		showLoading(true);
	}
	public void showLoading(boolean cancel) {
		if (progress == null) {
			progress = new ProgressDialog(getActivity());
			progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progress.setMessage("数据加载中...");
			progress.setCancelable(cancel);
		}
		if (!progress.isShowing()) {
			try {
				progress.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void hideLoading() {
		if (progress != null && progress.isShowing()) {
			progress.dismiss();
		}
	}

	public void geWeb(String url) {
		Uri uri = Uri.parse(url);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}
}
