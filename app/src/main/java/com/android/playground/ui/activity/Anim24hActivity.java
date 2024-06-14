package com.android.playground.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.android.playground.R;
import com.android.playground.base.BaseActivity;
import com.android.playground.databinding.ActivityAnim24hBinding;
import com.android.playground.ui.activity.database.ObjectBoxActivity;
import com.wangzhen.statusbar.DarkStatusBar;

/**
 * 24hour引导动画
 * Created by wangzhen on 2016/12/26/026
 */
public class Anim24hActivity extends BaseActivity {
    private ActivityAnim24hBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityAnim24hBinding.inflate(getLayoutInflater())).getRoot());
        DarkStatusBar.get().fitDark(this);
        addEvents();
    }

    public void addEvents() {
        binding.itemDays.setOnClickListener(v -> {
            binding.itemDays.setSelected(true);
            binding.itemNews.setSelected(false);
            startDaysNewsPanelAnim();
        });
        binding.itemNews.setOnClickListener(v -> {
            binding.itemDays.setSelected(false);
            binding.itemNews.setSelected(true);
            startDaysNewsPanelAnim();
        });
    }

    /**
     * 日子、新闻选择面板移到左上角，同时显示九宫面板
     */
    private void startDaysNewsPanelAnim() {
        if (binding.itemDays.isSelected()) {
            binding.item1.setSelected(true);
        } else {
            binding.item2.setSelected(true);
        }
        //禁用点击事件
        binding.itemDays.setEnabled(false);
        binding.itemNews.setEnabled(false);

        //日子新闻面板中心点修改为左上角，向左上角缩放
        binding.panelHomepage.setPivotX(0);
        binding.panelHomepage.setPivotY(0);

        //获得日子新闻面板左上角在屏幕中的实际坐标
        int[] locationHomepage = new int[2];
        binding.panelHomepage.getLocationOnScreen(locationHomepage);
        int panelHomepageY = locationHomepage[1];

        //获得九宫面板左上角在屏幕中的实际坐标
        int[] locationSecond = new int[2];
        binding.panelSecond.getLocationOnScreen(locationSecond);
        int panelSecondY = locationSecond[1];
        //计算当前日子新闻面板在Y方向偏移量
        final float currY = binding.panelHomepage.getTranslationY();
        //计算要执行的偏移量大小
        final float length = Math.abs(panelSecondY - panelHomepageY);

        ValueAnimator animatorY = ValueAnimator.ofFloat(currY, -length);
        animatorY.addUpdateListener(animation -> {
            float value = (Float) animation.getAnimatedValue();
            binding.panelHomepage.setTranslationY(value);
            value = Math.abs(value);
            //设置缩放比例
            float scalePanel = Math.abs((length - value) / length);// 1~0
            float scale = scalePanel * 0.35f + 0.65f;// 1~0.65
            binding.panelHomepage.setScaleX(scale);
            binding.panelHomepage.setScaleY(scale);

            //隐藏提示语
            binding.ivHomepage.setAlpha(scalePanel - 0.5f);
            binding.ivWord.setAlpha(scalePanel - 0.5f);

            //显示九宫图
            if (scalePanel <= 0.25) {//0.25~0
                float secondAlpha = scalePanel * 4;//1~0
                binding.panelSecond.setAlpha(1 - secondAlpha);//0~1n
            }
        });
        animatorY.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animation) {

            }

            @Override
            public void onAnimationEnd(@NonNull Animator animation) {
                binding.item1.animate().alpha(1).setDuration(800).start();
                binding.item2.animate().alpha(1).setDuration(800).start();
                binding.itemDays.animate().alpha(0).setDuration(450).start();
                binding.itemNews.animate().alpha(0).setDuration(450).start();
                new Handler().postDelayed(() -> startTransformAnim(), 1500);
            }

            @Override
            public void onAnimationCancel(@NonNull Animator animation) {

            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animation) {
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
        binding.item1.getLocationOnScreen(locationDays);
        int daysX = locationDays[0];
        int daysY = locationDays[1];
        //九宫新闻在屏幕中的坐标
        int[] locationNews = new int[2];
        binding.item2.getLocationOnScreen(locationNews);
        int newsX = locationNews[0];
        int newsY = locationNews[1];
        //九宫服务在屏幕中的坐标
        int[] locationService = new int[2];
        binding.itemService.getLocationOnScreen(locationService);
        int serviceX = locationService[0];
        int serviceY = locationService[1];
        //九宫栏目在屏幕中的坐标
        int[] locationColumn = new int[2];
        binding.itemColumn.getLocationOnScreen(locationColumn);
        int columnX = locationColumn[0];
        int columnY = locationColumn[1];

        //日子菜单在屏幕中的坐标
        int[] locationMenuDays = new int[2];
        binding.itemMenuDays.getLocationOnScreen(locationMenuDays);
        int menuDaysX = locationMenuDays[0];
        int menuDaysY = locationMenuDays[1];
        //新闻菜单在屏幕中的坐标
        int[] locationMenuNews = new int[2];
        binding.itemMenuNews.getLocationOnScreen(locationMenuNews);
        int menuNewsX = locationMenuNews[0];
        int menuNewsY = locationMenuNews[1];
        //服务菜单在屏幕中的坐标
        int[] locationMenuService = new int[2];
        binding.itemMenuService.getLocationOnScreen(locationMenuService);
        int menuServiceX = locationMenuService[0];
        int menuServiceY = locationMenuService[1];
        //栏目菜单在屏幕中的坐标
        int[] locationMenuColumn = new int[2];
        binding.itemMenuColumn.getLocationOnScreen(locationMenuColumn);
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
        binding.item1.setPivotX(0);
        binding.item1.setPivotY(0);

        binding.item2.setPivotX(0);
        binding.item2.setPivotY(0);

        binding.itemService.setPivotX(0);
        binding.itemService.setPivotY(0);

        binding.itemColumn.setPivotX(0);
        binding.itemColumn.setPivotY(0);

        //日子
        ValueAnimator animDaysX = ValueAnimator.ofFloat(binding.item1.getTranslationX(), translateDaysX);
        animDaysX.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            binding.item1.setTranslationX(value);
            //动画执行进度 1~0
            float scale = Math.abs((translateDaysX - value) / translateDaysX);//1~0

            //隐藏背景图片
            binding.item1.findViewById(R.id.anim_bg_days1).setAlpha(scale);
            binding.item2.findViewById(R.id.anim_bg_news1).setAlpha(scale);
            binding.itemService.findViewById(R.id.anim_bg_service).setAlpha(scale);
            binding.itemColumn.findViewById(R.id.anim_bg_column).setAlpha(scale);

            //隐藏圆角边框
            binding.item1.findViewById(R.id.anim_stroke_bg_days1).setAlpha(scale);
            binding.item2.findViewById(R.id.anim_stroke_bg_news1).setAlpha(scale);
            binding.itemService.findViewById(R.id.anim_stroke_bg_service).setAlpha(scale);
            binding.itemColumn.findViewById(R.id.anim_stroke_bg_column).setAlpha(scale);

            //显示椭圆View
            binding.item1.findViewById(R.id.anim_oval_days1).setAlpha(1 - scale);
            binding.item2.findViewById(R.id.anim_oval_news1).setAlpha(1 - scale);
            binding.itemService.findViewById(R.id.anim_oval_service).setAlpha(1 - scale);
            binding.itemColumn.findViewById(R.id.anim_oval_column).setAlpha(1 - scale);

            //隐藏九宫图
            float scale_ = scale - 0.5f;
            binding.item4.setAlpha(scale_);
            binding.item6.setAlpha(scale_);
            binding.item8.setAlpha(scale_);
            binding.item9.setAlpha(scale_);

            //显示环形菜单
            binding.panelCircleMenu.setAlpha(1 - scale);

            //等比例缩小
            float shrinkScaleX = scale * 0.55f + 0.45f;//1~0.45
            float shrinkScaleY = scale * 0.67f + 0.33f;//1~0.33
            binding.item1.setScaleX(shrinkScaleX);
            binding.item1.setScaleY(shrinkScaleY);

            binding.item2.setScaleX(shrinkScaleX);
            binding.item2.setScaleY(shrinkScaleY);

            binding.itemService.setScaleX(shrinkScaleX);
            binding.itemService.setScaleY(shrinkScaleY);

            binding.itemColumn.setScaleX(shrinkScaleX);
            binding.itemColumn.setScaleY(shrinkScaleY);
        });
        ValueAnimator animDaysY = ValueAnimator.ofFloat(binding.item1.getTranslationY(), translateDaysY);
        animDaysY.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            binding.item1.setTranslationY(value);
        });
        //新闻
        ValueAnimator animNewsX = ValueAnimator.ofFloat(binding.item2.getTranslationX(), translateNewsX);
        animNewsX.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            binding.item2.setTranslationX(value);
        });
        ValueAnimator animNewsY = ValueAnimator.ofFloat(binding.item2.getTranslationY(), translateNewsY);
        animNewsY.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            binding.item2.setTranslationY(value);
        });
        //服务
        ValueAnimator animServiceX = ValueAnimator.ofFloat(binding.itemService.getTranslationX(), translateServiceX);
        animServiceX.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            binding.itemService.setTranslationX(value);
        });
        ValueAnimator animServiceY = ValueAnimator.ofFloat(binding.itemService.getTranslationY(), translateServiceY);
        animServiceY.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            binding.itemService.setTranslationY(value);
        });
        //栏目
        ValueAnimator animColumnX = ValueAnimator.ofFloat(binding.itemColumn.getTranslationX(), translateColumnX);
        animColumnX.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            binding.itemColumn.setTranslationX(value);
        });
        ValueAnimator animColumnY = ValueAnimator.ofFloat(binding.itemColumn.getTranslationY(), translateColumnY);
        animColumnY.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            binding.itemColumn.setTranslationY(value);
        });
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animDaysX, animDaysY, animNewsX, animNewsY, animServiceX, animServiceY, animColumnX, animColumnY);
        set.setDuration(2000);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animation) {

            }

            @Override
            public void onAnimationEnd(@NonNull Animator animation) {
                Intent intent = new Intent(mContext, ObjectBoxActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.anim_zoomin, R.anim.anim_zoomout);
            }

            @Override
            public void onAnimationCancel(@NonNull Animator animation) {

            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animation) {

            }
        });
        set.start();
    }
}
