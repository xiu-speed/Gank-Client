package com.speed.gank.adapters;

import java.util.List;
import java.util.Map;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.speed.gank.R;
import com.speed.gank.models.GankModel;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CurrentDataExAdapter extends BaseExpandableListAdapter {

	public Context mContext;
	public Map<String, List<GankModel>> map;
	
	public CurrentDataExAdapter(Context context, Map<String, List<GankModel>> map) {
		this.mContext = context;
		this.map = map;
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		String[] keys = map.keySet().toArray(new String[]{});
		return map.get(keys[groupPosition]).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	

	@Override
	public int getChildrenCount(int groupPosition) {
		String[] keys = map.keySet().toArray(new String[]{});
		return map.get(keys[groupPosition]).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		String[] keys = map.keySet().toArray(new String[0]);
		return keys[groupPosition];
	}

	@Override
	public int getGroupCount() {
		return map.keySet().size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		GroupViewHolder holder;
		if (convertView == null) {
			holder = new GroupViewHolder();
			convertView = ((Activity) mContext).getLayoutInflater().inflate(R.layout.item_current_data_group, null);
			ViewUtils.inject(holder, convertView);
			convertView.setTag(holder);
		} else {
			holder = (GroupViewHolder)convertView.getTag();
		}
		String name = (String) getGroup(groupPosition);
		holder.name.setText(name);
		holder.arrow.setImageResource(isExpanded ? R.mipmap.arrow_up : R.mipmap.arrow_down);
		return convertView;
	}
	
	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {
		ChildViewHolder holder;
		if (convertView == null) {
			holder = new ChildViewHolder();
			convertView = ((Activity) mContext).getLayoutInflater().inflate(R.layout.item_current_data_child, null);
			ViewUtils.inject(holder, convertView);
			convertView.setTag(holder);
		} else {
			holder = (ChildViewHolder)convertView.getTag();
		}
		GankModel gankModel = (GankModel) getChild(groupPosition, childPosition);
		holder.name.setText(gankModel.desc);
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return true;
	}

	public static class GroupViewHolder {
		@ViewInject(R.id.item_current_data_group)
		public TextView name;
		@ViewInject(R.id.item_current_data_group_arrow)
		public ImageView arrow;
	}

	public static class ChildViewHolder {
		@ViewInject(R.id.item_current_data_child)
		public TextView name;
	}

}
