package com.ssong_develop.tadaassignment.utils

import android.content.Context
import android.widget.Toast
import kotlin.math.roundToInt

fun Context.dpToPixel(dp: Int): Int = (dp * resources.displayMetrics.density).roundToInt()

fun Context.dpToPixelFloat(dp: Int): Float =
    (dp * resources.displayMetrics.density).roundToInt().toFloat()

fun Context.pixelToDp(pixel: Int) = (pixel / resources.displayMetrics.density).roundToInt()

fun Context.shortToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()