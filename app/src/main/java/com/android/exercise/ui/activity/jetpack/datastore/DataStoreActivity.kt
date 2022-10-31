package com.android.exercise.ui.activity.jetpack.datastore

import android.os.Bundle
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.toolbar.ToolbarFactory
import com.android.exercise.databinding.ActivityDataStoreBinding
import com.wangzhen.commons.toolbar.impl.Toolbar
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * DataStoreActivity
 * Created by wangzhen on 2020/10/13.
 */
class DataStoreActivity : BaseActivity() {
    private val dataStore: DataStore<Preferences> by preferencesDataStore(name = "data-store")
    private lateinit var binding: ActivityDataStoreBinding

    private val nameKey = stringPreferencesKey("name")
    private val ageKey = intPreferencesKey("age")

    private val count = 10000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityDataStoreBinding.inflate(layoutInflater).apply {
            binding = this
        }.root)
        initViews()
    }

    private fun initViews() {
        binding.btnStore.setOnClickListener {
            lifecycleScope.launch {
                saveSingle()
                Toast.makeText(this@DataStoreActivity, "single save success", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        binding.btnQuery.setOnClickListener {
            lifecycleScope.launch {
                dataStore.edit {
                    val name = it[nameKey]
                    val age = it[ageKey]
                    val msg = if (name == null || age == null) "no data" else "$name - $age"
                    Toast.makeText(
                        this@DataStoreActivity, msg, Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        binding.btnTotalSize.setOnClickListener {
            lifecycleScope.launch {
                val size = dataStore.data.count()
                Toast.makeText(this@DataStoreActivity, "data size : $size", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        binding.btnBatchSave.setOnClickListener {
            lifecycleScope.launch {
                saveBatch()
                Toast.makeText(this@DataStoreActivity, "batch save success", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        binding.btnQueryPerformance.setOnClickListener {
            readPerformance()
        }
        binding.btnDelete.setOnClickListener {
            lifecycleScope.launch {
                clearAll()
                Toast.makeText(this@DataStoreActivity, "clear all success", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private suspend fun clearAll() {
        dataStore.edit { it.clear() }
    }

    private suspend fun saveBatch() {
        dataStore.edit {
            for (i in 1..count) {
                it[stringPreferencesKey(i.toString())] = i.toString()
            }
        }
    }

    private fun readPerformance() {
        runBlocking {
            for (i in 1..count) {
                dataStore.data.catch {
                    // 当读取数据遇到错误时，如果是 `IOException` 异常，发送一个 emptyPreferences 来重新使用
                    // 但是如果是其他的异常，最好将它抛出去，不要隐藏问题
                    if (it is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw it
                    }
                }.map {
                    it[stringPreferencesKey(i.toString())] ?: ""
                }.apply {
                    // get value by first()
                }
            }
        }
    }

    private suspend fun saveSingle() {
        val name = "name ${
            SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.CHINESE
            ).format(System.currentTimeMillis())
        }"
        dataStore.edit {
            it[nameKey] = name
            it[ageKey] = Random().nextInt(100)
        }
    }

    override fun createToolbar(): Toolbar {
        return ToolbarFactory.themed(this, getString(R.string.item_data_store))
    }
}