package com.android.aspectj;

import android.content.res.Resources;
import android.text.TextUtils;
import android.view.View;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * TrackPointAspect
 * Created by wangzhen on 2020/8/18.
 */
@Aspect
public class TrackPointAspect {
    @Pointcut("execution(* android.view.View.OnClickListener.onClick(..))")
    public void onViewClick() {
    }

    @Pointcut("execution(* android.view.View.OnLongClickListener.onLongClick(..))")
    public void onViewLongClick() {
    }

    /**
     * 拦截全局点击事件
     *
     * @param joinPoint joinPoint
     * @throws Throwable Throwable
     */
    @Around("onViewClick()")
    public void processClickJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = "";
        Object target = joinPoint.getTarget();
        if (target != null) {
            className = target.getClass().getName();
        }
        String entryName = "";
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof View) {
            View view = (View) args[0];
            try {
                entryName = view.getResources().getResourceEntryName(view.getId());
            } catch (Resources.NotFoundException ignore) {
            }
        }
        if (!TextUtils.isEmpty(entryName)) {
            TrackPoint.onClick(className, String.format("R.id.%s", entryName));
        }
        joinPoint.proceed();
    }

//    /**
//     * 拦截全局长按事件
//     *
//     * @param joinPoint joinPoint
//     * @throws Throwable Throwable
//     */
//    @Around("onViewLongClick()")
//    public void processLongClickJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
//        String className = "";
//        Object target = joinPoint.getTarget();
//        if (target != null) {
//            className = target.getClass().getName();
//        }
//        String entryName = "";
//        Object[] args = joinPoint.getArgs();
//        if (args.length > 0 && args[0] instanceof View) {
//            View view = (View) args[0];
//            try {
//                entryName = view.getResources().getResourceEntryName(view.getId());
//            } catch (Resources.NotFoundException ignore) {
//            }
//        }
//        if (!TextUtils.isEmpty(entryName)) {
//            TrackPoint.onLongClick(className, String.format("R.id.%s", entryName));
//        }
//        joinPoint.proceed();
//    }
}
