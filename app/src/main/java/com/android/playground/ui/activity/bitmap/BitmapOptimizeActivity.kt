package com.android.playground.ui.activity.bitmap

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import com.android.playground.R
import com.android.playground.base.BaseActivity
import com.android.playground.base.toolbar.ToolbarFactory
import com.android.playground.databinding.ActivityBitmapOptimizeBinding
import com.wangzhen.commons.toolbar.impl.Toolbar

/**
 * 大图加载
 * 1、使用图片压缩加载超大图片，细节不清晰
 * 2、使用BitmapRegionDecoder加载
 * Created by wangzhen on 2020/7/8.
 */
class BitmapOptimizeActivity : BaseActivity() {
    private lateinit var binding: ActivityBitmapOptimizeBinding
    private var mBitmap: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBitmapOptimizeBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        binding.ivInBitmap.setImageBitmap(bitmap)
        binding.tvInBitmap.text = String.format("内存重用(%s)", formatSize(bitmap))
    }

    private fun inSampleSize() {
        val options = BitmapFactory.Options()
        options.inSampleSize = 2
        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.bicycle, options)
        binding.ivInSampleSize.setImageBitmap(bitmap)
        binding.tvInSampleSize.text =
            String.format("修改采样率(%s)\ninSampleSize = 2\n大小缩小1/2\n质量缩小1/4", formatSize(bitmap))
    }

    private fun raw() {
        mBitmap = BitmapFactory.decodeResource(resources, R.mipmap.bicycle)
        binding.ivRaw.setImageBitmap(mBitmap)
        binding.tvRaw.text = String.format("原图(%s)", formatSize(mBitmap))
    }

    private fun formatSize(bitmap: Bitmap?): String {
        if (bitmap != null) {
            return String.format("%.2fMB", bitmap.allocationByteCount * 1f / 1024 / 1024)
        }
        return "unknown"
    }

    override fun createToolbar(): Toolbar {
        return ToolbarFactory.themed(this, getString(R.string.item_bitmap_optimize))
    }
}