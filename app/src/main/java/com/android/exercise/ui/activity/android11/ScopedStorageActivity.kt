package com.android.exercise.ui.activity.android11

import android.Manifest
import android.app.Activity
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.toolbar.ToolBarCommonHolder
import com.android.exercise.ui.widget.dialog.PictureWindow
import com.wangzhen.permission.PermissionManager
import com.wangzhen.permission.callback.AbsPermissionCallback
import java.io.File

/**
 * Android 10 分区存储
 * Created by wangzhen on 2020/8/25.
 */
class ScopedStorageActivity : BaseActivity() {
    companion object {
        const val REQUEST_CREATE_DOCUMENT: Int = 0x1
        const val REQUEST_OPEN_DOCUMENT: Int = 0x2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage)
    }

    override fun onSetupToolbar(toolbar: Toolbar?, actionBar: ActionBar?) {
        ToolBarCommonHolder(this, toolbar, getString(R.string.item_scoped_storage))
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_get_files_dir -> {
                Toast.makeText(this, filesDir.absolutePath, Toast.LENGTH_SHORT).show()
            }
            R.id.btn_get_cache_dir -> {
                Toast.makeText(this, cacheDir.absolutePath, Toast.LENGTH_SHORT).show()
            }
            R.id.btn_get_external_cache_dir -> {
                Toast.makeText(
                    this,
                    if (externalCacheDir == null) "externalCacheDir目录不存在" else (externalCacheDir as File).absolutePath,
                    Toast.LENGTH_SHORT
                ).show()
            }
            R.id.btn_get_external_files_dir -> {
                val dir = getExternalFilesDir("files")
                val file = File(dir, "text.txt")
                if (!file.exists())
                    file.createNewFile()
                Toast.makeText(
                    this,
                    if (dir == null) "externalFilesDir目录不存在" else file.absolutePath,
                    Toast.LENGTH_SHORT
                ).show()
            }
            R.id.btn_external_storage_create_dir -> {
                PermissionManager.request(
                    this,
                    object : AbsPermissionCallback() {
                        override fun onGrant(permissions: Array<String>) {
                            val dir = File(Environment.getExternalStorageDirectory(), "abc/def")
                            if (!dir.exists()) {
                                val result = dir.mkdirs()
                                Toast.makeText(
                                    view.context,
                                    if (result) "目录创建成功" else "目录创建失败",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(view.context, "目录已存在", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onDeny(
                            deniedPermissions: Array<String>,
                            neverAskPermissions: Array<String>
                        ) {
                            Toast.makeText(view.context, "存储权限被拒绝", Toast.LENGTH_SHORT).show()
                        }
                    },
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            }
            R.id.btn_external_storage_read_file -> {
                PermissionManager.request(
                    this,
                    object : AbsPermissionCallback() {
                        override fun onDeny(
                            deniedPermissions: Array<String>,
                            neverAskPermissions: Array<String>
                        ) {
                            Toast.makeText(view.context, "存储权限被拒绝", Toast.LENGTH_SHORT).show()
                        }

                        override fun onGrant(permissions: Array<String>) {
                            val file =
                                File(Environment.getExternalStorageDirectory(), "deviceid.txt")
                            if (file.exists()) {
                                val text = file.readText()
                                Toast.makeText(view.context, text, Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(
                                    view.context,
                                    "deviceid.txt 文件不存在",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }
                    },
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            }
            R.id.btn_saf_create_file -> {
                val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                    type = "text/plain"
                    addCategory(Intent.CATEGORY_OPENABLE)
                    putExtra(Intent.EXTRA_TITLE, "created_by_saf.txt")
                }
                startActivityForResult(intent, REQUEST_CREATE_DOCUMENT)
            }
            R.id.btn_saf_open_file -> {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    type = "image/*"
                    addCategory(Intent.CATEGORY_OPENABLE)
                }
                startActivityForResult(intent, REQUEST_OPEN_DOCUMENT)
            }
            R.id.btn_get_content -> {
                mContentLauncher.launch("image/*")
            }
            R.id.btn_media_store_create_file -> {
                val values = ContentValues()
                values.put(
                    MediaStore.Images.Media.DISPLAY_NAME,
                    "${System.currentTimeMillis()}.png"
                )
                values.put(MediaStore.Images.Media.DESCRIPTION, "media store test")
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                val uri =
                    contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                uri?.let { _uri ->
                    val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.bg_dog)
                    val stream = contentResolver.openOutputStream(_uri)
                    stream?.let { _stream ->
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, _stream)
                        _stream.close()
                        Toast.makeText(this, "文件创建成功 $_uri", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            R.id.btn_media_store_read_files -> {
                val cursor = contentResolver.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    null,
                    null,
                    null,
                    "${MediaStore.MediaColumns.DATE_ADDED} desc"
                )
                if (cursor != null) {
                    val list: MutableList<Uri> = ArrayList()
                    while (cursor.moveToNext()) {
                        val id =
                            cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID))
                        val uri = ContentUris.withAppendedId(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            id
                        )
                        list.add(uri)
                    }
                    Toast.makeText(this, "图片数量 ${cursor.count}", Toast.LENGTH_SHORT).show()
                    cursor.close()
                }
            }
            R.id.btn_manage_external_storage -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    startActivity(Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION))
                } else {
                    Toast.makeText(this, "仅支持Android11+", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /**
     * @see ActivityResultContracts
     */
    private val mContentLauncher =
        registerForActivityResult(object : ActivityResultContract<String, Uri?>() {
            override fun createIntent(context: Context, input: String): Intent =
                Intent(Intent.ACTION_GET_CONTENT).apply {
                    type = input
                    addCategory(Intent.CATEGORY_OPENABLE)
                }

            override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
                return intent?.data
            }

        }) {
            it?.let {
                PictureWindow().setImageUri(it).showDialog(supportFragmentManager)
            }
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CREATE_DOCUMENT -> {
                    Toast.makeText(this, "saf文件创建成功", Toast.LENGTH_SHORT).show()
                }
                REQUEST_OPEN_DOCUMENT -> {
                    data?.data?.let { uri ->
                        PictureWindow().setImageUri(uri).showDialog(supportFragmentManager)
                    }
                }
            }
        }
    }
}