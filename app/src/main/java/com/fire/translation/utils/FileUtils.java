package com.fire.translation.utils;

import android.app.Activity;
import android.app.Application;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import com.fire.baselibrary.utils.ToastUtils;
import com.fire.translation.TransApplication;
import com.orhanobut.logger.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import okhttp3.ResponseBody;

/**
 * Created by fire on 2018/1/12.
 * Date：2018/1/12
 * Author: fire
 * Description:
 */

public class FileUtils {
    /**
     *
     * @param filePath
     * @return
     */
    public static boolean isFolderExists(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        File file = new File(filePath);
        return file.isDirectory() && file.exists();
    }


    /**
     * 创建文件夹
     *
     * @throws IOException
     */
    public static boolean makeDirectory(String directory) {
        return makeDirectory(directory, true);
    }

    /**
     * 创建文件夹
     *
     * @throws IOException
     */
    public static boolean makeDirectory(String directory, boolean createParents) {
        boolean created;
        try {
            File dir = new File(directory);
            if (createParents) {
                created = dir.mkdirs();
            } else {
                if (dir.isFile()) {
                    created = dir.createNewFile();
                } else {
                    created = dir.mkdir();
                }
            }
        } catch (Exception e) {
            created = false;
        }
        return created;
    }

    /**
     * 生成文件路径和文件名
     * @return
     */
    public static String getFileName() {
        String saveDir = Environment.getExternalStorageDirectory() + "/translation";
        File dir = new File(saveDir);
        if (!dir.exists()) {
            dir.mkdir(); // 创建文件夹
        }
        // 用日期作为文件名，确保唯一性
        String fileName = saveDir + "/" + DateUtils.formatDateToString(new Date(),DateUtils.dateFormat3) + ".png";
        return fileName;
    }

    public static void copy(CharSequence text) {
        ClipboardManager cm = (ClipboardManager) TransApplication.mTransApp.getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(text);
        ToastUtils.showToast("复制成功");
    }

    public static boolean writeResponseBodyToDisk(Context context,ResponseBody body,String fileName) {
        try {
            File futureStudioIconFile = new File(context.getFilesDir() + File.separator + fileName);
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    Logger.e("file download: " + fileSizeDownloaded + " of " + fileSize);
                }
                outputStream.flush();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    public static void unZip(File file1,String outputDirectory) throws IOException {
        //创建解压目标目录
        File file = new File(outputDirectory);
        //如果目标目录不存在，则创建
        if (!file.exists()) {
            file.mkdirs();
        }
        InputStream inputStream = null;
        //打开压缩文件
        inputStream = new FileInputStream(file1);
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        //读取一个进入点
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        //使用1Mbuffer
        byte[] buffer = new byte[1024 * 1024];
        //解压时字节计数
        int count = 0;
        //如果进入点为空说明已经遍历完所有压缩包中文件和目录
        while (zipEntry != null) {
            //如果是一个目录
            if (zipEntry.isDirectory()) {
                file = new File(outputDirectory + File.separator + zipEntry.getName());
                file.mkdir();
            } else {
                //如果是文件
                file = new File(outputDirectory + File.separator + zipEntry.getName() + ".db");
                //创建该文件
                file.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                while ((count = zipInputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, count);
                }
                fileOutputStream.close();
            }
            //定位到下一个文件入口
            zipEntry = zipInputStream.getNextEntry();
        }
        zipInputStream.close();
    }

    /**
     * 获取和保存当前屏幕的截图
     */
    public static String GetandSaveCurrentImage(Activity activity) {
        //1.构建Bitmap
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int w = display.getWidth();
        int h = display.getHeight();
        Bitmap Bmp = Bitmap.createBitmap( w, h, Bitmap.Config.ARGB_8888 );
        //2.获取屏幕
        View decorview = activity.getWindow().getDecorView();
        decorview.setDrawingCacheEnabled(true);
        Bmp = decorview.getDrawingCache();
        String SavePath = activity.getFilesDir() + File.separator +"ScreenImage";
        String filepath = SavePath + "/Screen_1.png";
        //3.保存Bitmap
        try {
            File path = new File(SavePath);
            //文件
            File file = new File(filepath);
            if(!path.exists()){
                path.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = null;
            fos = new FileOutputStream(file);
            if (null != fos) {
                Bmp.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.flush();
                fos.close();
                ToastUtils.showToast("截屏文件已保存至内置存储中");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filepath;
    }
}
