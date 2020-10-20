package com.android.exercise.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;

/**
 * MD5Utils
 * Created by wangzhen on 2020/10/20.
 */
public class MD5Utils {
    private static final char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 获取一个文件的md5值(可处理大文件)
     *
     * @return md5 value
     */
    public static String getMD5(File file) {
        FileInputStream fileInputStream = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                digest.update(buffer, 0, length);
            }
            byte[] md = digest.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (byte b : md) {
                str[k++] = hexDigits[b >>> 4 & 0xf];
                str[k++] = hexDigits[b & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
