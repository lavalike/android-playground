package com.android.exercise.ui.activity.android13

import android.content.Intent
import android.os.Bundle
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.toolbar.ToolbarFactory
import com.android.exercise.databinding.ActivityIntentMatchBinding
import com.wangzhen.commons.toolbar.impl.Toolbar

/**
 * IntentMatchActivity
 * @author: zhen51.wang
 * @date: 2022/10/21/021
 */
class IntentMatchActivity : BaseActivity() {
    private lateinit var binding: ActivityIntentMatchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntentMatchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            btnOpenPermissions.setOnClickListener {
                val intent = Intent("com.android.exercise.android13.permissions").apply {
                    setPackage(packageName)
                }
                startActivity(intent)
            }
        }
    }

    override fun createToolbar(): Toolbar {
        return ToolbarFactory.themed(this, getString(R.string.item_intent_match))
    }
}