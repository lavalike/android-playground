package com.android.exercise.ui.activity.android11

import android.app.Activity
import android.content.ContentUris
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import com.aliya.permission.Permission
import com.aliya.permission.PermissionCallback
import com.aliya.permission.PermissionManager
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.toolbar.ToolBarCommonHolder
import com.android.exercise.ui.widget.dialog.ADWindowDialog
import com.android.exercise.util.T
import java.io.File

/**
 * Android 10 分区存储
 * Created by wangzhen on 2020/8/25.
 */
class ScopedStorageActivity : BaseActivity() {
    private val REQUEST_CREATE_DOCUMENT: Int = 0x1
    private val REQUEST_OPEN_DOCUMENT: Int = 0x2

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
                T.get(this).toast(filesDir.absolutePath)
            }
            R.id.btn_get_cache_dir -> {
                T.get(this).toast(cacheDir.absolutePath)
            }
            R.id.btn_get_external_cache_dir -> {
                T.get(this).toast(if (externalCacheDir == null) "externalCacheDir目录不存在" else (externalCacheDir as File).absolutePath)
            }
            R.id.btn_get_external_files_dir -> {
                val file = getExternalFilesDir("files")
                T.get(this).toast(if (file == null) "externalFilesDir目录不存在" else file.absolutePath)
            }
            R.id.btn_external_storage_create_dir -> {
                PermissionManager.request(this, object : PermissionCallback {
                    override fun onGranted(isAlready: Boolean) {
                        val dir = File(Environment.getExternalStorageDirectory(), "abc/def")
                        if (!dir.exists()) {
                            val result = dir.mkdirs()
                            T.get(view.context).toast(if (result) "目录创建成功" else "目录创建失败")
                        } else {
                            T.get(view.context).toast("目录已存在")
                        }
                    }

                    override fun onDenied(deniedPermissions: MutableList<String>, neverAskPermissions: MutableList<String>?) {
                        T.get(view.context).toast("存储权限被拒绝")
                    }
                }, Permission.STORAGE_READ, Permission.STORAGE_WRITE)
            }
            R.id.btn_external_storage_read_file -> {
                PermissionManager.request(this, object : PermissionCallback {
                    override fun onGranted(isAlready: Boolean) {
                        val file = File(Environment.getExternalStorageDirectory(), "deviceid.txt")
                        if (file.exists()) {
                            val text = file.readText()
                            T.get(view.context).toast(text)
                        } else {
                            T.get(view.context).toast("deviceid.txt 文件不存在")
                        }
                    }

                    override fun onDenied(deniedPermissions: MutableList<String>, neverAskPermissions: MutableList<String>?) {
                        T.get(view.context).toast("存储权限被拒绝")
                    }
                }, Permission.STORAGE_READ, Permission.STORAGE_WRITE)
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
            R.id.btn_media_store_create_file -> {
                val contentValues = ContentValues()
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "${System.currentTimeMillis()}.png")
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, "")
                } else {
                    contentValues.put(MediaStore.MediaColumns.DATA, "")
                }
                val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                T.get(this).toast("文件创建成功 $uri")
            }
            R.id.btn_media_store_read_files -> {
                val cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, "${MediaStore.MediaColumns.DATE_ADDED} desc")
                if (cursor != null) {
                    val list: MutableList<Uri> = ArrayList()
                    while (cursor.moveToNext()) {
                        val id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID))
                        val uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                        list.add(uri)
                    }
                    T.get(this).toast("图片数量 ${cursor.count}")
                    cursor.close()
                }
            }
            R.id.btn_manage_external_storage -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    startActivity(Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION))
                } else {
                    T.get(this).toast("仅支持Android11+")
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CREATE_DOCUMENT -> {
                    T.get(this).toast("saf文件创建成功")
                }
                REQUEST_OPEN_DOCUMENT -> {
                    data?.data.also { uri ->
                        ADWindowDialog().setImageUri(uri).showDialog(supportFragmentManager)
                    }
                }
            }
        }
    }
}