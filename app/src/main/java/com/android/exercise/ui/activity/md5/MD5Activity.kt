package com.android.exercise.ui.activity.md5

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.toolbar.ToolBarCommonHolder
import com.android.exercise.util.MD5Utils
import kotlinx.android.synthetic.main.activity_m_d5.*
import java.io.File
import java.io.RandomAccessFile

/**
 * MD5Activity
 * Created by wangzhen on 2020/10/20.
 */
class MD5Activity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_m_d5)
    }

    override fun onSetupToolbar(toolbar: Toolbar?, actionBar: ActionBar?) {
        ToolBarCommonHolder(this, toolbar, getString(R.string.item_md5))
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_create_large_file -> {
                createLargeFile()
            }
            R.id.btn_file_md5 -> {
                getFileMd5()
            }
        }
    }

    private fun createLargeFile() {
        val start = System.currentTimeMillis()
        val file = File(getExternalFilesDir(null), "large_file.txt")
        val randomAccessFile = RandomAccessFile(file, "rw")
        randomAccessFile.setLength(1024 * 1024 * 512)
        randomAccessFile.close()
        tv_msg.text = "创建1GB文件成功，耗时：${System.currentTimeMillis() - start}"
    }

    private fun getFileMd5() {
        val file = File(getExternalFilesDir(null), "large_file.txt")
        if (!file.exists()) {
            Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show()
            return
        }
        val start = System.currentTimeMillis()
        val md5 = MD5Utils.getMD5Fast(file)
        tv_msg.text = "MD5 -> $md5\n"
        tv_msg.append("耗时 -> ${System.currentTimeMillis() - start}")
    }
}