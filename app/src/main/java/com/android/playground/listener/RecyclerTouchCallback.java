package com.android.playground.listener;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView Item触摸callback
 * Created by wangzhen on 2017/8/4.
 */
public class RecyclerTouchCallback extends ItemTouchHelper.Callback {

    private final ITouchCallback mCallback;

    public RecyclerTouchCallback(ITouchCallback callback) {
        this.mCallback = callback;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags;
        //swipeFlags=0表示不处理侧滑事件
        int swipeFlags = 0;
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            //如果为网格类型，则有UP、DOWN、LEFT、RIGHT四个方向
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        } else {
            //如果为列表类型，则有UP、DOWN两个方向
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        }
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                if (mCallback != null) {
                    return mCallback.onMove(i, i + 1);
                }
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                if (mCallback != null) {
                    return mCallback.onMove(i, i - 1);
                }
            }
        }
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    /**
     * 长按选中时调用
     *
     * @param viewHolder
     * @param actionState
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if (mCallback != null) mCallback.onSelectedChanged(viewHolder, actionState);
    }

    /**
     * 松开时调用
     *
     * @param recyclerView
     * @param viewHolder
     */
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if (mCallback != null) mCallback.clearView(recyclerView, viewHolder);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }
}
