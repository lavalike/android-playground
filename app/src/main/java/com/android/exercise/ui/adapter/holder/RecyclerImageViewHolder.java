package com.android.exercise.ui.adapter.holder;

import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.exercise.R;
import com.android.exercise.ui.widget.recyclerview.BaseViewHolder;
import com.android.exercise.util.GlideApp;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerImageViewHolder extends BaseViewHolder<String> {
    @BindView(R.id.iv_image)
    ImageView ivImage;

    public RecyclerImageViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_recycler_image);
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void bindData() {
        final String url = data;
        itemView.setTag(R.id.id_url, url);
        GlideApp.with(itemView.getContext())
                .load(url)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        Object tag = itemView.getTag(R.id.id_url);
                        if (tag == null || Objects.equals(tag, url)) {
                            ivImage.setImageDrawable(resource);
                        }
                    }
                });
    }
}
