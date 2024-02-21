package com.android.exercise.ui.activity.stream;

import android.os.Bundle;
import android.os.Environment;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.android.exercise.databinding.ActivityFileStreamBinding;
import com.android.exercise.util.IOUtils;
import com.wangzhen.commons.toolbar.impl.Toolbar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * FileStreamActivity
 * Created by wangzhen on 2019/2/25.
 */
public class FileStreamActivity extends BaseActivity {
    private ActivityFileStreamBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityFileStreamBinding.inflate(getLayoutInflater())).getRoot());

        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "20190211160659.mp4");
        binding.tvContent.append(file.getAbsolutePath());
        binding.tvContent.append("\n");
        binding.tvContent.append("file : " + getFileSize0(file));
        binding.tvContent.append("\n");
        binding.tvContent.append("stream : " + getFileSize1(file));
        binding.tvContent.append("\n");

        file = new File(Environment.getExternalStorageDirectory() + File.separator + "1526587196650.png");
        binding.tvContent.append(file.getAbsolutePath());
        binding.tvContent.append("\n");
        binding.tvContent.append("file : " + getFileSize0(file));
        binding.tvContent.append("\n");
        binding.tvContent.append("stream : " + getFileSize1(file));
        binding.tvContent.append("\n");

        file = new File(Environment.getExternalStorageDirectory() + File.separator + "MM_UDID");
        binding.tvContent.append(file.getAbsolutePath());
        binding.tvContent.append("\n");
        binding.tvContent.append("file : " + getFileSize0(file));
        binding.tvContent.append("\n");
        binding.tvContent.append("stream : " + getFileSize1(file));
    }

    /**
     * 获取文件大小（通过file.length()）
     *
     * @param file file
     * @return in bytes
     */
    private long getFileSize0(File file) {
        long size = 0;
        if (file == null) return size;
        if (file.exists()) {
            size = file.length();
        }
        return size;
    }

    /**
     * 获取文件大小（通过stream.available()）
     *
     * @param file file
     * @return in bytes
     */
    private long getFileSize1(File file) {
        long size = 0;
        if (file == null) return size;
        if (file.exists()) {
            FileInputStream stream = null;
            try {
                stream = new FileInputStream(file);
                size = stream.available();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.close(stream);
            }
        }
        return size;
    }

    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_stream));
    }
}
