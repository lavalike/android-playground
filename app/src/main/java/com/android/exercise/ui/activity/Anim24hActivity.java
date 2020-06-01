package com.android.exercise.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.wangzhen.statusbar.DarkStatusBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 24hour引导动画
 */
public class Anim24hActivity extends BaseActivity {

    @BindView(R.id.panel_homepage)
    LinearLayout panelHomepage;
    @BindView(R.id.panel_second)
    TableLayout panelSecond;
    @BindView(R.id.panel_circleMenu)
    FrameLayout panelCircleMenu;

    @BindView(R.id.item_days)
    FrameLayout itemDays;
    @BindView(R.id.item_news)
    FrameLayout itemNews;
    @BindView(R.id.item_service)
    FrameLayout itemService;
    @BindView(R.id.item_column)
    FrameLayout itemColumn;

    @BindView(R.id.item_menu_service)
    ImageView itemMenuService;
    @BindView(R.id.item_menu_news)
    ImageView itemMenuNews;
    @BindView(R.id.item_menu_column)
    ImageView itemMenuColumn;
    @BindView(R.id.item_menu_days)
    ImageView itemMenuDays;
    @BindView(R.id.item1)
    FrameLayout item1;
    @BindView(R.id.item2)
    FrameLayout item2;
    @BindView(R.id.item4)
    RelativeLayout item4;
    @BindView(R.id.item6)
    RelativeLayout item6;
    @BindView(R.id.item8)
    RelativeLayout item8;
    @BindView(R.id.item9)
    RelativeLayout item9;
    @BindView(R.id.item_bg_big_days)
    RelativeLayout itemBgBigDays;
    @BindView(R.id.item_bg_big_news)
    RelativeLayout itemBgBigNews;
    @BindView(R.id.iv_homepage)
    ImageView ivHomepage;
    @BindView(R.id.iv_word)
    ImageView ivWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim24h);
        ButterKnife.bind(this);
        DarkStatusBar.get().fitDark(this);
    }

    @Override
    public boolean showToolbar() {
        return false;
    }

    @OnClick({R.id.item_days, R.id.item_news})
    public void onClick(View view) {
        itemDays.setSelected(false);
        itemNews.setSelected(false);
        switch (view.getId()) {
            case R.id.item_days:
                itemDays.setSelected(true);
                startDaysNewsPanelAnim();
                break;
            case R.id.item_news:
                itemNews.setSelected(true);
                startDaysNewsPanelAnim();
                break;
        }
        if (itemDays.isSelected()) {
            item1.setSelected(true);
        } else {
            item2.setSelected(true);
        }
        //禁用点击事件
        itemDays.setEnabled(false);
        itemNews.setEnabled(false);
    }

    /**
     * 日子、新闻选择面板移到左上角，同时显示九宫面板
     */
    private void startDaysNewsPanelAnim() {
        //日子新闻面板中心点修改为左上角，向左上角缩放
        panelHomepage.setPivotX(0);
        panelHomepage.setPivotY(0);

        //获得日子新闻面板左上角在屏幕中的实际坐标
        int[] locationHomepage = new int[2];
        panelHomepage.getLocationOnScreen(locationHomepage);
        int panelHomepageY = locationHomepage[1];

        //获得九宫面板左上角在屏幕中的实际坐标
        int[] locationSecond = new int[2];
        panelSecond.getLocationOnScreen(locationSecond);
        int panelSecondY = locationSecond[1];
        //计算当前日子新闻面板在Y方向偏移量
        final float currY = panelHomepage.getTranslationY();
        //计算要执行的偏移量大小
        final float length = Math.abs(panelSecondY - panelHomepageY);

        ValueAnimator animatorY = ValueAnimator.ofFloat(currY, -length);
        animatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                panelHomepage.setTranslationY(value);
                value = Math.abs(value);
                //设置缩放比例
                float scalePanel = Math.abs((length - value) / length);// 1~0
                float scale = scalePanel * 0.35f + 0.65f;// 1~0.65
                panelHomepage.setScaleX(scale);
                panelHomepage.setScaleY(scale);

                //隐藏提示语
                ivHomepage.setAlpha(scalePanel - 0.5f);
                ivWord.setAlpha(scalePanel - 0.5f);

                //显示九宫图
                if (scalePanel <= 0.25) {//0.25~0
                    float secondAlpha = scalePanel * 4;//1~0
                    panelSecond.setAlpha(1 - secondAlpha);//0~1n
                }
            }
        });
        animatorY.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                item1.animate().alpha(1).setDuration(800).start();
                item2.animate().alpha(1).setDuration(800).start();
                itemDays.animate().alpha(0).setDuration(450).start();
                itemNews.animate().alpha(0).setDuration(450).start();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startTransformAnim();
                    }
                }, 1500);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        AnimatorSet set = new AnimatorSet();
        set.setDuration(2000);
        set.playTogether(animatorY);
        set.start();
    }

    /**
     * 开始位移变形动画
     */
    private void startTransformAnim() {
        //九宫日子在屏幕中的坐标
        int[] locationDays = new int[2];
        item1.getLocationOnScreen(locationDays);
        int daysX = locationDays[0];
        int daysY = locationDays[1];
        //九宫新闻在屏幕中的坐标
        int[] locationNews = new int[2];
        item2.getLocationOnScreen(locationNews);
        int newsX = locationNews[0];
        int newsY = locationNews[1];
        //九宫服务在屏幕中的坐标
        int[] locationService = new int[2];
        itemService.getLocationOnScreen(locationService);
        int serviceX = locationService[0];
        int serviceY = locationService[1];
        //九宫栏目在屏幕中的坐标
        int[] locationColumn = new int[2];
        itemColumn.getLocationOnScreen(locationColumn);
        int columnX = locationColumn[0];
        int columnY = locationColumn[1];

        //日子菜单在屏幕中的坐标
        int[] locationMenuDays = new int[2];
        itemMenuDays.getLocationOnScreen(locationMenuDays);
        int menuDaysX = locationMenuDays[0];
        int menuDaysY = locationMenuDays[1];
        //新闻菜单在屏幕中的坐标
        int[] locationMenuNews = new int[2];
        itemMenuNews.getLocationOnScreen(locationMenuNews);
        int menuNewsX = locationMenuNews[0];
        int menuNewsY = locationMenuNews[1];
        //服务菜单在屏幕中的坐标
        int[] locationMenuService = new int[2];
        itemMenuService.getLocationOnScreen(locationMenuService);
        int menuServiceX = locationMenuService[0];
        int menuServiceY = locationMenuService[1];
        //栏目菜单在屏幕中的坐标
        int[] locationMenuColumn = new int[2];
        itemMenuColumn.getLocationOnScreen(locationMenuColumn);
        int menuColumnX = locationMenuColumn[0];
        int menuColumnY = locationMenuColumn[1];

        //日子菜单偏移量
        final float translateDaysX = menuDaysX - daysX;
        float translateDaysY = menuDaysY - daysY;

        //新闻菜单偏移量
        float translateNewsX = menuNewsX - newsX;
        float translateNewsY = menuNewsY - newsY;

        //服务菜单偏移量
        float translateServiceX = menuServiceX - serviceX;
        float translateServiceY = menuServiceY - serviceY;

        //栏目菜单偏移量
        float translateColumnX = menuColumnX - columnX;
        float translateColumnY = menuColumnY - columnY;

        //将中心点修改到左上角
        item1.setPivotX(0);
        item1.setPivotY(0);

        item2.setPivotX(0);
        item2.setPivotY(0);

        itemService.setPivotX(0);
        itemService.setPivotY(0);

        itemColumn.setPivotX(0);
        itemColumn.setPivotY(0);

        //日子
        ValueAnimator animDaysX = ValueAnimator.ofFloat(item1.getTranslationX(), translateDaysX);
        animDaysX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                item1.setTranslationX(value);
                //动画执行进度 1~0
                float scale = Math.abs((translateDaysX - value) / translateDaysX);//1~0

                //隐藏背景图片
                item1.findViewById(R.id.anim_bg_days1).setAlpha(scale);
                item2.findViewById(R.id.anim_bg_news1).setAlpha(scale);
                itemService.findViewById(R.id.anim_bg_service).setAlpha(scale);
                itemColumn.findViewById(R.id.anim_bg_column).setAlpha(scale);

                //隐藏圆角边框
                item1.findViewById(R.id.anim_stroke_bg_days1).setAlpha(scale);
                item2.findViewById(R.id.anim_stroke_bg_news1).setAlpha(scale);
                itemService.findViewById(R.id.anim_stroke_bg_service).setAlpha(scale);
                itemColumn.findViewById(R.id.anim_stroke_bg_column).setAlpha(scale);

                //显示椭圆View
                item1.findViewById(R.id.anim_oval_days1).setAlpha(1 - scale);
                item2.findViewById(R.id.anim_oval_news1).setAlpha(1 - scale);
                itemService.findViewById(R.id.anim_oval_service).setAlpha(1 - scale);
                itemColumn.findViewById(R.id.anim_oval_column).setAlpha(1 - scale);

                //隐藏九宫图
                float scale_ = scale - 0.5f;
                item4.setAlpha(scale_);
                item6.setAlpha(scale_);
                item8.setAlpha(scale_);
                item9.setAlpha(scale_);

                //显示环形菜单
                panelCircleMenu.setAlpha(1 - scale);

                //等比例缩小
                float shrinkScaleX = scale * 0.55f + 0.45f;//1~0.45
                float shrinkScaleY = scale * 0.67f + 0.33f;//1~0.33
                item1.setScaleX(shrinkScaleX);
                item1.setScaleY(shrinkScaleY);

                item2.setScaleX(shrinkScaleX);
                item2.setScaleY(shrinkScaleY);

                itemService.setScaleX(shrinkScaleX);
                itemService.setScaleY(shrinkScaleY);

                itemColumn.setScaleX(shrinkScaleX);
                itemColumn.setScaleY(shrinkScaleY);
            }
        });
        ValueAnimator animDaysY = ValueAnimator.ofFloat(item1.getTranslationY(), translateDaysY);
        animDaysY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                item1.setTranslationY(value);
            }
        });
        //新闻
        ValueAnimator animNewsX = ValueAnimator.ofFloat(item2.getTranslationX(), translateNewsX);
        animNewsX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                item2.setTranslationX(value);
            }
        });
        ValueAnimator animNewsY = ValueAnimator.ofFloat(item2.getTranslationY(), translateNewsY);
        animNewsY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                item2.setTranslationY(value);
            }
        });
        //服务
        ValueAnimator animServiceX = ValueAnimator.ofFloat(itemService.getTranslationX(), translateServiceX);
        animServiceX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                itemService.setTranslationX(value);
            }
        });
        ValueAnimator animServiceY = ValueAnimator.ofFloat(itemService.getTranslationY(), translateServiceY);
        animServiceY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                itemService.setTranslationY(value);
            }
        });
        //栏目
        ValueAnimator animColumnX = ValueAnimator.ofFloat(itemColumn.getTranslationX(), translateColumnX);
        animColumnX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                itemColumn.setTranslationX(value);
            }
        });
        ValueAnimator animColumnY = ValueAnimator.ofFloat(itemColumn.getTranslationY(), translateColumnY);
        animColumnY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                itemColumn.setTranslationY(value);
            }
        });
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                animDaysX, animDaysY,
                animNewsX, animNewsY,
                animServiceX, animServiceY,
                animColumnX, animColumnY
        );
        set.setDuration(2000);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Intent intent = new Intent(mContext, RealmActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.anim_zoomin, R.anim.anim_zoomout);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        set.start();
    }
}
