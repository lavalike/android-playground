package com.android.exercise.ui.activity.motion

import android.os.Bundle
import com.android.exercise.base.BaseActivity
import com.android.exercise.databinding.ActivityLoadingBallBinding

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

    override fun showToolbar(): Boolean {
        return false
    }
}