package com.ssong_develop.tadaassignment.local

import android.content.Context
import androidx.core.content.edit

class SharedPref(
    context: Context
) {
    private val sharedPreferences = context.getSharedPreferences("sharedPreferences", 0)

    // 단 한번만 호출 될 함수가 호출이 되었는지 안되었는지를 확인하는 함수
    fun isAlreadySingleInvoke(): Boolean = sharedPreferences.getBoolean(IS_SINGLE_INVOKE_KEY, false)

    // 단 한번만 호출되어야 하는 함수가 호출 되었을 때 true 값을 설정
    fun onSingleInvoke() = sharedPreferences.edit(true) {
        putBoolean(IS_SINGLE_INVOKE_KEY, true)
    }

    // 단 한번만 호출되어야 하는 함수를 다시 재호출해야할 때 값을 false로 설정해서 재호출하도록 한다.
    fun releaseSingleInvoke() = sharedPreferences.edit(true) {
        putBoolean(IS_SINGLE_INVOKE_KEY, false)
    }

    companion object {
        private const val IS_SINGLE_INVOKE_KEY = "SINGLE_INVOKE"
    }
}