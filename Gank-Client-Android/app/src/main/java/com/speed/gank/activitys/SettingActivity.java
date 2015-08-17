package com.speed.gank.activitys;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.speed.gank.componets.BaseActivity;
import com.speed.gank.R;
/**
 * Created by huben on 15/8/11.
 */
@ContentView(R.layout.activity_setting)
public class SettingActivity extends BaseActivity {


    @Override
    public void initView() {
        ViewUtils.inject(this);
        getActionBar().setTitle("设置");
    }

    @Override
    public void initData() {

    }
}
