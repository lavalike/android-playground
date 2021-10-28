package com.android.exercise.ui.activity.shared_elements

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.databinding.ActivitySharedDetailBinding

/**
 * 共享元素详情
 * Created by wangzhen on 2020/7/23.
 */
class SharedDetailActivity : BaseActivity() {
    private lateinit var binding: ActivitySharedDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySharedDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.tvTitle.text = intent.getStringExtra("title")
        binding.imageView.setImageResource(intent.getIntExtra("resId", R.mipmap.ic_launcher))
    }

    override fun showToolbar(): Boolean {
        return false
    }
}