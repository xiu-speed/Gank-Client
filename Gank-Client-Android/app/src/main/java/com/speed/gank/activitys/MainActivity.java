package com.speed.gank.activitys;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.speed.gank.R;
import com.speed.gank.componets.BaseActivity;
import com.speed.gank.fragments.AllFragment;
import com.speed.gank.fragments.AndroidFragment;
import com.speed.gank.fragments.CurrentDatasFragment;
import com.speed.gank.fragments.ExtResFragment;
import com.speed.gank.fragments.GirlsFragment;
import com.speed.gank.fragments.IOSFragment;
import com.speed.gank.fragments.UIFragment;
import com.speed.gank.fragments.VedioFragment;
import com.speed.gank.models.RequestModel;
import com.speed.gank.utils.LogUtils;
import com.speed.gank.utils.TimeUtils;
import com.speed.gank.utils.ToastUtils;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;


@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    private long currentTime;

    @ViewInject(R.id.main_container)
    private FrameLayout frameLayout;

    @ViewInject(R.id.drawer_layout)
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        ViewUtils.inject(this);
        getActionBar().setTitle("Gank");
        getActionBar().setSubtitle(TimeUtils.getYear() + "/" + TimeUtils.getMonth() + "/" + TimeUtils.getDay());
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.mipmap.ic_launcher, R.string.drawer_open,
                R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
            }
        };
        //drawerLayout.setDrawerShadow(R.mipmap.ic_launcher, GravityCompat.START);
        drawerLayout.setDrawerListener(mDrawerToggle);


        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.main_container, new CurrentDatasFragment());
        transaction.commit();
    }

    @Override
    public void initData() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            //如果ActionBar设置setDisplayHomeAsUpEnabled为true，则可以监听到前面的箭头图标
            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.closeDrawers();
                } else {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
                break;
            case R.id.action_settings:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case R.id.action_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.action_share:
                startActivity(ShareActivity.makeIntent(this, "干货集中营", "每日分享妹子图 和 技术干货，还有供大家中午休息的休闲视频。", "http://gank.io"));
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @OnClick(R.id.drawer_girls)
    public void clickDrawerGirls(View v) {
        drawerLayout.closeDrawers();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new GirlsFragment()).commit();
        getActionBar().setSubtitle(RequestModel.DATATYPE.girl);
    }

    @OnClick(R.id.drawer_android)
    public void clickDrawerAndroid(View v) {
        drawerLayout.closeDrawers();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new AndroidFragment()).commit();
        getActionBar().setSubtitle(RequestModel.DATATYPE.android);
    }

    @OnClick(R.id.drawer_ios)
    public void clickDraweriOS(View v) {
        drawerLayout.closeDrawers();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new IOSFragment()).commit();
        getActionBar().setSubtitle(RequestModel.DATATYPE.ios);
    }

    @OnClick(R.id.drawer_vedio)
    public void clickDrawerVedio(View v) {
        drawerLayout.closeDrawers();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new VedioFragment()).commit();
        getActionBar().setSubtitle(RequestModel.DATATYPE.video);
    }

    @OnClick(R.id.drawer_ext)
    public void clickDrawerExtRes(View v) {
        drawerLayout.closeDrawers();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new ExtResFragment()).commit();
        getActionBar().setSubtitle(RequestModel.DATATYPE.ext_res);
    }

    @OnClick(R.id.drawer_ui)
    public void clickDrawerUi(View v) {
        drawerLayout.closeDrawers();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new UIFragment()).commit();
        getActionBar().setSubtitle(RequestModel.DATATYPE.ui);
    }

    @OnClick(R.id.drawer_all)
    public void clickDrawerAll(View v) {
        drawerLayout.closeDrawers();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new AllFragment()).commit();
        getActionBar().setSubtitle(RequestModel.DATATYPE.all);
    }


    @OnClick(R.id.github)
    public void clickGithub(View v) {
        drawerLayout.closeDrawers();
        geWeb("https://github.com/huben");
    }


    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - currentTime) > 3000) {
            ToastUtils.show(this, "再按一次退出程序");
            currentTime = System.currentTimeMillis();
        } else {
            this.finish();
            super.onBackPressed();
        }

    }
}
