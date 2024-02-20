package com.android.exercise.ui.activity.bluetooth

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.toolbar.ToolbarFactory
import com.android.exercise.databinding.ActivityBluetoothBinding
import com.android.exercise.util.toast
import com.wangzhen.commons.toolbar.impl.Toolbar
import com.wangzhen.permission.PermissionManager
import com.wangzhen.permission.callback.AbsPermissionCallback

/**
 * BluetoothActivity
 * Created by wangzhen on 2020/8/20.
 */
@SuppressLint("MissingPermission")
class BluetoothActivity : BaseActivity() {
    private lateinit var binding: ActivityBluetoothBinding
    private val bluetoothAdapter: BluetoothAdapter by lazy {
        (getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter
    }
    private var adapter: DataAdapter = DataAdapter(null)

    private val mReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            println("action: ${intent.action}")
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

        binding.btnEnable.setOnClickListener {
            enableLauncher.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
        }
        binding.btnFindBounded.setOnClickListener {
            requirePermissions {
                ensureState {
                    if (bluetoothAdapter.isDiscovering) {
                        bluetoothAdapter.cancelDiscovery()
                    }
                    val bondedDevices: Set<BluetoothDevice> = bluetoothAdapter.bondedDevices
                    if (bondedDevices.isNotEmpty()) {
                        val list: MutableList<BluetoothEntity> = ArrayList()
                        for (device in bondedDevices) {
                            list.add(BluetoothEntity(device.name, device.address))
                        }
                        adapter.setData(list)
                    } else {
                        "No Paired Devices".toast()
                    }
                }
            }
        }
        binding.btnFind.setOnClickListener {
            requirePermissions {
                ensureState {
                    "Finding".toast()
                    adapter.setData(null)
                    if (bluetoothAdapter.isDiscovering) {
                        bluetoothAdapter.cancelDiscovery()
                    }
                    bluetoothAdapter.startDiscovery()
                }
            }
        }

        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter

        registerReceiver(mReceiver, IntentFilter(BluetoothDevice.ACTION_FOUND))
    }

    private fun ensureState(block: () -> Unit) {
        if (bluetoothAdapter.isEnabled) {
            block.invoke()
        } else {
            "Enable Bluetooth First".toast()
        }
    }

    private fun requirePermissions(callback: () -> Unit) {
        PermissionManager.request(
            this, object : AbsPermissionCallback() {
                override fun onDeny(
                    deniedPermissions: Array<String>, neverAskPermissions: Array<String>
                ) {
                    "Lack of Bluetooth Permissions".toast()
                }

                override fun onGrant(permissions: Array<String>) {
                    callback.invoke()
                }

                override fun onNotDeclared(permissions: Array<String>) {
                    "Some Permissions Not Declared in Manifest".toast()
                }

            }, *obtainPermissions()
        )
    }

    private fun obtainPermissions(): Array<String> {
        val array = mutableListOf(
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            array.add(Manifest.permission.BLUETOOTH_SCAN)
            array.add(Manifest.permission.BLUETOOTH_CONNECT)
        }
        return array.toTypedArray()
    }

    private val enableLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                "Bluetooth Open".toast()
            }
        }

    override fun createToolbar(): Toolbar {
        return ToolbarFactory.themed(this, getString(R.string.item_bluetooth))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mReceiver)
        bluetoothAdapter.cancelDiscovery()
    }
}