package com.phgarcia.msjc.extensions

import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.recyclerview.widget.RecyclerView
import com.phgarcia.msjc.R

fun RecyclerView.runLayoutAnimation() {
    val controller: LayoutAnimationController =
        AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
    layoutAnimation = controller
    scheduleLayoutAnimation()
}