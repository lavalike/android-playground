package com.android.playground.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.playground.R;
import com.android.playground.databinding.ItemReposLayoutBinding;
import com.android.playground.domain.GithubBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wangzhen.adapter.RecyclerAdapter;
import com.wangzhen.adapter.base.RecyclerViewHolder;

import java.util.List;

/**
 * Created by wangzhen on 16/11/8.
 */

public class ReposAdapter extends RecyclerAdapter<GithubBean> {

    public ReposAdapter(List<GithubBean> list) {
        super(list);
    }

    @Override
    public RecyclerView.ViewHolder onAbsCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReposViewHolder(ItemReposLayoutBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    public static class ReposViewHolder extends RecyclerViewHolder<GithubBean> {
        ItemReposLayoutBinding binding;

        public ReposViewHolder(ItemReposLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void bind() {
            binding.tvItemReposName.setText(mData.getName());
            binding.tvItemReposUrl.setText(mData.getHtml_url());
            Glide.with(itemView.getContext()).load(mData.getOwner().getAvatar_url()).apply(new RequestOptions().placeholder(R.drawable.ic_placeholder).error(R.drawable.ic_placeholder)).into(binding.ivItemReposAvatar);
        }
    }
}
