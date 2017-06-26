package com.android.exercise.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.exercise.R;
import com.android.exercise.domain.ItemBean;
import com.android.exercise.ui.widget.recyclerview.BaseRecyclerAdapter;
import com.android.exercise.util.GlideApp;

import java.util.List;

/**
 * 主页功能
 * Created by Administrator on 2016/4/12.
 */
public class FunctionAdapter extends BaseRecyclerAdapter<ItemBean, RecyclerView.ViewHolder> {

    public FunctionAdapter(Context context, List<ItemBean> list) {
        super(context, list);
    }

    @Override
    public RecyclerView.ViewHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ItemBean.TYPE_TITLE) {
            View view = mInflater.inflate(R.layout.item_function_title_layout, parent, false);
            return new TitleViewHolder(view);
        } else {
            View view = mInflater.inflate(R.layout.item_function_layout, parent, false);
            return new ItemViewHolder(view);
        }
    }

    @Override
    public void onMyBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemBean itemBean = mDatas.get(position);
        if (holder instanceof TitleViewHolder) {
            TitleViewHolder titleHolder = (TitleViewHolder) holder;
            titleHolder.tv_title.setText(itemBean.getItemName() + "▼");
        } else if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemHolder = (ItemViewHolder) holder;
            itemHolder.tv_name.setText(itemBean.getItemName());
            GlideApp.with(mContext)
                    .load(R.mipmap.ic_burger)
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .into(itemHolder.iv_icon);
        }
    }

    @Override
    public int getItemViewType(int position) {
        ItemBean bean = mDatas.get(position);
        return bean.getItemType();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_icon;
        public TextView tv_name;

        public ItemViewHolder(View view) {
            super(view);
            this.tv_name = (TextView) view.findViewById(R.id.item_name);
            this.iv_icon = (ImageView) view.findViewById(R.id.item_icon);
        }
    }


    public class TitleViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title;

        public TitleViewHolder(View view) {
            super(view);
            this.tv_title = (TextView) view.findViewById(R.id.item_title);
        }
    }
}
