//package com.android.exercise.base;
//
//import android.support.v4.util.SparseArrayCompat;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.RecyclerView.ViewHolder;
//import android.support.v7.widget.StaggeredGridLayoutManager;
//import android.text.TextUtils;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.zbjt.zj24h.R;
//import com.zbjt.zj24h.common.listener.IOnItemClickListener;
//import com.zbjt.zj24h.common.listener.IOnItemLongClickListener;
//import com.zbjt.zj24h.utils.UIUtils;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
///**
// * RecyclerView适配器的简单基类封装
// *
// * @param <T>  Item数据类型
// * @param <VH> ViewHolder子类泛型
// * @author a_liYa
// * @date 2016-3-9 下午7:49:18
// */
//public abstract class BaseRecyclerHeaderAdapter<T, VH extends BaseRecyclerViewHolder<? extends T>>
//        extends RecyclerView.Adapter<ViewHolder> {
//
//    private static final int ITEM_VIEW_TYPE_EMPTY_PAGE = -1; // 空页面Type
//
//    private static final int ITEM_VIEW_TYPE_HEADER = -10000;
//    private static final int ITEM_VIEW_TYPE_FOOTER = -20000;
//
//    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
//    private SparseArrayCompat<View> mFooterViews = new SparseArrayCompat<>();
//
//    public List<T> mDatas;
//
//    /**
//     * 保存所有Holder.itemView
//     */
//    private Set<View> mItemViews = new HashSet<>();
//
//    /**
//     * 空页面
//     */
//    private EmptyPageHolder mEmptyPageHolder;
//    protected EmptyPageInfo mEmptyInfo;
//
//    private IOnItemClickListener<T> mOnItemClickListener;
//    private IOnItemLongClickListener<T> mOnItemLongClickListener;
//
//    public BaseRecyclerHeaderAdapter(List<T> datas) {
//        this.mDatas = datas;
//        registerAdapterDataObserver(mAdapterDataObserver);
//    }
//
//    public Set<View> getItemViews() {
//        return mItemViews;
//    }
//
//    public SparseArrayCompat<View> getHeaderViews() {
//        return mHeaderViews;
//    }
//
//    public SparseArrayCompat<View> getFooterViews() {
//        return mFooterViews;
//    }
//
//    /**
//     * 获取数据集合
//     *
//     * @return 数据列表 默认会自动刷新
//     */
//    public List<T> getDatas() {
//        return mDatas;
//    }
//
//    public void build(List<T> datas) {
//        build(datas, false);
//    }
//
//    /**
//     * 返回数据数量
//     */
//    public int getDataSize() {
//        return mDatas == null ? 0 : mDatas.size();
//    }
//
//    @Override
//    public int getItemCount() { // 设置了空页面且元素个数为0时  返回1
//        return ((getDataSize() == 0 && mEmptyInfo != null) ? 1 : getDataSize())
//                + getHeadersCount() + getFootersCount();
//    }
//
//    public void addHeaderView(View view) {
//        mHeaderViews.put(ITEM_VIEW_TYPE_HEADER - mHeaderViews.size(), view);
//    }
//
//    public void addFooterView(View view) {
//        mFooterViews.put(ITEM_VIEW_TYPE_FOOTER - mFooterViews.size(), view);
//    }
//
//    public int getHeadersCount() {
//        return mHeaderViews.size();
//    }
//
//    public int getFootersCount() {
//        return mFooterViews.size();
//    }
//
//    public boolean isHeaderViewPos(int position) {
//        return position < getHeadersCount();
//    }
//
//    public boolean isFooterViewPos(int position) {
//        return position >= getItemCount() - getFootersCount() && position < getItemCount();
//    }
//
//    @Override
//    public final void onBindViewHolder(ViewHolder holder, int position) {
//        if (isHeaderViewPos(position) || isFooterViewPos(position)) return;
//
//        if (onInnerBindViewHolder(holder, position)) return;
//
//        if (ITEM_VIEW_TYPE_EMPTY_PAGE == getItemViewType(position)) {
//            mEmptyPageHolder.setData(mEmptyInfo);
//        } else {
//            setupItemEvent((VH) holder);  // 初始化条目的点击事件
//            if (!onAbsBindViewHolder((VH) holder, position))
//                ((BaseRecyclerViewHolder) holder).setData(mDatas.get(position - getHeadersCount()));
//        }
//    }
//
//    /**
//     * 内部onBindViewHolder, 供子类拦截使用
//     */
//    protected boolean onInnerBindViewHolder(ViewHolder holder, int position) {
//        return false;
//    }
//
//    @Override
//    public final ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (mHeaderViews.get(viewType) != null) {
//            return new ViewHolder(mHeaderViews.get(viewType)) {
//            };
//
//        } else if (mFooterViews.get(viewType) != null) {
//            return new ViewHolder(mFooterViews.get(viewType)) {
//            };
//        }
//        ViewHolder holder = onInnerCreateViewHolder(parent, viewType);
//        if (holder != null)
//            return holder;
//        return onAbsCreateViewHolder(parent, viewType);
//    }
//
//    /**
//     * 内部onCreateViewHolder, 供子类拦截使用
//     */
//    protected ViewHolder onInnerCreateViewHolder(ViewGroup parent, int viewType) {
//        return null;
//    }
//
//    @Override
//    public final int getItemViewType(int position) {
//        if (isHeaderViewPos(position)) { // 页眉
//            return mHeaderViews.keyAt(position);
//        } else if (isFooterViewPos(position)) { // 页脚
//            return mFooterViews.keyAt(mFooterViews.size() - (getItemCount() - position));
//        } else if (getDataSize() == 0
//                && mEmptyInfo != null
//                && position >= getHeadersCount()
//                && position < getItemCount() - getFootersCount()) { // 空页面
//            return ITEM_VIEW_TYPE_EMPTY_PAGE;
//        }
//        int innerItemViewType = getInnerItemViewType(position);
//        if (0 != innerItemViewType) {
//            return innerItemViewType;
//        }
//        return getAbsItemViewType(position - getHeadersCount());
//    }
//
//    /**
//     * 内部getItemViewType, 供子类拦截使用
//     *
//     * @param position 全部位置的索引
//     * @return int类型
//     */
//    protected int getInnerItemViewType(int position) {
//        return 0;
//    }
//
//    /**
//     * 自定义重写getItemViewType
//     *
//     * @param position 屏蔽了内部其他内部item转换过的索引
//     * @return int类型
//     */
//    public int getAbsItemViewType(int position) {
//        return 0;
//    }
//
//    /**
//     * 自定义重写onBindViewHolder(ViewHolder holder, int position)
//     *
//     * @param holder   ViewHolder
//     * @param position 当前绑定的下标
//     * @return true 表示拦截的自动bindView方法 需要自己处理
//     */
//    public boolean onAbsBindViewHolder(VH holder, int position) {
//        return false;
//    }
//
//    /**
//     * 自定义重写onCreateViewHolder(ViewGroup parent, int viewType)
//     *
//     * @param parent   ViewGroup
//     * @param viewType {@see getItemViewType(int position)}
//     * @return
//     */
//    public abstract VH onAbsCreateViewHolder(ViewGroup parent, int viewType);
//
//    /**
//     * 设置空页面
//     *
//     * @param emptyView 空页面 View
//     */
//    public void setEmptyView(View emptyView) {
//        if (mEmptyInfo == null) {
//            mEmptyInfo = new EmptyPageInfo();
//        }
//        mEmptyInfo.emptyView = emptyView;
//    }
//
//    /**
//     * 设置空页面
//     *
//     * @param layoutId 空页面 LayoutId
//     */
//    public void setEmptyView(int layoutId) {
//        if (mEmptyInfo == null) {
//            mEmptyInfo = new EmptyPageInfo();
//        }
//
//        mEmptyInfo.layoutId = layoutId;
//    }
//
//    /**
//     * 设置空页面 提示语和图标
//     *
//     * @param infoStr 提示语
//     * @param resId   图标
//     */
//    public void setEmptyInfo(String infoStr, int resId) {
//        if (mEmptyInfo == null) {
//            mEmptyInfo = new EmptyPageInfo();
//        }
//        mEmptyInfo.infoStr = infoStr;
//        mEmptyInfo.icId = resId;
//    }
//
//    /**
//     * 设置条目的点击事件(点按/长按)
//     *
//     * @param holder ViewHolder
//     */
//    protected void setupItemEvent(VH holder) {
//        holder.itemView.setClickable(true);
//        if (mOnItemClickListener != null) {
//            // 1. onItemClick
//            holder.itemView.setOnClickListener(mInnerOnClickListener);
//        }
//        if (mOnItemLongClickListener != null) {
//            // 2. onItemLongClick
//            holder.itemView.setOnLongClickListener(mInnerOnLongClickListener);
//        }
//        mItemViews.add(holder.itemView);
//        holder.itemView.setTag(R.id.tag_holder, holder);
//    }
//
//    /**
//     * 增加一条数据
//     *
//     * @param position 增加数据的位置（下标）
//     * @param t        要增加的数据
//     */
//    public void addData(int position, T t) {
//        mDatas.add(position, t);
//
//        notifyItemInserted(position); // 注意: 不是调用notifyDataSetChanged();
//    }
//
//    /**
//     * 获取指定数据
//     *
//     * @param index 下标
//     * @return
//     */
//    public T getData(int index) {
//        if (mDatas != null && index < mDatas.size() && index >= 0) {
//            return mDatas.get(index);
//        }
//        return null;
//    }
//
//    /**
//     * 增加多个数据
//     *
//     * @param t 要增加的数据
//     */
//    public void addData(List<T> t) {
//        addData(t, false);
//    }
//
//    /**
//     * 增加多个数据
//     *
//     * @param t                要增加的数据
//     * @param isAutoPartNotify 是否自动局部刷新 true ： 自动刷新
//     */
//    public void addData(List<T> t, boolean isAutoPartNotify) {
//        if (t == null || t.isEmpty()) {
//            return;
//        }
//        int positionStart = getItemCount() - getFootersCount();
//        if (mDatas == null) {
//            mDatas = t;
//        } else {
//            mDatas.addAll(t);
//        }
//        if (isAutoPartNotify)
//            notifyItemRangeInserted(positionStart, t.size());
//    }
//
//    /**
//     * 设置数据
//     *
//     * @param mDatas       数据集合
//     * @param isAutoNotify 是否自动刷新
//     */
//    public void build(List<T> mDatas, boolean isAutoNotify) {
//        this.mDatas = mDatas;
//        if (isAutoNotify)
//            notifyDataSetChanged();
//    }
//
//    /**
//     * 删除一个条目 公有方法, 暴露给调用者
//     *
//     * @param position 删除数据的位置（下标）
//     */
//    public void deleteData(int position) {
//        mDatas.remove(position);
//
//        notifyItemRemoved(position); // 注意这里调用的方法
//    }
//
//    // 解决Grid布局时添加Header、footer合并一行
//    @Override
//    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
//        if (manager instanceof GridLayoutManager) {
//            final GridLayoutManager gridManager = (GridLayoutManager) manager;
//            final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridManager.getSpanSizeLookup();
//            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//                @Override
//                public int getSpanSize(int position) {
//                    if (mHeaderViews.get(getItemViewType(position)) != null) {
//                        return gridManager.getSpanCount();
//                    }
//                    if (mFooterViews.get(getItemViewType(position)) != null) {
//                        return gridManager.getSpanCount();
//                    }
//                    if (getItemViewType(position) == ITEM_VIEW_TYPE_EMPTY_PAGE) {
//                        return gridManager.getSpanCount();
//                    }
//                    if (spanSizeLookup == null) {
//                        return 1;
//                    }
//
//                    return spanSizeLookup.getSpanSize(position);
//
//                }
//            });
//        }
//    }
//
//    // 解决StaggeredGrid布局时添加Header、footer合并一行
//    @Override
//    public void onViewAttachedToWindow(ViewHolder holder) {
//        super.onViewAttachedToWindow(holder);
//        int position = holder.getLayoutPosition();
//        if (isHeaderViewPos(position) || isFooterViewPos(position) ||
//                getItemViewType(position) == ITEM_VIEW_TYPE_EMPTY_PAGE) {
//            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
//            if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
//                StaggeredGridLayoutManager.LayoutParams p =
//                        (StaggeredGridLayoutManager.LayoutParams) lp;
//
//                p.setFullSpan(true);
//            }
//        }
//    }
//
//    public IOnItemClickListener<T> getOnItemClickListener() {
//        return mOnItemClickListener;
//    }
//
//    public void setOnItemClickListener(IOnItemClickListener<T> onItemClickListener) {
//        mOnItemClickListener = onItemClickListener;
//        if (mOnItemClickListener != null)
//            for (View itemView : mItemViews) {
//                itemView.setOnClickListener(mInnerOnClickListener);
//            }
//    }
//
//    public IOnItemLongClickListener<T> getOnItemLongClickListener() {
//        return mOnItemLongClickListener;
//    }
//
//    public void setOnItemLongClickListener(IOnItemLongClickListener<T> onItemLongClickListener) {
//        mOnItemLongClickListener = onItemLongClickListener;
//        if (mOnItemLongClickListener != null)
//            for (View itemView : mItemViews) {
//                itemView.setOnLongClickListener(mInnerOnLongClickListener);
//            }
//    }
//
//    /**
//     * 点击监听
//     */
//    private View.OnClickListener mInnerOnClickListener = new View.OnClickListener() {
//
//        @Override
//        public void onItemClick(View v) {
//            if (v.getTag(R.id.tag_holder) instanceof ViewHolder) {
//                ViewHolder holder = (ViewHolder) v.getTag(R.id.tag_holder);
//                if (mOnItemClickListener != null) {
//                    int layoutPosition = holder.getLayoutPosition();
//                    if (layoutPosition == RecyclerView.NO_POSITION) return; // 说明已经是废弃的Item
//
//                    int position = layoutPosition - getHeadersCount();
//                    mOnItemClickListener.onItemClick(holder.itemView, position,
//                            mDatas.get(position % mDatas.size()));
//                }
//            }
//        }
//    };
//
//    /**
//     * 长按点击监听
//     */
//    private View.OnLongClickListener mInnerOnLongClickListener = new View.OnLongClickListener() {
//
//        @Override
//        public boolean onItemLongClick(View v) {
//            if (v.getTag(R.id.tag_holder) instanceof ViewHolder) {
//                ViewHolder holder = (ViewHolder) v.getTag(R.id.tag_holder);
//                if (mOnItemLongClickListener != null) {
//                    int position = holder.getLayoutPosition() - getHeadersCount();
//                    return mOnItemLongClickListener.onItemLongClick(holder.itemView, position,
//                            mDatas.get(position % mDatas.size()));
//                }
//            }
//            return false;
//        }
//    };
//
//    /**
//     * 获取空页面RootView
//     *
//     * @return View
//     */
//    public View getEmptyPageView() {
//        if (mEmptyPageHolder == null) return null;
//
//        return mEmptyPageHolder.itemView;
//    }
//
//    /**
//     * Adapter数据变化监听
//     */
//    private RecyclerView.AdapterDataObserver mAdapterDataObserver =
//            new RecyclerView.AdapterDataObserver() {
//                @Override
//                public void onChanged() {
//                    mItemViews.clear();
//                }
//            };
//
//    /**
//     * Empty Page.
//     */
//    public static class EmptyPageHolder extends BaseRecyclerViewHolder<EmptyPageInfo> {
//
//        private TextView mTvEmpty;
//        private ImageView mIvEmpty;
//
//        public EmptyPageHolder(View itemView) {
//            super(itemView);
//            mTvEmpty = (TextView) itemView.findViewById(R.id.tv_empty);
//            mIvEmpty = (ImageView) itemView.findViewById(R.id.iv_empty);
//        }
//
//        @Override
//        public void bindView() {
//            if (mData == null)
//                return;
//            if (mData.emptyView != null) {
//                ((ViewGroup) itemView).removeAllViews();
//                ((ViewGroup) itemView).addView(mData.emptyView, ViewGroup.LayoutParams
//                                .MATCH_PARENT,
//                        ViewGroup.LayoutParams.MATCH_PARENT);
//            }
//            if (mData.layoutId != -1) {
//                ((ViewGroup) itemView).removeAllViews();
//                UIUtils.inflate(mData.layoutId, ((ViewGroup) itemView), true);
//            } else {
//                if (!TextUtils.isEmpty(mData.infoStr))
//                    mTvEmpty.setText(mData.infoStr);
//
//                if (-1 != mData.icId)
//                    mIvEmpty.setImageResource(mData.icId);
//            }
//        }
//    }
//
//    /**
//     * EmptyPageInfo
//     */
//    private static final class EmptyPageInfo {
//        String infoStr;
//        int icId = -1;
//        View emptyView;
//        int layoutId = -1;
//
//    }
//}
