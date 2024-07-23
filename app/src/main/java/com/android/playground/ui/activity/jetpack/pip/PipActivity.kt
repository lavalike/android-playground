package com.android.playground.ui.activity.jetpack.pip

import android.app.PictureInPictureParams
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.PictureInPictureModeChangedInfo
import androidx.core.content.res.ResourcesCompat
import androidx.core.util.Consumer
import com.android.playground.R
import com.android.playground.base.BaseActivity
import com.android.playground.databinding.ActivityPipBinding

/**
 * PipActivity
 * Created by wangzhen on 2024/7/23
 */
class PipActivity : BaseActivity() {
    companion object {
        const val PIP_NOT_SUPPORT = "当前设备不支持画中画(PIP)模式"
        const val PIP_NOT_SUPPORT_BELOW_O = "Android 8.0 以下不支持画中画(PIP)模式"
    }

    private lateinit var binding: ActivityPipBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPipBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setEvents()
    }

    private fun setEvents() {
        with(binding) {
            btnEnterPip.setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (isSupportPipMode()) {
                        enterPipMode()
                    } else {
                        Toast.makeText(
                            it.context, PIP_NOT_SUPPORT, Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        it.context, PIP_NOT_SUPPORT_BELOW_O, Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        addOnPictureInPictureModeChangedListener(pipModeChangeListener)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun enterPipMode() {
        val builder = PictureInPictureParams.Builder()
        enterPictureInPictureMode(builder.build())
    }

    private fun isSupportPipMode() =
        packageManager.hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)

    private val pipModeChangeListener = Consumer<PictureInPictureModeChangedInfo> {
        binding.tvMode.text = if (it.isInPictureInPictureMode) {
            getString(R.string.pip_mode_pip)
        } else {
            getString(R.string.pip_mode_normal)
        }
        binding.tvMode.setTextColor(
            if (it.isInPictureInPictureMode) {
                ResourcesCompat.getColor(
                    resources, R.color.colorPrimary, theme
                )
            } else {
                ResourcesCompat.getColor(
                    resources, R.color.color_text_color, theme
                )
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        removeOnPictureInPictureModeChangedListener(pipModeChangeListener)
    }
}