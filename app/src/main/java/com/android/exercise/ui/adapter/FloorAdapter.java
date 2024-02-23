package com.android.exercise.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.exercise.R;
import com.android.exercise.databinding.ItemCommentLayoutBinding;
import com.android.exercise.domain.CommentBean;
import com.android.exercise.ui.widget.FloorView;
import com.wangzhen.adapter.RecyclerAdapter;
import com.wangzhen.adapter.base.RecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 盖楼Adapter
 * Created by wangzhen on 2017/8/10.
 */
public class FloorAdapter extends RecyclerAdapter<CommentBean> implements FloorView.IItemClickListener {

    private List<CommentBean> floorList;

    public FloorAdapter(List<CommentBean> list) {
        super(list);
        init();
    }

    private void init() {
        floorList = new ArrayList<>();
        CommentBean bean;
        for (int i = 1; i <= 10; i++) {
            bean = new CommentBean();
            bean.setBuildLevel(i);
            bean.setName("用户" + i);
            bean.setContent("评论内容" + i);
            floorList.add(bean);
        }
    }

    @Override
    public RecyclerView.ViewHolder onAbsCreateViewHolder(ViewGroup parent, int viewType) {
        return new FloorViewHolder(parent);
    }

    @Override
    public void onItemClick(CommentBean data) {

    }

    class FloorViewHolder extends RecyclerViewHolder<CommentBean> {
        private final ItemCommentLayoutBinding binding;

        public FloorViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment_layout, parent, false));
            binding = ItemCommentLayoutBinding.bind(itemView);
        }

        @Override
        public void bind() {
            if (floorList == null || floorList.size() == 0) {
                binding.floorView.setVisibility(View.GONE);
            } else {
                binding.floorView.setVisibility(View.VISIBLE);
                binding.floorView.setItemClickListener(FloorAdapter.this);
                binding.floorView.build(floorList);
            }
        }
    }
}
