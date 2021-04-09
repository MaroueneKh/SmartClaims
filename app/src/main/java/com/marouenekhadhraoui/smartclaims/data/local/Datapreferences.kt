package com.marouenekhadhraoui.smartclaims.data.local


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Datapreferences @Inject constructor(@ApplicationContext context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    companion object {
        val CONNECTED = booleanPreferencesKey("connected")
        val DARKENABLED = booleanPreferencesKey("darkenabled")
        val TOKEN = stringPreferencesKey("token")

    }

    val status: Flow<Boolean> = context.dataStore.data
            .map { settings ->
                // No type safety.
                settings[CONNECTED] ?: false
            }
    val darkmode: Flow<Boolean> = context.dataStore.data
            .map { settings ->
                // No type safety.
                settings[DARKENABLED] ?: false
            }


    suspend fun setStatus(context: Context) {
        context.dataStore.edit { settings ->
            val currentStatus = settings[CONNECTED] ?: false
            settings[CONNECTED] = !currentStatus
        }
    }

    suspend fun setToken(context: Context, token: String) {
        context.dataStore.edit { settings ->
            settings[TOKEN] = token
        }
    }


}