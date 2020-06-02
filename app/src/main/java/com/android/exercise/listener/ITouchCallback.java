package com.android.exercise.listener;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Touch回调
 * Created by wangzhen on 2017/8/4.
 */
public interface ITouchCallback {
    /**
     * 长按移动交换位置
     *
     * @param fromPosition 当前位置
     * @param toPosition   目的位置
     * @return 是否允许交换位置
     */
    boolean onMove(int fromPosition, int toPosition);

    /**
     * 长按选中时调用
     *
     * @param viewHolder
     * @param actionState
     */
    void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState);

    /**
     * 松开时调用
     *
     * @param recyclerView
     * @param viewHolder
     */
    void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder);
}
