package com.ssong_develop.tadaassignment.utils

import android.view.View
import androidx.databinding.BindingAdapter

typealias OnClickListener = (View) -> Unit

class OnDebounceClickListener(private val listener: OnClickListener) : View.OnClickListener {
    override fun onClick(v: View?) {
        val now = System.currentTimeMillis()
        if (now < lastTime + INTEVAL) return
        lastTime = now
        v?.run(listener)
    }

    companion object {
        private const val INTEVAL: Long = 500L
        private var lastTime: Long = 0
    }
}

@BindingAdapter("android:onDebounceClick")
fun View.setOnDebounceClickListener(listener: View.OnClickListener) {
    if (listener == null) {
        this.setOnClickListener(null)
    } else {
        this.setOnClickListener(OnDebounceClickListener {
            listener.onClick(it)
        })
    }
}