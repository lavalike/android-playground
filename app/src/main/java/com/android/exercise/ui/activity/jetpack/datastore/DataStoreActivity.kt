package com.android.exercise.ui.activity.jetpack.datastore

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.toolbar.ToolBarCommonHolder
import com.android.exercise.databinding.ActivityDataStoreBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
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
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var binding: ActivityDataStoreBinding

    private val nameKey = preferencesKey<String>("name")
    private val ageKey = preferencesKey<Int>("age")

    private val count = 10000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityDataStoreBinding.inflate(layoutInflater).apply {
            binding = this
        }.root)
        initViews()
        initDataStore()
    }

    private fun initViews() {
        binding.btnStore.setOnClickListener {
            MainScope().launch {
                saveSingle()
            }
        }
        binding.btnQuery.setOnClickListener {
            MainScope().launch {
                val size = dataStore.data.count()
                Log.e("TAG", "data size : $size")
                launch(Dispatchers.Main) {
                    Toast.makeText(this@DataStoreActivity, "data size : $size", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.btnBatchSave.setOnClickListener {
            MainScope().launch {
                saveBatch()
            }
        }
        binding.btnQueryPerformance.setOnClickListener {
            readPerformance()
        }
        binding.btnDelete.setOnClickListener {
            MainScope().launch {
                clearAll()
            }
        }
    }

    private suspend fun clearAll() {
        dataStore.edit { it.clear() }
    }

    private suspend fun saveBatch() {
        dataStore.edit {
            for (i in 1..count) {
                it[preferencesKey<String>(i.toString())] = i.toString()
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
                    it[preferencesKey<String>(i.toString())] ?: ""
                }.apply {
                    // get value by first()
                }
            }
        }
    }

    private suspend fun saveSingle() {
        val name = "name ${SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE).format(System.currentTimeMillis())}"
        dataStore.edit {
            it[nameKey] = name
            it[ageKey] = Random().nextInt(100)
        }
    }

    private fun initDataStore() {
        dataStore = createDataStore(name = "data-store")
    }

    override fun onSetupToolbar(toolbar: Toolbar?, actionBar: ActionBar?) {
        ToolBarCommonHolder(this, toolbar, "DataStore")
    }
}