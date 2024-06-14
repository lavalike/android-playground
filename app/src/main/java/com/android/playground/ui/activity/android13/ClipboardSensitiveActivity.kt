package com.android.playground.ui.activity.android13

import android.content.ClipData
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import com.android.playground.R
import com.android.playground.base.BaseActivity
import com.android.playground.base.toolbar.ToolbarFactory
import com.android.playground.databinding.ActivityClipboardSensitiveBinding
import com.wangzhen.commons.toolbar.impl.Toolbar

/**
 * ClipboardSensitiveActivity
 * @author: zhen51.wang
 * @date: 2022/10/20/020
 */
class ClipboardSensitiveActivity : BaseActivity() {
    private lateinit var binding: ActivityClipboardSensitiveBinding
    private lateinit var manager: ClipboardManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClipboardSensitiveBinding.inflate(layoutInflater)
        setContentView(binding.root)
        manager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        with(binding) {
            btnCopyPlain.setOnClickListener {
                manager.setPrimaryClip(ClipData.newPlainText(null, "text content"))
            }
            btnCopySensitive.setOnClickListener {
                manager.setPrimaryClip(ClipData.newPlainText(null, "text content").apply {
                    description.extras = PersistableBundle().apply {
                        putBoolean(
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                                ClipDescription.EXTRA_IS_SENSITIVE
                            else
                                "android.content.extra.IS_SENSITIVE",
                            true
                        )
                    }
                })
            }
        }
    }

    override fun createToolbar(): Toolbar {
        return ToolbarFactory.themed(this, getString(R.string.item_clipboard_sensitive))
    }
}