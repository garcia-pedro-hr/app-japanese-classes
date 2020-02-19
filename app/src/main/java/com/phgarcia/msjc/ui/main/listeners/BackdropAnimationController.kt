package com.phgarcia.msjc.ui.main.listeners

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.Interpolator
import android.widget.ImageView
import com.phgarcia.msjc.R

/**
 * [android.view.View.OnClickListener] used to translate the product grid sheet downward on
 * the Y-axis when the navigation icon in the toolbar is pressed.
 */
class BackdropAnimationController @JvmOverloads internal constructor(
        private val context: Context,
        private val sheet: View,
        private val interpolator: Interpolator? = null,
        private val openIcon: Drawable? = null,
        private val closeIcon: Drawable? = null
) {

    private val animatorSet = AnimatorSet()
    private val height: Int
    private var visible = false
    private var navIconView: View? = null

    var navIconClickListener = View.OnClickListener {
        navIconView = it
        toggleVisibility()
    }

    init {
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        height = displayMetrics.heightPixels
    }

    fun show() {
        if (!visible) {
            val translateY =
                height - context.resources.getDimensionPixelSize(R.dimen.backdrop_reveal_height)
            animate(translateY)
        }
    }

    fun hide() {
        if (visible) animate(0)
    }

    private fun toggleVisibility() = if (visible) hide() else show()

    private fun cancelAnimations() {
        // Cancel the existing animations
        animatorSet.removeAllListeners()
        animatorSet.end()
        animatorSet.cancel()
    }

    private fun animate(translateY: Int) {
        visible = !visible
        cancelAnimations()
        updateIcon(navIconView)
        val animator =
            ObjectAnimator.ofFloat(sheet, "translationY", translateY.toFloat())
        animator.duration = 250
        if (interpolator != null) {
            animator.interpolator = interpolator
        }
        animatorSet.play(animator)
        animator.start()
    }

    private fun updateIcon(view: View?) {
        if (view != null && openIcon != null && closeIcon != null) {
            if (view !is ImageView) {
                throw IllegalArgumentException("updateIcon() must be called on an ImageView")
            }
            view.setImageDrawable(if (visible) closeIcon else openIcon)
        }
    }

}
