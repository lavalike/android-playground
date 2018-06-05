package com.android.exercise.ui.adapter.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;

/**
 * 悬停分割线
 */
public class HoverDecoration extends RecyclerView.ItemDecoration {
    private Paint mPaintHover;
    private Paint mPaintTitle;
    private Context context;
    private HoverCallback mCallback;
    private int mDividerHeight;
    private int mHoverHeight;
    private float mTitleLeftMargin;
    private Rect textBound = new Rect();

    public HoverDecoration(@NonNull Context context) {
        this.context = context;
        mDividerHeight = dp2px(0.5f);
        mHoverHeight = dp2px(40);
        mTitleLeftMargin = dp2px(10);
        mPaintHover = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintHover.setColor(Color.parseColor("#EDEDED"));
        mPaintTitle = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintTitle.setColor(Color.parseColor("#3B424C"));
        mPaintTitle.setTextSize(sp2px(18));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        if (isFirstInGroup(position)) {
            outRect.top = mHoverHeight;
        } else {
            outRect.top = mDividerHeight;
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();
        View child;
        for (int i = 0; i < childCount; i++) {
            child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);
            if (isFirstInGroup(position)) {
                int bottom = child.getTop();
                int top = bottom - mHoverHeight;
                c.drawRect(
                        child.getLeft(),
                        top,
                        child.getRight(),
                        bottom,
                        mPaintHover
                );
                String groupName = mCallback.getGroupName(position);
                mPaintTitle.getTextBounds(groupName, 0, groupName.length(), textBound);
                c.drawText(
                        groupName,
                        mTitleLeftMargin,
                        child.getTop() - (mHoverHeight / 2 - textBound.height() / 2),
                        mPaintTitle
                );

            } else {
                c.drawRect(
                        child.getLeft(),
                        child.getTop() - mDividerHeight,
                        child.getRight(),
                        child.getTop(),
                        mPaintHover
                );
            }
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        int firstPosition = layoutManager.findFirstVisibleItemPosition();
        View child = parent.findViewHolderForAdapterPosition(firstPosition).itemView;
        String groupName = mCallback.getGroupName(firstPosition);
        String secondGroupName = mCallback.getGroupName(firstPosition + 1);
        boolean flag = false;
        if (!TextUtils.equals(groupName, secondGroupName)) {
            //两个tile开始接触，第一item在屏幕剩余的高度小于title高度
            if (child.getHeight() + child.getTop() < mHoverHeight) {
                c.save();
                c.translate(0, child.getHeight() + child.getTop() - mHoverHeight);
                flag = true;
            }
        }
        c.drawRect(0, 0, parent.getRight(), mHoverHeight, mPaintHover);
        mPaintTitle.getTextBounds(groupName, 0, groupName.length(), textBound);
        c.drawText(
                groupName,
                mTitleLeftMargin,
                mHoverHeight - (mHoverHeight / 2 - textBound.height() / 2),
                mPaintTitle
        );
        if (flag)
            c.restore();
    }

    /**
     * 判断是否是分组内第一个
     *
     * @param position 位置
     * @return true 显示组名 false不显示组名
     */
    private boolean isFirstInGroup(int position) {
        if (mCallback == null)
            return false;
        if (position == 0) {
            return true;
        } else {
            String preGroupName = mCallback.getGroupName(position - 1);
            String groupName = mCallback.getGroupName(position);
            return !TextUtils.equals(preGroupName, groupName);
        }
    }

    public void setCallback(HoverCallback callback) {
        this.mCallback = callback;
    }

    private int dp2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    private int sp2px(float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
    }

    public interface HoverCallback {
        /**
         * 获取组名
         *
         * @param position 位置
         * @return 组名
         */
        String getGroupName(int position);
    }
}
