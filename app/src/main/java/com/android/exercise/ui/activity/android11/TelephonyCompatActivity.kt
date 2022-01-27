package com.android.exercise.ui.activity.android11

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.toolbar.ToolBarCommonHolder
import com.android.exercise.util.T
import com.wangzhen.permission.PermissionManager
import com.wangzhen.permission.callback.AbsPermissionCallback

/**
 * 电话号码相关权限
 * Created by wangzhen on 2020/8/26.
 */
class TelephonyCompatActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_telephony_compat)
    }

    override fun onSetupToolbar(toolbar: Toolbar?, actionBar: ActionBar?) {
        ToolBarCommonHolder(this, toolbar, getString(R.string.item_telephony_compat))
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_get_phone_number -> {
                PermissionManager.request(this, object : AbsPermissionCallback() {
                    override fun onDeny(
                        deniedPermissions: Array<String>,
                        neverAskPermissions: Array<String>
                    ) {
                        T.get(applicationContext).toast("权限被拒绝")
                    }

                    override fun onGrant(permissions: Array<String>) {
                        val tm = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                        T.get(applicationContext).toast("电话号码 ${tm.line1Number}")
                    }
                }, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_PHONE_NUMBERS)
            }
            R.id.btn_get_imei -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    T.get(this).toast("Android 10+ 不支持获取IMEI")
                    return
                }
                PermissionManager.request(this, object : AbsPermissionCallback() {
                    override fun onDeny(
                        deniedPermissions: Array<String>,
                        neverAskPermissions: Array<String>
                    ) {
                        T.get(applicationContext).toast("权限被拒绝")
                    }

                    override fun onGrant(permissions: Array<String>) {
                        val tm = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                        T.get(applicationContext).toast("IMEI ${tm.imei}")
                    }
                }, Manifest.permission.READ_PHONE_STATE)
            }
        }
    }
}