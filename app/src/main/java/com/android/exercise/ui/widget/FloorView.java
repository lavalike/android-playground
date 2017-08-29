package com.android.exercise.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.exercise.R;
import com.android.exercise.domain.CommentBean;

import java.util.List;

/**
 * 实现盖楼效果的布局
 * Created by wangzhen on 2017/8/10.
 */
public class FloorView extends LinearLayout {
    //最小楼层，大于该层数需要手动展开隐藏楼层
    private static final int MIN_LEVEL = 6;
    private final int density;
    private List<CommentBean> mDatas;
    private LayoutInflater mInflater;
    //边框背景
    private Drawable bounderDrawable;
    private IItemClickListener mItemListener;
    //楼层是否展开
    private boolean isExpanded;

    public FloorView(Context context) {
        this(context, null);
    }

    public FloorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.VERTICAL);
        density = (int) (2.0F * context.getResources().getDisplayMetrics().density);
        mInflater = LayoutInflater.from(context);
        TypedArray typedArray = context.obtainStyledAttributes(R.styleable.FloorView);
        int resourceId = typedArray.getResourceId(R.styleable.FloorView_bounder_drawable, -1);
        if (resourceId == -1) {
            this.bounderDrawable = ContextCompat.getDrawable(context, R.drawable.bg_bounder_floor);
        } else {
            this.bounderDrawable = ContextCompat.getDrawable(context, resourceId);
        }
        typedArray.recycle();
    }

    /**
     * 设置边框背景
     *
     * @param drawable 背景
     */
    public void setBounderDrawable(Drawable drawable) {
        this.bounderDrawable = drawable;
    }

    /**
     * 生成楼层
     *
     * @param floorData 楼层数据
     */
    public void build(List<CommentBean> floorData) {
        if (floorData == null || floorData.size() == 0) return;
        this.mDatas = floorData;
//        if (isExpanded()) {
//            expandAll();
//        } else {
        innerBuild();
//        }
    }

    private void innerBuild() {
        removeAllViews();
        if (mDatas.size() < MIN_LEVEL) {
            for (CommentBean data : mDatas) {
                addView(buildNormalItem(data));
            }
        } else {
            View view;
            view = buildNormalItem(mDatas.get(0));
            addView(view);
            view = buildNormalItem(mDatas.get(1));
            addView(view);
            view = buildHiddenItem();
            addView(view);
            view = buildNormalItem(mDatas.get(mDatas.size() - 1));
            addView(view);
        }
        layoutChildren();
    }

    /**
     * 缩进楼层
     */
    private void layoutChildren() {
        int childCount = getChildCount();
        LayoutParams params;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            int factor = i > childCount - MIN_LEVEL ? (childCount - i + 1) : MIN_LEVEL;
            int margin = factor * density;
            params.leftMargin = margin;
            params.rightMargin = margin;
            if (i == 0)
                params.topMargin = margin;
            child.setLayoutParams(params);
        }
    }

    /**
     * 创建正常条目
     *
     * @return 布局
     */
    private View buildNormalItem(CommentBean data) {
        View view = mInflater.inflate(R.layout.item_comment_floor_layout, this, false);
        TextView tvName = (TextView) view.findViewById(R.id.tv_name);
        TextView tvFloor = (TextView) view.findViewById(R.id.tv_floor);
        TextView tvContent = (TextView) view.findViewById(R.id.tv_content);
        tvName.setText(data.getName());
        tvFloor.setText(String.valueOf(data.getBuildLevel()));
        tvContent.setText(data.getContent());
        view.setTag(R.id.id_tag, data);
        view.setOnClickListener(innerNormalItemClickListener);
        return view;
    }

    /**
     * 创建隐藏条目
     *
     * @return 布局
     */
    private View buildHiddenItem() {
        View view = mInflater.inflate(R.layout.item_comment_hidden_layout, this, false);
        view.setOnClickListener(innerHiddenItemClickListener);
        return view;
    }

    /**
     * 正常楼层点击事件
     */
    private OnClickListener innerNormalItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mItemListener != null) {
                CommentBean data = (CommentBean) v.getTag(R.id.id_tag);
                mItemListener.onItemClick(data);
            }
        }
    };

    /**
     * 展开楼层点击事件
     */
    private OnClickListener innerHiddenItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            expandAll();
        }
    };

    /**
     * 展开所有楼层
     */
    private void expandAll() {
        removeAllViews();
        for (CommentBean mData : mDatas) {
            addView(buildNormalItem(mData));
        }
        layoutChildren();
        isExpanded = true;
    }

    /**
     * 设置是否展开所有楼层
     *
     * @param isExpanded
     */
    public void setExpanded(boolean isExpanded) {
        this.isExpanded = isExpanded;
    }

    /**
     * 是否展开所有楼层
     *
     * @return
     */
    public boolean isExpanded() {
        return isExpanded;
    }

    /**
     * 绘制连框背景
     *
     * @param canvas 画布
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        int childCount = getChildCount();
        if (bounderDrawable != null && childCount > 0) {
            //倒序绘制背景，以防背景叠加遮挡
            for (int i = childCount - 1; i >= 0; i--) {
                View child = getChildAt(i);
                bounderDrawable.setBounds(child.getLeft(), child.getLeft(), child.getRight(), child.getBottom());
                bounderDrawable.draw(canvas);
            }
        }
        super.dispatchDraw(canvas);
    }

    private int dip2px(float dipValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public void setItemClickListener(IItemClickListener listener) {
        mItemListener = listener;
    }

    public interface IItemClickListener {
        void onItemClick(CommentBean data);
    }
}
