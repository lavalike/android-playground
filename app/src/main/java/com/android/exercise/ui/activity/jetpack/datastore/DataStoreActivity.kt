package com.android.exercise.ui.activity.jetpack.datastore

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.toolbar.ToolBarCommonHolder
import kotlinx.android.synthetic.main.activity_data_store.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_store)
        tv_msg.text = ""
        initDataStore()
    }

    private fun initDataStore() {
        dataStore = createDataStore(name = "data-store")
    }


    override fun onSetupToolbar(toolbar: Toolbar?, actionBar: ActionBar?) {
        ToolBarCommonHolder(this, toolbar, "DataStore")
    }

    fun onClick(view: View) {
        val nameKey = preferencesKey<String>("name")
        val ageKey = preferencesKey<Int>("age")
        when (view.id) {
            R.id.btn_store -> {
                runBlocking {
                    val name = "name ${SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE).format(System.currentTimeMillis())}"
                    dataStore.edit {
                        it[nameKey] = name
                    }
                    tv_msg.append("╔════════════════\n")
                    tv_msg.append("║ save : $name\n")

                    val age = Random().nextInt(100)
                    dataStore.edit {
                        it[ageKey] = age
                    }
                    tv_msg.append("║ save : $age\n")
                    tv_msg.append("╚════════════════\n")
                }
            }
            R.id.btn_read -> {
                runBlocking {
                    dataStore.data.catch {
                        // 当读取数据遇到错误时，如果是 `IOException` 异常，发送一个 emptyPreferences 来重新使用
                        // 但是如果是其他的异常，最好将它抛出去，不要隐藏问题
                        if (it is IOException) {
                            emit(emptyPreferences())
                        } else {
                            throw it
                        }
                    }.map {
                        it[nameKey] ?: ""
                    }.apply {
                        tv_msg.append("╔════════════════\n")
                        tv_msg.append("║ name : ${if (TextUtils.isEmpty(first())) "无数据" else first()}\n")
                    }
                }

                runBlocking {
                    dataStore.data.map { it[ageKey] }.apply {
                        tv_msg.append("║ age : ${first()}\n")
                        tv_msg.append("╚════════════════\n")
                    }
                }
            }
            R.id.btn_batch_save -> {
                val store = createDataStore(name = "data-test")
                GlobalScope.launch(Dispatchers.Main) {
                    val start = System.currentTimeMillis()
                    store.edit {
                        for (i in 1..10000) {
                            it[preferencesKey<String>(i.toString())] = i.toString()
                        }
                    }
                    tv_msg.append("-> 批量保存10000条耗时：${System.currentTimeMillis() - start}\n")
                }

            }
        }
    }
}