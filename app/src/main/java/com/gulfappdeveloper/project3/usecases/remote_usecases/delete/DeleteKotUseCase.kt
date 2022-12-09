package com.gulfappdeveloper.project3.usecases.remote_usecases.delete

import com.gulfappdeveloper.project3.repositories.RemoteRepository

class DeleteKotUseCase(
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke(url: String, callBack: suspend (Int, String) -> Unit) {
        remoteRepository.deleteKOT(url = url, callBack = callBack)
    }
}