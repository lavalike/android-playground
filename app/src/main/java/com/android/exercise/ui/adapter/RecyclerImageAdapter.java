package com.android.exercise.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.android.exercise.ui.adapter.holder.RecyclerImageViewHolder;
import com.android.exercise.ui.widget.recyclerview.BaseRecyclerAdapter;

import java.util.List;

public class RecyclerImageAdapter extends BaseRecyclerAdapter<String, RecyclerImageViewHolder> {

    public RecyclerImageAdapter(Context context, List<String> list) {
        super(context, list);
    }

    @Override
    public RecyclerImageViewHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerImageViewHolder(parent);
    }
}
