package com.android.exercise.ui.activity.database

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.base.toolbar.ToolbarFactory
import com.android.exercise.domain.litepal.Album
import com.wangzhen.commons.toolbar.impl.Toolbar
import org.litepal.LitePal
import java.util.*

/**
 * LitePalActivity
 * Created by wangzhen on 2020/9/15.
 */
class LitePalActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lite_pal)
    }

    override fun createToolbar(): Toolbar {
        return ToolbarFactory.themed(this, getString(R.string.item_litepal))
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_add -> {
                val album = Album()
                album.name = "album"
                album.price = 10.99f
                album.save()
                Toast.makeText(this, "新增成功", Toast.LENGTH_SHORT).show()
            }
            R.id.btn_delete -> {
                LitePal.deleteAll(Album::class.java)
                Toast.makeText(this, "全部删除", Toast.LENGTH_SHORT).show()
            }
            R.id.btn_add_batch -> {
                val start = System.currentTimeMillis()
                val list = mutableListOf<Album>()
                for (index in 0 until 10000) {
                    val album = Album()
                    album.name = UUID.randomUUID().toString()
                    album.price = index.toFloat()
                    list.add(album)
                }
                LitePal.saveAll(list)
                Toast.makeText(
                    this, "耗时 -> ${System.currentTimeMillis() - start}", Toast.LENGTH_SHORT
                ).show()
            }
            R.id.btn_query -> {
                val list = LitePal.findAll(Album::class.java)
                Toast.makeText(
                    this,
                    "共${if (list == null || list.isEmpty()) 0 else list.size}条",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}