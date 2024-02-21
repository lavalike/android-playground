package com.android.exercise.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.exercise.R;
import com.android.exercise.databinding.ItemReposLayoutBinding;
import com.android.exercise.domain.GithubBean;
import com.android.exercise.ui.widget.recyclerview.BaseRecyclerAdapter;
import com.android.exercise.ui.widget.recyclerview.BaseViewHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * Created by wangzhen on 16/11/8.
 */

public class ReposAdapter extends BaseRecyclerAdapter<GithubBean, ReposAdapter.ReposViewHolder> {

    public ReposAdapter(Context context, List<GithubBean> list) {
        super(context, list);
    }

    @Override
    public ReposViewHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReposViewHolder(ItemReposLayoutBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    public class ReposViewHolder extends BaseViewHolder<GithubBean> {
        ItemReposLayoutBinding binding;

        public ReposViewHolder(ItemReposLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        protected void bindData() {
            binding.tvItemReposName.setText(data.getName());
            binding.tvItemReposUrl.setText(data.getHtml_url());
            Glide.with(mContext).load(data.getOwner().getAvatar_url()).apply(new RequestOptions().placeholder(R.drawable.ic_placeholder).error(R.drawable.ic_placeholder)).into(binding.ivItemReposAvatar);
        }
    }
}
