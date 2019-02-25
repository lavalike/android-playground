package com.android.exercise.util.base64;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Base64Util
 * Created by wangzhen on 2019/2/25.
 */
public class Base64Util {
    /**
     * 将本地图片转换为Base64String
     *
     * @param imageUrl 图片路径
     * @param quality  图片质量限制 单位:KB
     * @return Base64String
     */
    @Nullable
    public static String convertImage2Base64(String imageUrl, int quality) {
        Bitmap bitmap = BitmapFactory.decodeFile(imageUrl);
        return convertImage2Base64(bitmap, quality);
    }

    public static String convertImage2Base64(Bitmap bitmap, int quality) {
        if (bitmap != null) {
            ByteArrayOutputStream out = null;
            try {
                out = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                //质量压缩
                int options = 90;
                while (out.toByteArray().length / 1024 > quality) {
                    out.reset();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, options, out);
                    options -= 10;
                }
                out.flush();
                out.close();
                return Base64.encodeToString(out.toByteArray(), Base64.DEFAULT);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.flush();
                        out.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    
    /**
     * 将文件转为Base64String
     *
     * @param url 文件路径
     * @return Base64String
     */
    @Nullable
    public static String convertFile2Base64(String url) {
        FileInputStream fis = null;
        ByteArrayOutputStream baos = null;
        try {
            fis = new FileInputStream(url);
            baos = new ByteArrayOutputStream();
            int len;
            byte[] bytes = new byte[1024];
            while ((len = fis.read(bytes)) != -1) {
                baos.write(bytes, 0, len);
            }
            return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (baos != null) {
                try {
                    baos.flush();
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
