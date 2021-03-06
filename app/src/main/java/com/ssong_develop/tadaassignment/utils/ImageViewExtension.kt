package com.ssong_develop.tadaassignment.utils

import android.widget.ImageView

fun ImageView.sizeUpImageView(width: Int, height: Int) {
    val params = layoutParams

    params.width = context.pixelToDp(width + 40)
    params.height = context.pixelToDp(height + 40)

    layoutParams = params
}

fun ImageView.rollbackOriginSize(width: Int, height: Int) {
    val params = layoutParams

    params.width = context.pixelToDp(width)
    params.height = context.pixelToDp(height)

    layoutParams = params
}