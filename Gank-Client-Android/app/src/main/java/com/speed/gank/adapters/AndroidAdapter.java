package com.speed.gank.adapters;

import java.util.List;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.speed.gank.R;
import com.speed.gank.models.GankModel;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AndroidAdapter extends BaseAdapter {

	private Activity activity;
	private List<GankModel> mList;
	
	public AndroidAdapter(Activity activity, List<GankModel> list) {
		this.activity = activity;
		this.mList = list;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		ViewHolder holder;
		if(view == null) {
			holder = new ViewHolder();
			view = activity.getLayoutInflater().inflate(R.layout.item_android, null);
			ViewUtils.inject(holder, view);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		GankModel model = (GankModel) getItem(position);
		holder.android_desc.setText(model.desc);
		return view;
	}

	public static class ViewHolder {
		@ViewInject(R.id.item_android_desc)
		public TextView android_desc;
	}

}
