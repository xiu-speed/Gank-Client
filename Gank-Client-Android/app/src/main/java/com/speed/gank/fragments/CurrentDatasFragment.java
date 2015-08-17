package com.speed.gank.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.speed.gank.R;
import com.speed.gank.activitys.MediaPlayerActivity;
import com.speed.gank.adapters.CurrentDataExAdapter;
import com.speed.gank.adapters.CurrentDataGrilsAdapter;
import com.speed.gank.componets.BaseFragment;
import com.speed.gank.models.GankModel;
import com.speed.gank.models.RequestModel;
import com.speed.gank.utils.HttpOperation;
import com.speed.gank.utils.JsonUtils;
import com.speed.gank.utils.LogUtils;
import com.speed.gank.utils.ToastUtils;

public class CurrentDatasFragment extends BaseFragment {

    View view;
    Callback callback;
    boolean firstTime = true;

    @ViewInject(R.id.current_data_viewpager)
    private ViewPager viewPager;
    PagerAdapter viewPagerAdapter;
    List<String> list = new ArrayList<String>();

    @ViewInject(R.id.current_data_exlistview)
    private ExpandableListView exListView;
    BaseExpandableListAdapter exListViewAdapter;
    Map<String, List<GankModel>> dataMap = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_current_data, container,
                false);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void initView() {
        viewPagerAdapter = new CurrentDataGrilsAdapter(getActivity(), list);
        viewPager.setAdapter(viewPagerAdapter);
        exListViewAdapter = new CurrentDataExAdapter(getActivity(), dataMap);
        exListView.setGroupIndicator(null);
        exListView.setAdapter(exListViewAdapter);
        exListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                String url = ((GankModel) exListViewAdapter.getChild(groupPosition, childPosition)).url;
                geWeb(url);
                return true;
            }
        });
    }

    @Override
    public void initData() {
        showLoading();
        callback = new Callback();
        HttpOperation.shareHttpOperation().getCurrentDatas(callback);
    }

    class Callback extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> response) {
            hideLoading();
            Map<String, List<GankModel>> map = JsonUtils.getCurrentData(response.result);
            Log.d("fragment", map.toString());
            list.clear();
            List<GankModel> grils = map.get("福利");
            if (grils == null || grils.size() == 0) {
                if (firstTime) {
                    firstTime = false;
                    HttpOperation.shareHttpOperation().getLastDatas(callback);
                }
            } else {
                for (GankModel gankModel : grils) {
                    list.add(gankModel.url);
                }

            }
            map.remove("福利");
            dataMap.putAll(map);
            LogUtils.d(dataMap.toString());
            viewPagerAdapter.notifyDataSetChanged();
            exListViewAdapter.notifyDataSetChanged();
        }

        @Override
        public void onFailure(HttpException e, String arg1) {
            hideLoading();
            ToastUtils.show(getActivity(), "网络数据错误");
        }

    }

}
