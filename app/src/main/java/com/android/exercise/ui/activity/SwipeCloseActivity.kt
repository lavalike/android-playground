package com.android.exercise.ui.activity

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.android.exercise.R
import com.android.exercise.ui.widget.nineoldandroids.animation.Animator
import com.android.exercise.ui.widget.nineoldandroids.animation.AnimatorListenerAdapter
import com.android.exercise.ui.widget.nineoldandroids.animation.AnimatorSet
import com.android.exercise.ui.widget.nineoldandroids.animation.ValueAnimator
import kotlinx.android.synthetic.main.activity_swipe_close.*
import kotlin.math.abs

/**
 * swipe close activity
 * Created by wangzhen on 11/27/20.
 */
class SwipeCloseActivity : AppCompatActivity() {
    private var initialY: Float = 0f
    private var progress: Float = 0f
    private var delta: Float = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe_close)
        window.setBackgroundDrawable(ColorDrawable(Color.parseColor("#00000000")))
    }

    private fun renderBackground(progress: Float) {
        if (imageView.parent is ViewGroup) {
            val parent: ViewGroup = imageView.parent as ViewGroup
            parent.setBackgroundColor(colorTransform(progress, Color.parseColor("#000000"), Color.parseColor("#00000000")))
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                initialY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                delta = event.y - initialY
                imageView.translationY = delta

                progress = abs(delta * 1f / imageView.height)
                imageView.alpha = 1 - progress
                renderBackground(progress)
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                val threshold = imageView.height * 1f / 2
                if (delta >= threshold) {
                    animateFinish()
                } else {
                    reset()
                }
            }
        }
        return super.onTouchEvent(event)
    }

    private fun animateFinish() {
        val animatorTranslation = ValueAnimator.ofFloat(delta, imageView.height.toFloat()).apply {
            addUpdateListener { animation ->
                val value = animation.animatedValue as Float
                imageView.translationY = value
            }
        }
        val animatorBackground = ValueAnimator.ofFloat(progress, 1f).apply {
            addUpdateListener { animation ->
                val value: Float = animation.animatedValue as Float
                renderBackground(value)
            }
        }
        AnimatorSet().apply {
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    finish()
                }
            })
            playTogether(animatorTranslation, animatorBackground)
            duration = 150
            start()
        }
    }

    private fun reset() {
        ValueAnimator.ofFloat(progress, 0f).apply {
            addUpdateListener { animation ->
                val value: Float = animation.animatedValue as Float
                renderBackground(value)
            }
            start()
        }
        imageView.animate().translationY(0f).alpha(1f).start()
    }

    /**
     * 颜色渐变
     *
     * @param progress  进度 0~1
     * @param fromColor 开始颜色(ARGB)
     * @param toColor   结束颜色(ARGB)
     * @return 变化后的颜色值(ARGB)
     */
    private fun colorTransform(progress: Float, fromColor: Int, toColor: Int): Int {
        val oa = fromColor shr 24 and 0xFF
        val or = fromColor shr 16 and 0xFF
        val og = fromColor shr 8 and 0xFF
        val ob = fromColor and 0xFF
        val na = toColor shr 24 and 0xFF
        val nr = toColor shr 16 and 0xFF
        val ng = toColor shr 8 and 0xFF
        val nb = toColor and 0xFF
        val aGap = ((na - oa).toFloat() * progress).toInt()
        val rGap = ((nr - or).toFloat() * progress).toInt()
        val gGap = ((ng - og).toFloat() * progress).toInt()
        val bGap = ((nb - ob).toFloat() * progress).toInt()
        return (oa + aGap shl 24) or (or + rGap shl 16) or (og + gGap shl 8) or (ob + bGap)
    }
}