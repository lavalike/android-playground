package com.android.playground.ui.activity.motion

import android.os.Bundle
import com.android.playground.base.BaseActivity
import com.android.playground.databinding.ActivityLoadingBallBinding

/**
 * LoadingBallActivity
 * Created by wangzhen on 2021/9/2
 */
class LoadingBallActivity : BaseActivity() {
    private lateinit var binding: ActivityLoadingBallBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fitDarkStatus(true)
        setContentView(ActivityLoadingBallBinding.inflate(layoutInflater).apply {
            binding = this
        }.root)
    }
}