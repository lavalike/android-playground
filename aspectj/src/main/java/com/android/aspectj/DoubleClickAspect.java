package com.android.aspectj;

import android.util.Log;
import android.view.View;

import com.android.aspectj.annotation.DoubleClick;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * anti double click
 * Created by wangzhen on 2020/8/19.
 */
@Aspect
public class DoubleClickAspect {
    private static final int DEFAULT_INTERVAL = 500;

    private static final String ONCLICK = "execution(void android.view.View.OnClickListener+.onClick(..))";
    private static final String ONCLICK_XML = "execution(void androidx.appcompat.app.AppCompatViewInflater.DeclaredOnClickListener.onClick(View))";
    private static final String ONCLICK_BUTTER_KNIFE = "execution(@butterknife.OnClick * *(..))";

    @Pointcut(ONCLICK)
    public void onClick() {
    }

    @Pointcut(ONCLICK_XML)
    public void onClickXml() {
    }

    @Pointcut(ONCLICK_BUTTER_KNIFE)
    public void onClickButterKnife() {

    }

    @Around("onClick() || onClickXml() || onClickButterKnife()")
    public void process(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        if (signature instanceof MethodSignature) {
            Method method = ((MethodSignature) signature).getMethod();
            boolean present = method.isAnnotationPresent(DoubleClick.class);
            DoubleClick annotation = method.getAnnotation(DoubleClick.class);
            if (present && annotation != null) {
                View view = getView(joinPoint);
                if (view != null) {
                    int interval = annotation.value();
                    if (interval <= 0)
                        interval = DEFAULT_INTERVAL;
                    Object tag = view.getTag(R.id.tag_last_click_time);
                    view.setTag(R.id.tag_last_click_time, System.currentTimeMillis());
                    if (tag == null) tag = 0L;
                    long diff = System.currentTimeMillis() - (long) tag;
                    if (diff < interval) {
                        Log.e("AspectJ", "-> 触发防连击");
                        return;
                    }
                }
            }
        }
        joinPoint.proceed();
    }

    private View getView(ProceedingJoinPoint joinPoint) {
        View view = null;
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof View) {
            view = (View) args[0];
        }
        return view;
    }
}
