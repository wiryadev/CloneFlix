package com.wiryadev.shared.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class UserPreferenceFactory(
    private val appContext: Context,
) {

    fun create(): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { appContext.preferencesDataStoreFile(USER_PREFERENCE) }
        )
    }

}

private const val USER_PREFERENCE = "user_preference"

object UserPreferenceKey {
    private const val PREF_USER_TOKEN = "pref_user_token"
    private const val PREF_IS_USER_LOGIN = "pref_is_user_login"
    private const val PREF_USER_OBJECT = "pref_user_object"

    val userToken = stringPreferencesKey(PREF_USER_TOKEN)
    val isUserLoggedIn = booleanPreferencesKey(PREF_IS_USER_LOGIN)
    val userObject = stringPreferencesKey(PREF_USER_OBJECT)
}