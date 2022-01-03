package com.chullian.terminustest.data.persistance

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
    var prefs = context.getSharedPreferences("Settings", MODE_PRIVATE)

    var isLoggedIn: Boolean
        get() = prefs.getBoolean("isLoggedIn", false)
        set(value) {
            prefs.edit().putBoolean("isLoggedIn", value).apply()
        }

    var user: String
        get() = prefs.getString("user","")?:""
        set(value) {
            prefs.edit().putString("user", value).apply()
        }
    var userBio: String
        get() = prefs.getString("userBio","")?:""
        set(value) {
            prefs.edit().putString("userBio", value).apply()
        }

    var userImage: String
        get() = prefs.getString("userImage","")?:""
        set(value) {
            prefs.edit().putString("userImage", value).apply()
        }

    var email: String
        get() = prefs.getString("email","")?:""
        set(value) {
            prefs.edit().putString("email", value).apply()
        }

    fun clear(){
        prefs.edit().clear().apply()
    }
}