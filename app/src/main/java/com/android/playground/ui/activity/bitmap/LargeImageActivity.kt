package com.android.playground.ui.activity.bitmap

import android.content.Intent
import android.os.Bundle
import com.android.playground.base.BaseActivity
import com.android.playground.base.toolbar.ToolbarFactory
import com.android.playground.databinding.ActivityLargeImageBinding
import com.android.playground.ui.activity.bitmap.regiondecoder.BitmapRegionDecoderActivity
import com.android.playground.ui.activity.bitmap.subsampling.SubsamplingScaleActivity
import com.wangzhen.commons.toolbar.impl.Toolbar

/**
 * 大图加载
 * 使用BitmapRegionDecoder加载巨图，细节清晰
 * Created by wangzhen on 2020/7/9.
 */
class LargeImageActivity : BaseActivity() {
    lateinit var binding: ActivityLargeImageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLargeImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btnRegionDecoder.setOnClickListener {
                startActivity(
                    Intent(
                        it.context, BitmapRegionDecoderActivity::class.java
                    )
                )
            }
            btnSubsampling.setOnClickListener {
                startActivity(
                    Intent(
                        it.context, SubsamplingScaleActivity::class.java
                    )
                )
            }
        }
    }

    override fun createToolbar(): Toolbar {
        return ToolbarFactory.themed(this, "大图加载方案")
    }
}