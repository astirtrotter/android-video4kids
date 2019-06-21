package com.example.video4kids.helpers

import android.content.Context
import android.content.SharedPreferences
import com.example.video4kids.Constants

object SharedPreferencesHelper {

    val sharedPreferences: SharedPreferences
        get() = Constants.context!!.getSharedPreferences(Constants.StringConstants.PREF_APP, Context.MODE_PRIVATE)


}