package com.android.exercise.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.exercise.R;
import com.android.exercise.domain.GithubBean;
import com.android.exercise.ui.widget.recyclerview.BaseRecyclerAdapter;
import com.android.exercise.ui.widget.recyclerview.BaseViewHolder;
import com.android.exercise.util.GlideApp;

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
    public ReposViewHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReposViewHolder(parent);
    }

    public class ReposViewHolder extends BaseViewHolder<GithubBean> {
        @BindView(R.id.iv_item_repos_avatar)
        ImageView ivItemReposAvatar;
        @BindView(R.id.tv_item_repos_name)
        TextView tvItemReposName;
        @BindView(R.id.tv_item_repos_url)
        TextView tvItemReposUrl;

        public ReposViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_repos_layout);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void bindData() {
            tvItemReposName.setText(data.getName());
            tvItemReposUrl.setText(data.getHtml_url());
            GlideApp.with(mContext)
                    .load(data.getOwner().getAvatar_url())
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .into(ivItemReposAvatar);
        }
    }
}
