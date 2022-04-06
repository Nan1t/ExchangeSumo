package ua.nanit.extop.storage

import android.content.Context
import ua.nanit.extop.monitoring.LocalStorage

class LocalStorageFactory(
    private val ctx: Context,
    private val name: String
) : LocalStorage.Factory {

    override fun create(): LocalStorage {
        val prefs = ctx.getSharedPreferences(name, Context.MODE_PRIVATE)
        return LocalPrefsStorage(prefs)
    }

}