package com.speed.gank.activitys;

import android.os.Bundle;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.speed.gank.componets.BaseActivity;
import com.speed.gank.R;
/**
 * Created by huben on 15/8/11.
 */
@ContentView(R.layout.activity_about)
public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        ViewUtils.inject(this);
        getActionBar().setTitle("关于");
    }

    @Override
    public void initData() {

    }
}
