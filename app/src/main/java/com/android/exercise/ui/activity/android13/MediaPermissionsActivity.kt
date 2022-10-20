package com.android.exercise.ui.activity.android13

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.toolbar.ToolBarCommonHolder
import com.android.exercise.databinding.ActivityMediaPermissionsBinding
import com.wangzhen.permission.PermissionManager
import com.wangzhen.permission.callback.AbsPermissionCallback

/**
 * MediaPermissionsActivity
 * @author: zhen51.wang
 * @date: 2022/10/19/019
 */
class MediaPermissionsActivity : BaseActivity() {
    private lateinit var binding: ActivityMediaPermissionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaPermissionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private val pickLauncher =
        registerForActivityResult(object : ActivityResultContract<Int, List<Uri>?>() {
            override fun createIntent(context: Context, input: Int): Intent {
                val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    Intent(MediaStore.ACTION_PICK_IMAGES).putExtra(
                        MediaStore.EXTRA_PICK_IMAGES_MAX, input
                    )
                } else {
                    Intent(Intent.ACTION_GET_CONTENT)
                        .addCategory(Intent.CATEGORY_OPENABLE)
                        .putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                        .setType("image/*")
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
                    binding.iv.setImageURI(it[0])
                    if (it.size > 1) {
                        Toast.makeText(this, "多个图片只展示第一个", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    private fun initViews() {
        with(binding) {
            btnExternalStorage.setOnClickListener {
                execPermission(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            }
            btnMediaImages.setOnClickListener {
                execPermission(Manifest.permission.READ_MEDIA_IMAGES)
            }
            btnMediaVideo.setOnClickListener {
                execPermission(Manifest.permission.READ_MEDIA_VIDEO)
            }
            btnMediaAudio.setOnClickListener {
                execPermission(Manifest.permission.READ_MEDIA_AUDIO)
            }
            btnPickImage.setOnClickListener {
                pickLauncher.launch(2)
            }
        }
    }

    private fun execPermission(vararg permissions: String) {
        PermissionManager.request(this, object : AbsPermissionCallback() {
            override fun onDeny(
                deniedPermissions: Array<String>, neverAskPermissions: Array<String>
            ) {
                Toast.makeText(
                    this@MediaPermissionsActivity, "permissions denied", Toast.LENGTH_SHORT
                ).show()
            }

            override fun onGrant(permissions: Array<String>) {
                Toast.makeText(
                    this@MediaPermissionsActivity, "permissions granted", Toast.LENGTH_SHORT
                ).show()
            }
        }, *permissions)
    }

    override fun onSetupToolbar(toolbar: Toolbar?, actionBar: ActionBar?) {
        ToolBarCommonHolder(this, toolbar, getString(R.string.item_android13_media_permissions))
    }
}