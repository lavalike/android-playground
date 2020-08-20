package com.android.exercise.ui.activity.wifi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.toolbar.ToolBarCommonHolder
import kotlinx.android.synthetic.main.activity_wifi.*

/**
 * WifiActivity
 *
 * Starting with Build.VERSION_CODES#Q, applications are not allowed to enable/disable Wi-Fi.
 * Created by wangzhen on 2020/8/20.
 */
class WifiActivity : BaseActivity() {
    private var mWifiManager: WifiManager? = null
    private val mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                Toast.makeText(context, "搜索完毕", Toast.LENGTH_SHORT).show()
                bind()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wifi)

        mWifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager?

        switch_view.setOnSwitchStateChangeListener { v, isOn -> mWifiManager?.isWifiEnabled = isOn }

        btn_scan.setOnClickListener {
            if (mWifiManager != null) {
                if (!mWifiManager!!.isWifiEnabled) {
                    mWifiManager!!.isWifiEnabled = true
                }
                mWifiManager?.startScan()
                bind()
            }
        }

        val filter = IntentFilter()
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        registerReceiver(mReceiver, filter)
    }

    override fun onSetupToolbar(toolbar: Toolbar?, actionBar: ActionBar?) {
        ToolBarCommonHolder(this, toolbar, "Wifi(未完成)")
    }

    private fun bind() {
        val list = mWifiManager?.scanResults
        Toast.makeText(this, "数量：${list?.size ?: 0}", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mReceiver)
    }
}