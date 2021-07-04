package com.ssong_develop.tadaassignment.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.hideKeyboard() {
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).also {
        it.hideSoftInputFromWindow(windowToken, 0)
    }
}