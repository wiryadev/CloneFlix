package com.wiryadev.shared.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.google.gson.Gson
import com.wiryadev.shared.data.model.response.UserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferenceDataSourceImpl(
    private val dataStore: DataStore<Preferences>,
    private val gson: Gson,
) : UserPreferenceDataSource {

    override suspend fun clearData() {
        dataStore.edit { it.clear() }
    }

    override suspend fun getUserToken(): Flow<String> {
        return dataStore.data.map { pref ->
            pref[UserPreferenceKey.userToken].orEmpty()
        }
    }

    override suspend fun setUserToken(newToken: String) {
        dataStore.edit {
            it[UserPreferenceKey.userToken] = newToken
        }
    }

    override suspend fun getUserLoginStatus(): Flow<Boolean> {
        return dataStore.data.map { pref ->
            pref[UserPreferenceKey.isUserLoggedIn] ?: false
        }
    }

    override suspend fun setUserLoginStatus(isUserLoggedIn: Boolean) {
        dataStore.edit {
            it[UserPreferenceKey.isUserLoggedIn] = isUserLoggedIn
        }
    }

    override suspend fun getCurrentUser(): Flow<UserResponse> {
        return dataStore.data.map { pref ->
            gson.fromJson(
                pref[UserPreferenceKey.userObject].orEmpty(),
                UserResponse::class.java,
            )
        }
    }

    override suspend fun setCurrentUser(user: UserResponse) {
        dataStore.edit {
            it[UserPreferenceKey.userObject] = gson.toJson(user)
        }
    }
}