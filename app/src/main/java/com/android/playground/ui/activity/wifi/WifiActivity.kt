package com.android.playground.ui.activity.wifi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.Bundle
import android.text.TextUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.playground.R
import com.android.playground.base.BaseActivity
import com.android.playground.base.toolbar.ToolbarFactory
import com.android.playground.databinding.ActivityWifiBinding
import com.wangzhen.commons.toolbar.impl.Toolbar

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

    override fun createToolbar(): Toolbar {
        return ToolbarFactory.themed(this, getString(R.string.item_wifi))
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