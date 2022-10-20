package com.android.exercise.ui.activity.android13

import android.content.ClipData
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.toolbar.ToolBarCommonHolder
import com.android.exercise.databinding.ActivityClipboardSensitiveBinding

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

    override fun onSetupToolbar(toolbar: Toolbar?, actionBar: ActionBar?) {
        ToolBarCommonHolder(this, toolbar, getString(R.string.item_clipboard_sensitive))
    }
}