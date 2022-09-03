package com.wiryadev.shared.data.local.datastore

import com.wiryadev.shared.data.model.response.UserResponse
import kotlinx.coroutines.flow.Flow

interface UserPreferenceDataSource {

    suspend fun clearData()

    suspend fun getUserToken(): Flow<String>

    suspend fun setUserToken(newToken: String)

    suspend fun getUserLoginStatus(): Flow<Boolean>

    suspend fun setUserLoginStatus(isUserLoggedIn: Boolean)

    suspend fun getCurrentUser(): Flow<UserResponse>

    suspend fun setCurrentUser(user: UserResponse)
}
