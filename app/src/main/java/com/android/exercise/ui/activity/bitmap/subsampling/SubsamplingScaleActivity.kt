package com.android.exercise.ui.activity.bitmap.subsampling

import android.os.Bundle
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.databinding.ActivitySubsamplingScaleBinding
import com.davemorrissey.labs.subscaleview.ImageSource

/**
 * SubsamplingScaleActivity
 * 参考：[https://github.com/davemorrissey/subsampling-scale-image-view]
 * Created by wangzhen on 2022/1/14
 */
class SubsamplingScaleActivity : BaseActivity() {
    private lateinit var binding: ActivitySubsamplingScaleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubsamplingScaleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.largeImage.setImage(ImageSource.resource(R.mipmap.bicycle))
    }
}