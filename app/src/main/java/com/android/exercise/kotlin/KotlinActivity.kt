package com.android.exercise.kotlin

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.widget.TextView
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.toolbar.ToolBarCommonHolder

class KotlinActivity : BaseActivity() {
    private lateinit var mTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        mTextView = findViewById(R.id.tv_msg) as TextView

        mTextView.setText("开始\n")

        val items = listOf("apple", "banana", "kiwifruit")
        for (item in items) {
            mTextView.append("-> $item length is ${getLength(item)}\n")
        }
    }

    private fun getLength(obj: Any): Int? {
        if (obj is String) {
            return obj.length
        }
        return 0
    }

    override fun onSetupToolbar(toolbar: Toolbar?, actionBar: ActionBar?) {
        ToolBarCommonHolder(this, toolbar, "Kotlin")
    }
}
