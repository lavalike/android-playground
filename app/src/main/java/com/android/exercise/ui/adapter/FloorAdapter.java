package com.android.exercise.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.exercise.R;
import com.android.exercise.domain.CommentBean;
import com.android.exercise.ui.widget.FloorView;
import com.android.exercise.ui.widget.recyclerview.BaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 盖楼Adapter
 * Created by wangzhen on 2017/8/10.
 */
public class FloorAdapter extends BaseRecyclerAdapter<CommentBean, FloorAdapter.FloorViewHolder> implements FloorView.IItemClickListener {

    private List<CommentBean> floorList;

    public FloorAdapter(Context context, List<CommentBean> list) {
        super(context, list);
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
    public FloorViewHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        return new FloorViewHolder(parent);
    }

    @Override
    public void onMyBindViewHolder(FloorViewHolder holder, int position) {
        if (floorList == null || floorList.size() == 0) {
            holder.floorView.setVisibility(View.GONE);
        } else {
            holder.floorView.setVisibility(View.VISIBLE);
            holder.floorView.setItemClickListener(this);
            holder.floorView.build(floorList);
        }
    }

    @Override
    public void onItemClick(CommentBean data) {
        Toast.makeText(mContext, data.getContent(), Toast.LENGTH_SHORT).show();
    }

    class FloorViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.floorView)
        FloorView floorView;
        @BindView(R.id.tv_content)
        TextView tvContent;

        public FloorViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment_layout, parent, false));
            ButterKnife.bind(this, itemView);
        }
    }
}
