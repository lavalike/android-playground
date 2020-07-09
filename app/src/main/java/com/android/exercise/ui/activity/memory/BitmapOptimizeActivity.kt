package com.android.exercise.ui.activity.memory

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.toolbar.ToolBarCommonHolder
import kotlinx.android.synthetic.main.activity_bitmap_optimize.*

/**
 * 大图加载
 * 1、使用图片压缩加载超大图片，细节不清晰
 * 2、使用BitmapRegionDecoder加载
 * Created by wangzhen on 2020/7/8.
 */
class BitmapOptimizeActivity : BaseActivity() {
    private var mBitmap: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bitmap_optimize)

        raw()
        inSampleSize()
        inBitmap()
    }

    private fun inBitmap() {
        val options = BitmapFactory.Options()
        options.inBitmap = mBitmap
        options.inMutable = true
        options.inSampleSize = 1
        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.bicycle, options)
        iv_in_bitmap.setImageBitmap(bitmap)
        tv_in_bitmap.text = String.format("内存重用(%s)", formatSize(bitmap))
    }

    private fun inSampleSize() {
        val options = BitmapFactory.Options()
        options.inSampleSize = 2
        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.bicycle, options)
        iv_in_sample_size.setImageBitmap(bitmap)
        tv_in_sample_size.text = String.format("修改采样率(%s)\ninSampleSize = 2\n大小缩小1/2\n质量缩小1/4", formatSize(bitmap))
    }

    private fun raw() {
        mBitmap = BitmapFactory.decodeResource(resources, R.mipmap.bicycle)
        iv_raw.setImageBitmap(mBitmap)
        tv_raw.text = String.format("原图(%s)", formatSize(mBitmap))
    }

    private fun formatSize(bitmap: Bitmap?): String {
        if (bitmap != null) {
            return String.format("%.2fMB", bitmap.allocationByteCount * 1f / 1024 / 1024)
        }
        return "unknown"
    }

    override fun onSetupToolbar(toolbar: Toolbar, actionBar: ActionBar) {
        ToolBarCommonHolder(this, toolbar, getString(R.string.item_bitmap_optimize))
    }
}