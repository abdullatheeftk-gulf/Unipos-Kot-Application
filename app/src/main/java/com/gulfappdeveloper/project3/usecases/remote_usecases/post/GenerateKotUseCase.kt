package com.gulfappdeveloper.project3.usecases.remote_usecases.post

import com.gulfappdeveloper.project3.domain.remote.post.Kot
import com.gulfappdeveloper.project3.repositories.RemoteRepository

class GenerateKotUseCase(
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke(url: String, kot: Kot, callBack: suspend (Int, String) -> Unit) {
        remoteRepository.generateKOT(url = url, kot = kot, callBack = callBack)
    }
}