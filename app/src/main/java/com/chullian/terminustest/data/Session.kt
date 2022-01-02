package com.chullian.terminustest.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Created by binMammootty on 02/01/2022.
 */
class Session @Inject constructor(
    @ApplicationContext private val context: Context
) {
    val prefs = context.getSharedPreferences("Settings", MODE_PRIVATE)

    var isLoggedIn: Boolean
        get() = prefs.getBoolean("isLoggedIn", false)
        set(value) {
            prefs.edit().putBoolean("isLoggedIn", value).apply()
        }
}