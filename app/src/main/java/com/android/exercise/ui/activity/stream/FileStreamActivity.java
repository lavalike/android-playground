package com.android.exercise.ui.activity.stream;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.android.exercise.util.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * FileStreamActivity
 * Created by wangzhen on 2019/2/25.
 */
public class FileStreamActivity extends BaseActivity {

    @BindView(R.id.tv_content)
    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_stream);
        ButterKnife.bind(this);


        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "20190211160659.mp4");
        tvContent.append(file.getAbsolutePath());
        tvContent.append("\n");
        tvContent.append("file : " + getFileSize0(file));
        tvContent.append("\n");
        tvContent.append("stream : " + getFileSize1(file));
        tvContent.append("\n");

        file = new File(Environment.getExternalStorageDirectory() + File.separator + "1526587196650.png");
        tvContent.append(file.getAbsolutePath());
        tvContent.append("\n");
        tvContent.append("file : " + getFileSize0(file));
        tvContent.append("\n");
        tvContent.append("stream : " + getFileSize1(file));
        tvContent.append("\n");

        file = new File(Environment.getExternalStorageDirectory() + File.separator + "MM_UDID");
        tvContent.append(file.getAbsolutePath());
        tvContent.append("\n");
        tvContent.append("file : " + getFileSize0(file));
        tvContent.append("\n");
        tvContent.append("stream : " + getFileSize1(file));
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
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_stream));
    }
}
