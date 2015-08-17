package com.speed.gank.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.speed.gank.R;
import com.speed.gank.adapters.GirlsAdapter;
import com.speed.gank.componets.BaseFragment;
import com.speed.gank.models.GankModel;
import com.speed.gank.models.RequestModel;
import com.speed.gank.models.RequestModel.DATATYPE;
import com.speed.gank.utils.HttpOperation;
import com.speed.gank.utils.JsonUtils;
import com.speed.gank.utils.LogUtils;

public class GirlsFragment extends BaseFragment implements OnRefreshListener {
	@ViewInject(R.id.girls_swipe_layout)
	private SwipeRefreshLayout mSwipeLayout;
	@ViewInject(R.id.girls_list)
	private RecyclerView recyclerView;
	private GirlsAdapter adapter;
	private List<GankModel> mList = new ArrayList<GankModel>();
	private int currentPage = 1;
	private boolean loading = true;
	int pastVisiblesItems, visibleItemCount, totalItemCount;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_girls, container, false);
		ViewUtils.inject(this, view);
		return view;
	}
	
	@Override
	public void initView() {
		
		mSwipeLayout.setOnRefreshListener(this);
		final GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
		recyclerView.setLayoutManager(gridLayoutManager);
		adapter = new GirlsAdapter(getActivity(), mList);
		recyclerView.setAdapter(adapter);
		recyclerView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

				visibleItemCount = gridLayoutManager.getChildCount();
				totalItemCount = gridLayoutManager.getItemCount();
				pastVisiblesItems = gridLayoutManager.findFirstVisibleItemPosition();
				if (loading) {
					if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
						loading = false;
						currentPage++;
						requestData(currentPage);
					}
				}
			}

		});

	}

	@Override
	public void initData() {
		requestData(currentPage);
	}
	private void requestData(int page) {
		RequestModel model = new RequestModel();
		model.dataType = DATATYPE.girl;
		model.page = page;
		model.pageSize = 10;
		
		HttpOperation.shareHttpOperation().getDatas(model, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> response) {
				mSwipeLayout.setRefreshing(false);
				if (currentPage > 1) {
					loading = true;
				}
				List<GankModel> list = JsonUtils.json2AndroidGanks(response.result);
				if (list != null) {
					LogUtils.d(list.toString());
					mList.addAll(list);
					adapter.notifyDataSetChanged();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				mSwipeLayout.setRefreshing(false);
				if (currentPage > 1) {
					loading = true;
				}
			}
		});
	}

	@Override
	public void onRefresh() {
		mList.clear();
		requestData(1);
	}

}
