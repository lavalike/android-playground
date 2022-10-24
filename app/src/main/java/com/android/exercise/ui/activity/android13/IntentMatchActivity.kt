package com.android.exercise.ui.activity.android13

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.toolbar.ToolBarCommonHolder
import com.android.exercise.databinding.ActivityIntentMatchBinding

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

    override fun onSetupToolbar(toolbar: Toolbar?, actionBar: ActionBar?) {
        ToolBarCommonHolder(this, toolbar, getString(R.string.item_intent_match))
    }
}