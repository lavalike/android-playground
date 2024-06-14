package com.android.playground.ui

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.android.playground.R
import com.android.playground.base.BaseActivity
import com.android.playground.databinding.ActivityMainBinding
import com.android.playground.domain.NotificationBean
import com.android.playground.repository.DataRepository
import com.android.playground.ui.adapter.HomeAdapter
import com.android.playground.ui.widget.decoration.GridSpaceItemDecoration
import com.android.playground.util.IKey
import com.android.playground.util.UIUtils
import com.wangzhen.adapter.base.RecyclerItem

/**
 * Created by wangzhen on 2016/10/19/019.
 */
class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fitDarkStatus(true)
        initPush(intent)
        initRecycler()
    }

    private fun initRecycler() {
        with(binding.recyclerview) {
            layoutManager = GridLayoutManager(
                mContext,
                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 3 else 6
            ).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int) = adapter?.let { adapter ->
                        val type = adapter.getItemViewType(position)
                        if (type == HomeAdapter.TYPE_TITLE) spanCount else 1
                    } ?: 1
                }
            }

            addItemDecoration(GridSpaceItemDecoration(UIUtils.dip2px(context, GAP)))

            adapter = HomeAdapter(DataRepository.home()).apply {
                addFooter(object : RecyclerItem() {
                    public override fun layout(): Int {
                        return R.layout.home_footer_layout
                    }
                }.onCreateView(this@with))
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        initPush(intent)
    }

    private fun initPush(intent: Intent) {
        val extra = intent.getSerializableExtra(IKey.PUSH_DATA)
        if (extra is NotificationBean) {
            showToast(extra.title)
        }
    }

    companion object {
        private const val GAP = 2f
    }
}