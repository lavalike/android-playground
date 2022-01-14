package com.android.exercise.ui.activity.bitmap

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.toolbar.ToolBarCommonHolder
import com.android.exercise.databinding.ActivityLargeImageBinding
import com.android.exercise.ui.activity.bitmap.regiondecoder.BitmapRegionDecoderActivity
import com.android.exercise.ui.activity.bitmap.subsampling.SubsamplingScaleActivity

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
                        it.context,
                        BitmapRegionDecoderActivity::class.java
                    )
                )
            }
            btnSubsampling.setOnClickListener {
                startActivity(
                    Intent(
                        it.context,
                        SubsamplingScaleActivity::class.java
                    )
                )
            }
        }
    }

    override fun onSetupToolbar(toolbar: Toolbar?, actionBar: ActionBar?) {
        ToolBarCommonHolder(this, toolbar, "大图加载方案")
    }
}