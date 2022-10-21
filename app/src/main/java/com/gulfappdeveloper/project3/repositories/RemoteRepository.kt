package com.gulfappdeveloper.project3.repositories

import com.gulfappdeveloper.project3.domain.remote.get.GetDataFromRemote
import com.gulfappdeveloper.project3.domain.remote.get.welcome.WelcomeMessage
import com.gulfappdeveloper.project3.domain.services.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RemoteRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getWelcomeMessage(url: String): Flow<GetDataFromRemote<WelcomeMessage>> {
        return apiService.getWelcomeMessage(url = url)
    }
}