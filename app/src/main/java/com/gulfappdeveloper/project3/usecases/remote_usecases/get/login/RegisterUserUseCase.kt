package com.gulfappdeveloper.project3.usecases.remote_usecases.get.login

import com.gulfappdeveloper.project3.domain.remote.get.GetDataFromRemote
import com.gulfappdeveloper.project3.domain.remote.get.login.User
import com.gulfappdeveloper.project3.repositories.RemoteRepository
import kotlinx.coroutines.flow.Flow

class RegisterUserUseCase(
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke(url: String): Flow<GetDataFromRemote<User>> {
        return remoteRepository.registerUser(url = url)
    }
}