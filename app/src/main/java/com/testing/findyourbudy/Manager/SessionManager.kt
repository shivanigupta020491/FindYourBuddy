package com.testing.findyourbudy.Manager

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.testing.findyourbudy.activity.LoginActivity

class SessionManager(var context: Context) {

    private val KEY_FOR_LOGIN_ID = "loginId"
    private val KEY_FOR_IS_LOGIN = "isLogin"
    val KEY_FOR_PREFERENCES = "LOGIN_CREDENTIAL"
    private val KEY_FOR_MOBILENO = "mobileno"

    private val preferences: SharedPreferences = context.getSharedPreferences(
        KEY_FOR_PREFERENCES, Context.MODE_PRIVATE
    )
    private val editor: SharedPreferences.Editor = preferences.edit()

    fun logOutUser() {
        editor.clear()
        editor.commit()
        context.startActivity(Intent(context, LoginActivity::class.java))
    }

    fun setMobileNo(mobileNo: String?) {
        editor.putString(KEY_FOR_MOBILENO, mobileNo)
        editor.commit()
    }

    fun getMobileNo(): String? {
        return preferences.getString(KEY_FOR_MOBILENO, "")
    }

}