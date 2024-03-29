package com.android.exercise.ui

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.android.exercise.R
import com.android.exercise.base.BaseActivity
import com.android.exercise.databinding.ActivityMainBinding
import com.android.exercise.domain.NotificationBean
import com.android.exercise.repository.DataRepository
import com.android.exercise.ui.adapter.HomeAdapter
import com.android.exercise.ui.widget.decoration.GridSpaceItemDecoration
import com.android.exercise.util.IKey
import com.android.exercise.util.UIUtils
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