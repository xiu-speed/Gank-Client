package com.speed.gank.adapters;

import java.util.List;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.speed.gank.R;
import com.speed.gank.activitys.PhotoActivity;
import com.speed.gank.models.GankModel;
import com.speed.gank.utils.LogUtils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class GirlsAdapter extends
		RecyclerView.Adapter<GirlsAdapter.GirlsViewHolder> {

	private static Context mContext;
	private static List<GankModel> mList;

	public GirlsAdapter(Context context, List<GankModel> list) {
		this.mContext = context;
		this.mList = list;
	}

	@Override
	public int getItemCount() {
		return mList.size();
	}

	@Override
	public void onBindViewHolder(GirlsViewHolder viewHolder, int position) {
		BitmapUtils bitmapUtils = new BitmapUtils(mContext);
		bitmapUtils.display(viewHolder.imageView, mList.get(position).url);
	}

	@Override
	public GirlsViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_girls, viewGroup, false);
		return new GirlsViewHolder(view);
	}



	public static class GirlsViewHolder extends RecyclerView.ViewHolder {

		public ImageView imageView;

		public GirlsViewHolder(View itemView) {
			super(itemView);
			imageView = (ImageView) itemView.findViewById(R.id.item_girls_im);
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					LogUtils.d("jwzhangjie当前点击的位置：" + getPosition());
					mContext.startActivity(PhotoActivity.makeIntent(mContext, mList.get(getPosition()).url));
				}
			});
		}

	}

}
