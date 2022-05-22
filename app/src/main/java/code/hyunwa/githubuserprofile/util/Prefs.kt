package code.hyunwa.githubuserprofile.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class Prefs(activity: Activity) {
    private  var sp : SharedPreferences? = null
    init {
        sp = activity.getSharedPreferences("MY_APP", Context.MODE_PRIVATE)
    }

    val login = "LOGIN"
    fun setLogin(value:Boolean){
        sp!!.edit().putBoolean(login, value)
    }
    fun getLogin():Boolean{
        return sp!!.getBoolean(login, false)
    }
}