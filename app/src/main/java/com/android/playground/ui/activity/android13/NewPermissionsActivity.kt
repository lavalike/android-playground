package com.android.playground.ui.activity.android13

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.android.playground.R
import com.android.playground.base.BaseActivity
import com.android.playground.base.toolbar.ToolbarFactory
import com.android.playground.databinding.ActivityMediaPermissionsBinding
import com.android.playground.ui.widget.dialog.PictureWindow
import com.wangzhen.commons.toolbar.impl.Toolbar

/**
 * NewPermissionsActivity
 * @author: zhen51.wang
 * @date: 2022/10/19/019
 */
class NewPermissionsActivity : BaseActivity() {
    private lateinit var binding: ActivityMediaPermissionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaPermissionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            for ((_, granted) in result) {
                if (granted) {
                    Toast.makeText(
                        this@NewPermissionsActivity, "permission granted", Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@NewPermissionsActivity, "permission denied", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    private val pickLauncher =
        registerForActivityResult(object : ActivityResultContract<Int, List<Uri>?>() {
            override fun createIntent(context: Context, input: Int): Intent {
                val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    Intent(MediaStore.ACTION_PICK_IMAGES).putExtra(
                        MediaStore.EXTRA_PICK_IMAGES_MAX, input
                    )
                } else {
                    Intent(Intent.ACTION_GET_CONTENT).addCategory(Intent.CATEGORY_OPENABLE)
                        .putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true).setType("image/*")
                }
                return intent
            }

            override fun parseResult(resultCode: Int, intent: Intent?): List<Uri>? {
                // 13以上单张/多张统一从clipData读取
                // 13以下多张从clipData读取
                intent?.clipData?.let { clip ->
                    val list = mutableListOf<Uri>()
                    for (i in 0 until clip.itemCount) {
                        list.add(clip.getItemAt(i).uri)
                    }
                    return list
                } ?: let {
                    // 13以下单张从data读取
                    intent?.data?.let {
                        return listOf(it)
                    }
                }
                return null
            }
        }) { result ->
            result?.let {
                if (it.isNotEmpty()) {
                    PictureWindow().setImageUri(it[0]).showDialog(supportFragmentManager)
                    if (it.size > 1) {
                        Toast.makeText(this, "多个图片只展示第一个", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    private fun initViews() {
        with(binding) {
            btnRevokeAll.setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    revokeSelfPermissionsOnKill(
                        listOf(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.READ_MEDIA_IMAGES,
                            Manifest.permission.READ_MEDIA_VIDEO,
                            Manifest.permission.READ_MEDIA_AUDIO
                        )
                    )
                    Toast.makeText(
                        it.context,
                        getString(R.string.new_permission_revoke_success),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        it.context,
                        getString(R.string.new_permission_revoke_unsupported),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            btnExternalStorage.setOnClickListener {
                permissionLauncher.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
            }
            btnMediaImages.setOnClickListener {
                permissionLauncher.launch(arrayOf(Manifest.permission.READ_MEDIA_IMAGES))
            }
            btnMediaVideo.setOnClickListener {
                permissionLauncher.launch(arrayOf(Manifest.permission.READ_MEDIA_VIDEO))
            }
            btnMediaAudio.setOnClickListener {
                permissionLauncher.launch(arrayOf(Manifest.permission.READ_MEDIA_AUDIO))
            }
            btnNearbyWifiDevices.setOnClickListener {
                permissionLauncher.launch(arrayOf(Manifest.permission.NEARBY_WIFI_DEVICES))
            }
            btnBodySensors.setOnClickListener {
                permissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.BODY_SENSORS,
                        Manifest.permission.BODY_SENSORS_BACKGROUND
                    )
                )
            }
            btnPickImage.setOnClickListener {
                pickLauncher.launch(2)
            }
        }
    }

    override fun createToolbar(): Toolbar {
        return ToolbarFactory.themed(this, getString(R.string.item_android13_new_permissions))
    }
}