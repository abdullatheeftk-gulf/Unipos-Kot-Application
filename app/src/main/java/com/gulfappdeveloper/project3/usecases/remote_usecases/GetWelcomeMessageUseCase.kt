package com.gulfappdeveloper.project3.usecases.remote_usecases

import com.gulfappdeveloper.project3.domain.remote.get.GetDataFromRemote
import com.gulfappdeveloper.project3.domain.remote.get.welcome.WelcomeMessage
import com.gulfappdeveloper.project3.repositories.RemoteRepository
import kotlinx.coroutines.flow.Flow

class GetWelcomeMessageUseCase(
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke(url: String): Flow<GetDataFromRemote<WelcomeMessage>> {
        return remoteRepository.getWelcomeMessage(url = url)
    }
}