package com.gulfappdeveloper.project3.domain.services

import com.gulfappdeveloper.project3.domain.remote.get.GetDataFromRemote
import com.gulfappdeveloper.project3.domain.remote.get.welcome.WelcomeMessage
import kotlinx.coroutines.flow.Flow

interface ApiService {
    suspend fun getWelcomeMessage(url: String): Flow<GetDataFromRemote<WelcomeMessage>>
}