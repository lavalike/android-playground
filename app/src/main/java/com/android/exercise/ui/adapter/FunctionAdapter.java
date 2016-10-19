package com.android.exercise.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.exercise.R;
import com.android.exercise.ui.adapter.base.BaseRecyclerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/4/12.
 */
public class FunctionAdapter extends BaseRecyclerAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    public FunctionAdapter(Context context, List list) {
        super(list);
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_function_layout, parent, false);
        FunctionViewHolder holder = new FunctionViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FunctionViewHolder viewHolder = (FunctionViewHolder) holder;
        String title = (String) mDatas.get(position);
        viewHolder.tv_title.setText(title);
    }

    public class FunctionViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title;

        public FunctionViewHolder(View view) {
            super(view);
            this.tv_title = (TextView) view.findViewById(R.id.item_title);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onItemClick(v, getLayoutPosition(), mDatas.get(getLayoutPosition()));
                    }
                }
            });
        }
    }
}
