package com.gulfappdeveloper.project3.usecases.remote_usecases.get.kot

import com.gulfappdeveloper.project3.domain.remote.get.GetDataFromRemote
import com.gulfappdeveloper.project3.domain.remote.post.Kot
import com.gulfappdeveloper.project3.repositories.RemoteRepository
import kotlinx.coroutines.flow.Flow

class GetKOTDetailsUseCase(
    private val remoteRepository: RemoteRepository
) {

    suspend operator fun invoke(
        url: String,

    ): Flow<GetDataFromRemote<Kot?>> {
        return remoteRepository.getKOTDetails(url = url)
    }
}