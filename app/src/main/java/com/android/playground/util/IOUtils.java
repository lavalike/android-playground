package com.android.playground.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * IOUtils
 * Created by wangzhen on 2019/2/25.
 */
public class IOUtils {

    /**
     * 关闭流
     *
     * @param closeables closeables
     */
    public static void close(Closeable... closeables) {
        if (closeables != null && closeables.length > 0) {
            for (Closeable closeable : closeables) {
                if (closeable != null) {
                    try {
                        closeable.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
