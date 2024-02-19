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
                println({ "forest: $forest" })
            }
            btnV.setOnClickListener { Timber.v("Timber Verbose") }
            btnD.setOnClickListener { Timber.d("Timber Debug") }
            btnI.setOnClickListener { Timber.i("Timber Info") }
            btnW.setOnClickListener { Timber.tag("tag_w").w("Timber Warn") }
            btnE.setOnClickListener { Timber.tag("tag_e").e("Timber Error") }
        }
    }

    override fun createToolbar() = ToolbarFactory.themed(this, getString(R.string.item_timber))
}