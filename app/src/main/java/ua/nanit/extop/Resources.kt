package ua.nanit.extop

import android.content.Context
import android.content.SharedPreferences

object Resources {

    fun getPrefs(ctx: Context): SharedPreferences {
        return ctx.getSharedPreferences("config", Context.MODE_PRIVATE)
    }

}