package com.android.exercise.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.exercise.R;
import com.android.exercise.base.BaseRecyclerAdapter;
import com.android.exercise.domain.GithubBean;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangzhen on 16/11/8.
 */

public class ReposAdapter extends BaseRecyclerAdapter<GithubBean, ReposAdapter.ReposViewHolder> {

    public ReposAdapter(Context context, List<GithubBean> list) {
        super(context, list);
    }

    @Override
    public void onMyBindViewHolder(ReposViewHolder holder, int position) {
        GithubBean bean = mDatas.get(position);
        holder.tvItemReposName.setText(bean.getName());
        holder.tvItemReposUrl.setText(bean.getHtml_url());
        Glide.with(mContext).load(bean.getOwner().getAvatar_url()).into(holder.ivItemReposAvatar);
    }

    @Override
    public ReposViewHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_repos_layout, parent, false);
        return new ReposViewHolder(view);
    }

    public class ReposViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_item_repos_avatar)
        ImageView ivItemReposAvatar;
        @BindView(R.id.tv_item_repos_name)
        TextView tvItemReposName;
        @BindView(R.id.tv_item_repos_url)
        TextView tvItemReposUrl;

        public ReposViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
