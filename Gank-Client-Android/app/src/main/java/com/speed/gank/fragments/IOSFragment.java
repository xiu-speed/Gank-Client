package com.speed.gank.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.speed.gank.R;
import com.speed.gank.adapters.AndroidAdapter;
import com.speed.gank.componets.BaseFragment;
import com.speed.gank.models.GankModel;
import com.speed.gank.models.RequestModel;
import com.speed.gank.models.RequestModel.DATATYPE;
import com.speed.gank.utils.HttpOperation;
import com.speed.gank.utils.JsonUtils;
import com.speed.gank.utils.LogUtils;
import com.speed.gank.utils.ToastUtils;
import com.speed.gank.widget.RefreshLayout;
import com.speed.gank.widget.RefreshLayout.OnLoadListener;

import java.util.ArrayList;
import java.util.List;

public class IOSFragment extends BaseFragment implements OnRefreshListener, OnLoadListener {
	
	@ViewInject(R.id.ios_swipe_layout)
	private RefreshLayout mSwipeLayout;
	@ViewInject(R.id.ios_listview)
	private ListView listView;
	BaseAdapter adapter;
	List<GankModel> mList = new ArrayList<GankModel>();
	private int currentPage = 1;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_ios, container, false);
		ViewUtils.inject(this, view);
		return view;
	}
	
	
	@Override
	public void initView() {
		mSwipeLayout.setOnRefreshListener(this); 
		mSwipeLayout.setOnLoadListener(this);
		adapter = new AndroidAdapter(getActivity(), mList);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				geWeb(mList.get(position).url);
			}
		});
	}

	@Override
	public void initData() {
		showLoading();
		requestData(currentPage);
	}

	private void requestData(int page) {
		RequestModel model = new RequestModel();
		model.dataType = DATATYPE.ios;
		model.page = page;
		model.pageSize = 10;
		
		HttpOperation.shareHttpOperation().getDatas(model, new RequestCallBack<String>() {
			
			@Override
			public void onSuccess(ResponseInfo<String> response) {
				hideLoading();
				mSwipeLayout.setRefreshing(false);
				if(currentPage > 1){
				    mSwipeLayout.setLoading(false);
				}
				List<GankModel> list = JsonUtils.json2AndroidGanks(response.result);
				if (list != null && list.size() > 0) {
					LogUtils.d(list.toString());
					mList.addAll(list);
					adapter.notifyDataSetChanged();
				}else{
					ToastUtils.show(getActivity(), "没有更多了");
				}
			}
			
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				hideLoading();
				ToastUtils.show(getActivity(), "网络数据错误");
				mSwipeLayout.setRefreshing(false);
				if(currentPage > 1){
				    mSwipeLayout.setLoading(false);
				}
			}
		});
	}

	@Override
	public void onRefresh() {
		mList.clear();
		requestData(1);
	}


	@Override
	public void onLoad() {
		currentPage ++;
		requestData(currentPage);
	}

}
