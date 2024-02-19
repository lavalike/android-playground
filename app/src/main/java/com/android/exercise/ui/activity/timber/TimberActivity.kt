package com.android.exercise.ui.activity.timber

import android.os.Bundle
import com.android.exercise.BuildConfig
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.toolbar.ToolbarFactory
import com.android.exercise.databinding.ActivityTimberBinding
import timber.log.Timber

/**
 * TimberActivity
 *
 * @author : wangzhen
 * @date : 2024/2/19/019
 */
class TimberActivity : BaseActivity() {
    private lateinit var binding: ActivityTimberBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        addEvents()
    }

    private fun addEvents() {
        with(binding) {
            btnForest.setOnClickListener {
                val forest = Timber.forest()
                val builder = StringBuilder()
                forest.forEach {
                    if (builder.isNotEmpty()) {
                        builder.append("\n")
                    }
                    builder.append(it.toString())
                }
                tv.text = builder.toString()
            }
            btnV.setOnClickListener {
                "Timber Verbose".run {
                    tv.text = this
                    Timber.v(this)
                }
            }
            btnD.setOnClickListener {
                "Timber Debug".run {
                    tv.text = this
                    Timber.d(this)
                }
            }
            btnI.setOnClickListener {
                "Timber Info".run {
                    tv.text = this
                    Timber.i(this)
                }
            }
            btnW.setOnClickListener {
                "Timber Warn".run {
                    tv.text = this
                    Timber.tag("tag_w").w(this)
                }
            }
            btnE.setOnClickListener {
                "Timber Error".run {
                    tv.text = this
                    Timber.tag("tag_e").e(this)
                }
            }
        }
    }

    override fun createToolbar() = ToolbarFactory.themed(this, getString(R.string.item_timber))
}