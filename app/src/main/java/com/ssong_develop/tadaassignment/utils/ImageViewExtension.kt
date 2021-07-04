package com.ssong_develop.tadaassignment.utils

import android.widget.ImageView

fun ImageView.sizeUpImageView() {
    val params = layoutParams

    params.width = params.width + context.dpToPixel(4)
    params.height = params.height + context.dpToPixel(4)

    layoutParams = params
}

fun ImageView.sizeDownImageView() {
    val params = layoutParams

    params.width -= context.dpToPixel(4)
    params.height -= context.dpToPixel(4)

    layoutParams = params
}
