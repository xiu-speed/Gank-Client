package com.speed.gank.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.speed.gank.R;
import com.speed.gank.utils.AndroidShare;

/**
 * Created by huben on 15/8/15.
 */
@ContentView(R.layout.activity_share)
public class ShareActivity extends Activity {

    private String title;
    private String msg;
    private String imgUrl;

    private static final String TITLE = "title";
    private static final String MSG = "msg";
    private static final String IMG_URL = "img_url";

    public static Intent makeIntent(Context context, String title, String msg, String imgUrl) {
        Intent intent = new Intent();
        intent.setClass(context, ShareActivity.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(MSG, msg);
        intent.putExtra(IMG_URL, imgUrl);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initView();
        Intent intent = getIntent();
        title = intent.getStringExtra(TITLE);
        msg = intent.getStringExtra(MSG);
        imgUrl = intent.getStringExtra(IMG_URL);
    }

    public void initView() {
        ViewUtils.inject(this);
    }

    @OnClick(R.id.share_wechat)
    public void clickWechat(View view) {
        AndroidShare.share(this, AndroidShare.SHARE_BY_WEIXIN, title, msg, imgUrl, 0);
        finish();
    }

    @OnClick(R.id.share_friends_groups)
    public void clickFriendsGroup(View view) {
        AndroidShare.share(this, AndroidShare.SHARE_BY_WEIXIN_MOMENTS, title, msg, imgUrl, 0);
        finish();
    }

    @OnClick(R.id.share_sina_weibo)
    public void clickWeibo(View view) {
        AndroidShare.share(this, AndroidShare.SHARE_BY_SINA_WEIBO, title, msg, imgUrl, 0);
        finish();
    }

    @OnClick(R.id.share_qq)
    public void clickQQ(View view) {
        AndroidShare.share(this, AndroidShare.SHARE_BY_QQ, title, msg, imgUrl, 0);
        finish();
    }

    @OnClick(R.id.share_qq_zone)
    public void clickQQZone(View view) {
        AndroidShare.share(this, AndroidShare.SHARE_BY_QQ_QZONE, title, msg, imgUrl, 0);
        finish();
    }

    @OnClick(R.id.share_msg)
    public void clickMsg(View view) {
        AndroidShare.share(this, AndroidShare.SHARE_BY_SMS, title, msg, imgUrl, 0);
        finish();
    }

}
