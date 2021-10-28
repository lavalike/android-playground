package com.android.exercise.ui.activity.wifi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.toolbar.ToolBarCommonHolder
import com.android.exercise.databinding.ActivityWifiBinding

/**
 * WifiActivity
 *
 * Starting with Build.VERSION_CODES#Q, applications are not allowed to enable/disable Wi-Fi.
 * Created by wangzhen on 2020/8/20.
 */
class WifiActivity : BaseActivity() {
    private lateinit var binding: ActivityWifiBinding
    private var mWifiManager: WifiManager? = null
    private var mCurrentSSID: String? = null
    private val mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                bind()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWifiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mWifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager?
        if (mWifiManager != null) {
            val connectionInfo = mWifiManager!!.connectionInfo
            if (connectionInfo != null) {
                mCurrentSSID = connectionInfo.ssid?.replace("\"", "")
            }
        }

        binding.switchView.state = mWifiManager?.isWifiEnabled ?: false
        binding.switchView.setOnSwitchStateChangeListener { v, isOn ->
            mWifiManager?.isWifiEnabled = isOn
        }

        binding.btnScan.setOnClickListener {
            if (mWifiManager != null) {
                if (!mWifiManager!!.isWifiEnabled) {
                    mWifiManager!!.isWifiEnabled = true
                }
                mWifiManager?.startScan()
            }
        }

        binding.recycler.layoutManager = LinearLayoutManager(this)

        registerReceiver(mReceiver, IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION))
    }

    override fun onSetupToolbar(toolbar: Toolbar?, actionBar: ActionBar?) {
        ToolBarCommonHolder(this, toolbar, "Wifi")
    }

    private fun bind() {
        val scanResults = mWifiManager!!.scanResults
        if (scanResults.isNotEmpty()) {
            val list: MutableList<WifiEntity> = ArrayList()
            for (result in scanResults) {
                val entity = WifiEntity()
                entity.ssid = result.SSID
                entity.bssid = result.BSSID
                entity.isCurrent = TextUtils.equals(result.SSID, mCurrentSSID)
                entity.frequency = result.frequency
                list.add(entity)
            }
            binding.recycler.adapter = WifiAdapter(list)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mReceiver)
    }
}