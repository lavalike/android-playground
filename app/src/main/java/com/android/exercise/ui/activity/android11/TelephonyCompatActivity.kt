package com.android.exercise.ui.activity.android11

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.view.View
import com.aliya.permission.Permission
import com.aliya.permission.PermissionCallback
import com.aliya.permission.PermissionManager
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.util.T

/**
 * 电话号码相关权限
 * Created by wangzhen on 2020/8/26.
 */
class TelephonyCompatActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_telephony_compat)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_get_phone_number -> {
                PermissionManager.request(this, object : PermissionCallback {
                    override fun onGranted(isAlready: Boolean) {
                        val tm = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                        T.get(applicationContext).toast("电话号码 ${tm.line1Number}")
                    }

                    override fun onDenied(deniedPermissions: MutableList<String>, neverAskPermissions: MutableList<String>?) {
                        T.get(applicationContext).toast("权限被拒绝")
                    }
                }, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_PHONE_NUMBERS)
            }
            R.id.btn_get_imei -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    T.get(this).toast("Android 10+ 不支持获取IMEI")
                    return
                }
                PermissionManager.request(this, object : PermissionCallback {
                    override fun onGranted(isAlready: Boolean) {
                        val tm = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                        T.get(applicationContext).toast("IMEI ${tm.imei}")
                    }

                    override fun onDenied(deniedPermissions: MutableList<String>, neverAskPermissions: MutableList<String>?) {
                        T.get(applicationContext).toast("权限被拒绝")
                    }
                }, Permission.PHONE_READ_PHONE_STATE)
            }
        }
    }
}