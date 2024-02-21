package com.android.exercise.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.android.exercise.R;
import com.android.exercise.databinding.ItemTouchLayoutBinding;
import com.android.exercise.listener.ITouchCallback;
import com.android.exercise.ui.widget.recyclerview.BaseRecyclerAdapter;
import com.android.exercise.ui.widget.recyclerview.BaseViewHolder;

import java.util.Collections;
import java.util.List;

/**
 * ItemTouchHelper Adapter
 * Created by wangzhen on 2017/8/1.
 */
public class RecyclerTouchAdapter extends BaseRecyclerAdapter<String, RecyclerTouchAdapter.TouchViewHolder> implements ITouchCallback {

    public RecyclerTouchAdapter(Context context, List<String> list) {
        super(context, list);
    }

    @Override
    public TouchViewHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        return new TouchViewHolder(parent);
    }

    @Override
    public boolean onMove(int fromPosition, int toPosition) {
        if (toPosition == 0) return false;
        Collections.swap(mDatas, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            viewHolder.itemView.animate().scaleX(2).scaleY(2).start();
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        viewHolder.itemView.animate().scaleX(1).scaleY(1).start();
    }

    class TouchViewHolder extends BaseViewHolder<String> {
        private ItemTouchLayoutBinding binding;

        public TouchViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_touch_layout);
            binding = ItemTouchLayoutBinding.bind(itemView);
        }

        @Override
        protected void bindData() {
            binding.tvTouch.setText(data);
        }
    }
}
