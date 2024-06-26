package com.android.playground.ui.activity.di.kodein

import android.os.Bundle
import com.android.playground.R
import com.android.playground.base.BaseActivity
import com.android.playground.base.toolbar.ToolbarFactory
import com.android.playground.databinding.ActivityKodeinBinding
import com.android.playground.ui.activity.di.kodein.entity.AppKodeinMessage
import com.android.playground.ui.activity.di.kodein.entity.KodeinMessage
import org.kodein.di.*

/**
 * KodeinActivity
 * @author: zhen51.wang
 * @date: 2022/11/11/011
 */
class KodeinActivity : BaseActivity() {
    private lateinit var binding: ActivityKodeinBinding

    private val kodeDi = DI {
        bindProvider<KodeinMessage> { AppKodeinMessage() }
    }
    private val message: KodeinMessage by kodeDi.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKodeinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tv.text = message.say()
    }

    override fun createToolbar() = ToolbarFactory.themed(this, getString(R.string.item_kodein))
}