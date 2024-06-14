package com.android.playground.ui.activity.android13

import android.os.Build
import android.os.Bundle
import android.window.OnBackInvokedCallback
import android.window.OnBackInvokedDispatcher
import com.android.playground.R
import com.android.playground.base.BaseActivity
import com.android.playground.base.toolbar.ToolbarFactory
import com.android.playground.databinding.ActivityOnBackInvokedCallbackBinding

/**
 * OnBackInvokedCallbackActivity
 * Created by wangzhen on 2023/7/28
 */
class OnBackInvokedCallbackActivity : BaseActivity() {
    private lateinit var binding: ActivityOnBackInvokedCallbackBinding
    private var callback: OnBackInvokedCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBackInvokedCallbackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            callback = OnBackInvokedCallback {
                println("On Back Invoked")
                onBackPressedDispatcher.onBackPressed()
            }.also {
                onBackInvokedDispatcher.registerOnBackInvokedCallback(
                    OnBackInvokedDispatcher.PRIORITY_DEFAULT, it
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            callback?.let {
                onBackInvokedDispatcher.unregisterOnBackInvokedCallback(it)
            }
        }
    }

    override fun createToolbar() = ToolbarFactory.themed(
        this, getString(R.string.item_android13_back_invoked_callback)
    )
}