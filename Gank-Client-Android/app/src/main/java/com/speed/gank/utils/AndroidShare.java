package com.speed.gank.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;

import java.io.File;
import java.util.List;


public class AndroidShare {
    public static final int SHARE_BY_SMS = 0xf0; // 短信
    public static final int SHARE_BY_WEIXIN = 0xf1; // 微信
    public static final int SHARE_BY_WEIXIN_MOMENTS = 0xf2; // 朋友圈
    public static final int SHARE_BY_QQ = 0xf3;// QQ
    public static final int SHARE_BY_QQ_QZONE = 0xf4;// QQ空间
    public static final int SHARE_BY_SINA_WEIBO = 0xf5;// 新浪微博
    public static final int SHARE_BY_TENCENT_WEIBO = 0xf6; // 腾讯微博


    private static boolean isAvilible(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> pis = pm.getInstalledPackages(0);
        for (PackageInfo i : pis) {
            if (i.packageName.equalsIgnoreCase(packageName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 调用系统分享功能
     * @param context
     * @param channel
     * @param title
     * @param msgText
     * @param imgPath
     * @param resultCode
     * @return false未安装相关的应用,true正确启动相关应用
     */
    public static boolean share(Context context, int channel, String title,
            String msgText, String imgPath, int resultCode) {
        try {
            if (channel == SHARE_BY_SMS) { // 短信分享
                Intent i = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"));
                i.putExtra("sms_body", msgText);
                if (context instanceof Activity) {
                    ((Activity) context).startActivityForResult(i, resultCode);
                }
                return true;
            } else {
                String packageName = getPackageNameByChannel(channel);
                if (isAvilible(context, packageName)) {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    if (TextUtils.isEmpty(imgPath)) {
                        i.setType("text/plain");
                    } else {
                        File f = new File(imgPath);
                        if (f != null && f.exists() && f.isFile()) {
                            i.setType("image/png");
                            Uri u = Uri.fromFile(f);
                            i.putExtra(Intent.EXTRA_STREAM, u);
                        }
                    }
                    i.putExtra(Intent.EXTRA_SUBJECT, title);
                    i.putExtra(Intent.EXTRA_TEXT, msgText);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.setComponent(getComponetNameByChannel(channel));
                    if (context instanceof Activity) {
                        ((Activity) context).startActivityForResult(i, resultCode);
                    }
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String getPackageNameByChannel(int channel) {
        switch (channel) {
            case SHARE_BY_WEIXIN:
                return "com.tencent.mm";
            case SHARE_BY_WEIXIN_MOMENTS:
                return "com.tencent.mm";
            case SHARE_BY_QQ:
                return "com.tencent.mobileqq";
            case SHARE_BY_QQ_QZONE:
                return "com.qzone";
            case SHARE_BY_SINA_WEIBO:
                return "com.sina.weibo";
            case SHARE_BY_TENCENT_WEIBO:
                return "com.tencent.WBlog";
            default:
                return null;
        }
    }

    private static ComponentName getComponetNameByChannel(int channel) {
        String packageName = getPackageNameByChannel(channel);
        String activityName = null;
        switch (channel) {
            case SHARE_BY_WEIXIN:
                activityName = "com.tencent.mm.ui.tools.ShareImgUI";
                break;
            case SHARE_BY_WEIXIN_MOMENTS:
                activityName = "com.tencent.mm.ui.tools.ShareToTimeLineUI";
                break;
            case SHARE_BY_QQ:
                activityName = "com.tencent.mobileqq.activity.JumpActivity";
                break;
            case SHARE_BY_QQ_QZONE:
                activityName = "com.qzone.ui.operation.QZonePublishMoodActivity";
                break;
            case SHARE_BY_SINA_WEIBO:
                activityName = "com.sina.weibo.EditActivity";
                break;
            case SHARE_BY_TENCENT_WEIBO:
                activityName = "com.tencent.WBlog.intentproxy.TencentWeiboIntent";
                break;
            default:
                break;
        }
        if (!TextUtils.isEmpty(packageName) && !TextUtils.isEmpty(activityName)) {
            return new ComponentName(packageName, activityName);
        } else {
            return null;
        }
    }


}
