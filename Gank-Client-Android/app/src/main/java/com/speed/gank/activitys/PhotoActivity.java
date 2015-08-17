package com.speed.gank.activitys;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.speed.gank.R;
import com.speed.gank.componets.BaseActivity;
import com.speed.gank.utils.FileUtil;
import com.speed.gank.utils.ToastUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by huben on 15/8/11.
 */
@ContentView(R.layout.activity_photo)
public class PhotoActivity extends BaseActivity {
    private static final String PATH = "path";
    private String path;

    @ViewInject(R.id.photo_view)
    private PhotoView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        ViewUtils.inject(this);
        getActionBar().setTitle("福利");
    }

    @Override
    public void initData() {
        path = getIntent().getStringExtra(PATH);
        new BitmapUtils(this).display(photoView, path);
    }

    public static Intent makeIntent(Context context, String stringPath) {
        Intent intent = new Intent();
        intent.setClass(context, PhotoActivity.class);
        intent.putExtra(PATH, stringPath);
        return intent;
    }

    @OnClick(R.id.photo_view_save)
    public void clickViewSave(View v) {
        final File appDir = new File(Environment.getExternalStorageDirectory(), "Gank");
        new HttpUtils().download(path, Environment.getExternalStorageDirectory() + "/Gank/" + System.currentTimeMillis() + ".jpg", new RequestCallBack<File>() {
            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                try {
                    MediaStore.Images.Media.insertImage(PhotoActivity.this.getContentResolver(),
                            responseInfo.result.getAbsolutePath(), responseInfo.result.getName(), null);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                // 最后通知图库更新
                PhotoActivity.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(appDir)));
                ToastUtils.show(PhotoActivity.this, "保存图片成功");
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });

    }
}
