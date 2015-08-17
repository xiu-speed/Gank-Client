package com.speed.gank.adapters;

import java.util.List;

import com.lidroid.xutils.BitmapUtils;
import com.speed.gank.activitys.PhotoActivity;
import com.speed.gank.utils.ScreenUtils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class CurrentDataGrilsAdapter extends PagerAdapter {

	private Context mContext;
	private List<String> mList;
	
	public CurrentDataGrilsAdapter(Context context, List<String> list) {
		this.mContext = context;
		this.mList = list;
	}
	
	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object o) {
		return view == o;
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		ImageView imageView = new ImageView(mContext);
		LayoutParams lp = new LayoutParams(ScreenUtils.getScreenWidth((Activity) mContext), (int) (ScreenUtils.getScreenWidth((Activity) mContext) * 1.5));
		imageView.setScaleType(ScaleType.CENTER_CROP);
		imageView.setLayoutParams(lp);
		new BitmapUtils(mContext).display(imageView, mList.get(position));
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mContext.startActivity(PhotoActivity.makeIntent(mContext, mList.get(position)));
			}
		});
		container.addView(imageView);
		
		return imageView;
	}
	
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	

}
