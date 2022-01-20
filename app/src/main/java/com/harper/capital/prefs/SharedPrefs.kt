package com.harper.capital.prefs

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class SharedPrefs(private val sharedPrefs: SharedPreferences) {

    fun <T : Any> prefFlow(key: String, defaultValue: T): Flow<T> = callbackFlow {
        trySend(getPref(key, defaultValue))
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, prefKey ->
            if (key == prefKey) {
                trySend(getPref(prefKey, defaultValue))
            }
        }
        try {
            sharedPrefs.registerOnSharedPreferenceChangeListener(listener)
            awaitCancellation()
        } finally {
            sharedPrefs.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }

    fun <T : Any> pref(key: String, defaultValue: T): ReadWriteProperty<Any, T> =
        object : ReadWriteProperty<Any, T> {

            override fun getValue(thisRef: Any, property: KProperty<*>): T = getPref(key, defaultValue)

            override fun setValue(thisRef: Any, property: KProperty<*>, value: T) = setPref(key, value)
        }

    fun <T : Any> setPref(key: String, value: T) = sharedPrefs.edit {
        when (value) {
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
            is Boolean -> putBoolean(key, value)
        }
    }

    private fun <T : Any> getPref(key: String, defaultValue: T): T =
        (sharedPrefs.all[key] as? T) ?: defaultValue
}
