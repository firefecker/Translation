package com.fire.translation.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.content.FileProvider;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import com.fire.translation.constant.Constant;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 *
 * @author fire
 * @date 2018/1/15
 * Description:
 */

public class FunctionUtils {

    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());
        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    /**
     * 拍照
     * @param context
     * @return
     */
    public static Intent getNormalPictureIntent(Context context) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri imageUri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = FileProvider.getUriForFile(context, "com.fire.translation.ui.fileprovider", getImageFile());
        } else {
            imageUri = Uri.fromFile(getImageFile());
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        return intent;
    }

    /**
     * 调用相册
     * @return
     */
    public static Intent getAlbumIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        return intent;
    }

    public static File getImageFile() {
        File file = new File((SDCardUtils.isSDCardEnable() ? Constant.SDCARD_DIRECTORY : Constant.PHONE_DIRECTORY));
        if (!file.exists()) {
            file.mkdirs();
        }
        File imgFile = new File(file, "translation.jpg");
        return imgFile;
    }

    public static Bitmap getNormalBitmap(File file) {
        FileInputStream fis = null;
        Bitmap bitmap = null;
        try {
            if (file.exists()) {
                fis = new FileInputStream(file.getPath());
                bitmap = BitmapFactory.decodeStream(fis);
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return bitmap;
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    public static boolean isInMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static Bitmap readBitmapFromFile(String filePath, int size) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;
        int inSampleSize = 1;
        if (srcHeight > size || srcWidth > size) {
            if (srcWidth < srcHeight) {
                inSampleSize = Math.round(srcHeight / size);
            } else {
                inSampleSize = Math.round(srcWidth / size);
            }
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;
        return BitmapFactory.decodeFile(filePath, options);
    }


}
