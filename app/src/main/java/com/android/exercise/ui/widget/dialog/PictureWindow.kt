package com.android.exercise.ui.widget.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.android.exercise.R
import com.android.exercise.databinding.LayoutAdWindowBinding
import com.bumptech.glide.Glide

/**
 * picture window dialog
 * Created by wangzhen on 2018/3/15.
 */
class PictureWindow : DialogFragment() {
    private lateinit var binding: LayoutAdWindowBinding
    private var uri: Uri? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding =
            LayoutAdWindowBinding.bind(View.inflate(activity, R.layout.layout_ad_window, null))
        initViews()
        return AlertDialog.Builder(activity, R.style.ADWindowDialog)
            .setView(binding.root).create().apply {
                setCancelable(false)
                setCanceledOnTouchOutside(false)
                setOnKeyListener { _, keyCode, _ ->
                    keyCode == KeyEvent.KEYCODE_BACK
                }
            }
    }

    private fun initViews() {
        with(binding) {
            if (uri != null) {
                Glide.with(requireContext()).load(uri).into(ivAd)
            }
            ivClose.setOnClickListener {
                dismiss()
            }
        }
    }

    fun setImageUri(uri: Uri): PictureWindow {
        this.uri = uri
        return this
    }

    fun showDialog(fragmentManager: FragmentManager) {
        val ft = fragmentManager.beginTransaction()
        ft.add(this, "ad_window_fragment")
        ft.commitAllowingStateLoss()
    }
}