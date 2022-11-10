package com.android.exercise.ui.activity.di.dagger2

import android.os.Bundle
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.toolbar.ThemedCommonToolbar
import com.android.exercise.databinding.ActivityDagger2Binding
import com.android.exercise.ui.activity.di.dagger2.component.DaggerAppComponent
import com.android.exercise.ui.activity.di.dagger2.entity.Man
import com.wangzhen.commons.toolbar.impl.Toolbar
import javax.inject.Inject

/**
 * Dagger2Activity
 * @author: zhen51.wang
 * @date: 2022/11/7/007
 */
class Dagger2Activity : BaseActivity() {
    private lateinit var binding: ActivityDagger2Binding

    @Inject
    lateinit var man: Man

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDagger2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        DaggerAppComponent.create().inject(this)

        with(binding) {
            tv.text = man.work()
        }
    }

    override fun createToolbar(): Toolbar =
        ThemedCommonToolbar(this, getString(R.string.item_dagger))
}