package com.android.exercise.ui.activity.shared_elements

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import kotlinx.android.synthetic.main.activity_shared_detail.*

/**
 * 共享元素详情
 * Created by wangzhen on 2020/7/23.
 */
class SharedDetailActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_detail)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        tv_title.text = intent.getStringExtra("title")
        imageView.setImageResource(intent.getIntExtra("resId", R.mipmap.ic_launcher))
    }

    override fun showToolbar(): Boolean {
        return false
    }
}