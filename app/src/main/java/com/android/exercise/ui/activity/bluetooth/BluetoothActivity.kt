package com.android.exercise.ui.activity.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.aliya.permission.Permission
import com.aliya.permission.PermissionCallback
import com.aliya.permission.PermissionManager
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.toolbar.ToolBarCommonHolder
import com.android.exercise.databinding.ActivityBluetoothBinding

/**
 * BluetoothActivity
 * Created by wangzhen on 2020/8/20.
 */
class BluetoothActivity : BaseActivity() {
    private lateinit var binding: ActivityBluetoothBinding
    private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    private var adapter: DataAdapter = DataAdapter(null)

    private val mReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (TextUtils.equals(intent.action, BluetoothDevice.ACTION_FOUND)) {
                val device: BluetoothDevice? =
                    intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                if (device != null && !TextUtils.isEmpty(device.name)) {
                    adapter.addData(mutableListOf(BluetoothEntity(device.name, device.address)))
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBluetoothBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (bluetoothAdapter == null) {
            Toast.makeText(this, "不支持蓝牙设备", Toast.LENGTH_SHORT).show()
            return
        }

        binding.switchView.state = bluetoothAdapter.isEnabled

        binding.switchView.setOnSwitchStateChangeListener { v, isOn ->
            if (isOn) bluetoothAdapter.enable() else bluetoothAdapter.disable()
        }
        binding.btnFindBounded.setOnClickListener {
            check()
            val bondedDevices: Set<BluetoothDevice> = bluetoothAdapter.bondedDevices
            if (bondedDevices.isNotEmpty()) {
                val list: MutableList<BluetoothEntity> = ArrayList()
                for (device in bondedDevices) {
                    list.add(BluetoothEntity(device.name, device.address))
                }
                adapter.setData(list)
            } else {
                Toast.makeText(this, "没有已配对设备", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnFind.setOnClickListener {
            PermissionManager.request(this, object : PermissionCallback {
                override fun onGranted(isAlready: Boolean) {
                    check()
                    Toast.makeText(applicationContext, "正在查找", Toast.LENGTH_SHORT).show()
                    adapter.datas?.clear()
                    adapter.notifyDataSetChanged()
                    if (bluetoothAdapter.isDiscovering) {
                        bluetoothAdapter.cancelDiscovery()
                    }
                    bluetoothAdapter.startDiscovery()
                }

                override fun onDenied(
                    deniedPermissions: MutableList<String>,
                    neverAskPermissions: MutableList<String>?
                ) {
                    Toast.makeText(applicationContext, "需要位置权限", Toast.LENGTH_SHORT).show()
                }

            }, Permission.LOCATION_FINE, Permission.LOCATION_COARSE)
        }

        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter

        registerReceiver(mReceiver, IntentFilter(BluetoothDevice.ACTION_FOUND))
    }

    private fun check() {
        if (bluetoothAdapter != null) {
            if (!bluetoothAdapter.isEnabled) {
                bluetoothAdapter.enable()
                binding.switchView.state = true
            }
        }
    }

    override fun onSetupToolbar(toolbar: Toolbar?, actionBar: ActionBar?) {
        ToolBarCommonHolder(this, toolbar, getString(R.string.item_bluetooth))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mReceiver)
        bluetoothAdapter?.cancelDiscovery()
    }
}