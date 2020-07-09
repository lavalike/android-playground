package com.android.exercise.ui.activity.memory

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.toolbar.ToolBarCommonHolder
import kotlinx.android.synthetic.main.activity_bitmap.*

/**
 * BitmapActivity
 * Created by wangzhen on 2020/7/8.
 */
class BitmapActivity : BaseActivity() {
    private var mBitmap: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bitmap)
        raw()
        inSampleSize()
        inBitmap()
    }

    private fun inBitmap() {
        val options = BitmapFactory.Options()
        options.inBitmap = mBitmap
        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.bicycle, options)
        iv_in_bitmap.setImageBitmap(bitmap)
    }

    private fun inSampleSize() {
        val options = BitmapFactory.Options()
        options.inSampleSize = 2
        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.bicycle, options)
        iv_in_sample_size.setImageBitmap(bitmap)
    }

    private fun raw() {
        mBitmap = BitmapFactory.decodeResource(resources, R.mipmap.bicycle)
        iv_raw.setImageBitmap(mBitmap)
    }

    override fun onSetupToolbar(toolbar: Toolbar, actionBar: ActionBar) {
        ToolBarCommonHolder(this, toolbar, getString(R.string.item_bitmap))
    }
}