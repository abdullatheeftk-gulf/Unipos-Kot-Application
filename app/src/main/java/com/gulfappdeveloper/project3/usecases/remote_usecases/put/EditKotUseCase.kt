package com.gulfappdeveloper.project3.usecases.remote_usecases.put

import com.gulfappdeveloper.project3.domain.remote.post.Kot
import com.gulfappdeveloper.project3.repositories.RemoteRepository

class EditKotUseCase(
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke(
        url: String, kot: Kot, callBack: suspend (Int, String) -> Unit
    ){
        remoteRepository.editKot(
            url = url, kot = kot,callBack = callBack
        )
    }
}