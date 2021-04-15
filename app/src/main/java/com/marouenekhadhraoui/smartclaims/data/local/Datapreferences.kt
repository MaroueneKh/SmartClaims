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
        val TYPE = stringPreferencesKey("type")
        val LAT = stringPreferencesKey("lat")
        val LONG = stringPreferencesKey("lang")
        val SCAN1 = stringPreferencesKey("scan1")
        val SCAN2 = stringPreferencesKey("scan1")
        val VID1 = stringPreferencesKey("vid1")
        val VID2 = stringPreferencesKey("vid2")
        val DEGATS1 = stringPreferencesKey("degats1")
        val DEGATS2 = stringPreferencesKey("degats2")
        val DEGATS3 = stringPreferencesKey("degats3")
        val DEGATS4 = stringPreferencesKey("degats4")

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
    val type: Flow<String> = context.dataStore.data
        .map { settings ->
            // No type safety.
            settings[TYPE] ?: "false"
        }

    val lat: Flow<String> = context.dataStore.data
        .map { settings ->
            // No type safety.
            settings[LAT] ?: "false"
        }
    val long: Flow<String> = context.dataStore.data
        .map { settings ->
            // No type safety.
            settings[LONG] ?: "false"
        }
    val scan1: Flow<String> = context.dataStore.data
        .map { settings ->
            // No type safety.
            settings[SCAN1] ?: "false"
        }
    val scan2: Flow<String> = context.dataStore.data
        .map { settings ->
            // No type safety.
            settings[SCAN2] ?: "false"
        }
    val vid1: Flow<String> = context.dataStore.data
        .map { settings ->
            // No type safety.
            settings[VID1] ?: "false"
        }
    val vid2: Flow<String> = context.dataStore.data
        .map { settings ->
            // No type safety.
            settings[VID2] ?: "false"
        }
    val degats1: Flow<String> = context.dataStore.data
        .map { settings ->
            // No type safety.
            settings[DEGATS1] ?: "false"
        }
    val degats2: Flow<String> = context.dataStore.data
        .map { settings ->
            // No type safety.
            settings[DEGATS2] ?: "false"
        }
    val degats3: Flow<String> = context.dataStore.data
        .map { settings ->
            // No type safety.
            settings[DEGATS3] ?: "false"
        }
    val degats4: Flow<String> = context.dataStore.data
        .map { settings ->
            // No type safety.
            settings[DEGATS4] ?: "false"
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

    suspend fun setType(context: Context, token: String) {
        context.dataStore.edit { settings ->
            settings[TYPE] = token
        }
    }

    suspend fun setlat(context: Context, token: String) {
        context.dataStore.edit { settings ->
            settings[LAT] = token
        }
    }

    suspend fun setlong(context: Context, token: String) {
        context.dataStore.edit { settings ->
            settings[LONG] = token
        }
    }

    suspend fun setscan1(context: Context, token: String) {
        context.dataStore.edit { settings ->
            settings[SCAN1] = token
        }
    }

    suspend fun setscan2(context: Context, token: String) {
        context.dataStore.edit { settings ->
            settings[SCAN2] = token
        }
    }

    suspend fun setvid1(context: Context, token: String) {
        context.dataStore.edit { settings ->
            settings[VID1] = token
        }
    }

    suspend fun setvid2(context: Context, token: String) {
        context.dataStore.edit { settings ->
            settings[VID2] = token
        }
    }

    suspend fun setdegats1(context: Context, token: String) {
        context.dataStore.edit { settings ->
            settings[DEGATS1] = token
        }
    }

    suspend fun setdegats2(context: Context, token: String) {
        context.dataStore.edit { settings ->
            settings[DEGATS2] = token
        }
    }

    suspend fun setdegats3(context: Context, token: String) {
        context.dataStore.edit { settings ->
            settings[DEGATS3] = token
        }
    }

    suspend fun setdegats4(context: Context, token: String) {
        context.dataStore.edit { settings ->
            settings[DEGATS4] = token
        }
    }


}