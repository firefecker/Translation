package com.fire.translation.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import java.net.URISyntaxException;

/**
 * Created by fire on 2018/1/23.
 * Date：2018/1/23
 * Author: fire
 * Description:
 */

public class IntentUtils {

    // 支付宝包名
    private static final String ALIPAY_PACKAGE_NAME = "com.eg.android.AlipayGphone";

    // 旧版支付宝二维码通用 Intent Scheme Url 格式
    private static final String INTENT_URL_FORMAT = "intent://platformapi/startapp?saId=10000007&"
                                                    +
                                                    "clientVersion=3.7.0.0718&qrcode=https%3A%2F%2Fqr.alipay.com%2F{urlCode}%3F_s"
                                                    +
                                                    "%3Dweb-other&_t=1472443966571#Intent;"
                                                    +
                                                    "scheme=alipayqr;package=com.eg.android"
                                                    + ".AlipayGphone;end";

    /**
     * 系统分享的intent
     */
    public static Intent openSystemShare(String content) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.setType("text/plain");
        return Intent.createChooser(intent, "分享到");
    }

    /**
     * 打开转账窗口
     * @param activity Parent Activity
     * @param urlCode 手动解析二维码获得地址中的参数，例如 https://qr.alipay.com/aehvyvf4taua18zo6e 最后那段
     * @return 是否成功调用
     */
    public static boolean startAlipayClient(Activity activity, String urlCode) {
        return startIntentUrl(activity, INTENT_URL_FORMAT.replace("{urlCode}", urlCode));
    }

    /**
     * 打开 Intent Scheme Url
     * @param activity Parent Activity
     * @param intentFullUrl Intent 跳转地址
     * @return 是否成功调用
     */
    public static boolean startIntentUrl(Activity activity, String intentFullUrl) {
        try {
            Intent intent = Intent.parseUri(
                    intentFullUrl,
                    Intent.URI_INTENT_SCHEME
            );
            activity.startActivity(intent);
            return true;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return false;
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断支付宝客户端是否已安装，建议调用转账前检查
     * @param context 上下文
     */
    public static boolean hasInstalledAlipayClient(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = null;
            info = pm.getPackageInfo(ALIPAY_PACKAGE_NAME, 0);
            return info != null;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
