package com.android.exercise.ui.activity.serialization

import android.os.Bundle
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.toolbar.ToolbarFactory
import com.android.exercise.databinding.ActivitySerializationBinding
import com.wangzhen.commons.toolbar.impl.Toolbar
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * SerializationActivity
 * Created by wangzhen on 2022/1/26
 */
class SerializationActivity : BaseActivity() {
    private lateinit var binding: ActivitySerializationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySerializationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            btnSerialization.setOnClickListener {
                val data = Data("王震", 30, "developer", 10000.00f)
                tvResult.text = Json.encodeToString(data)
            }
            btnDeserialization.setOnClickListener {
                val json = "{\"name\":\"王震\",\"age\":30,\"job\":\"developer\"}"
                tvResult.text = Json.decodeFromString<Data>(json).toString()
            }
        }
    }

    override fun createToolbar(): Toolbar {
        return ToolbarFactory.themed(this, getString(R.string.item_kotlin_serialization))
    }

    @Serializable
    data class Data(
        val name: String? = null,
        val age: Int = 0,
        val job: String? = null,
        @Transient
        val money: Float = 0f
    )
}