package com.gulfappdeveloper.project3.usecases.remote_usecases.get.dine_in

import com.gulfappdeveloper.project3.domain.remote.get.GetDataFromRemote
import com.gulfappdeveloper.project3.domain.remote.get.dine_in.Section
import com.gulfappdeveloper.project3.repositories.RemoteRepository
import kotlinx.coroutines.flow.Flow

class GetSectionListUseCase(
    private val remoteRepository: RemoteRepository
) {

    suspend operator fun invoke(url: String): Flow<GetDataFromRemote<List<Section>>> {
        return remoteRepository.getSectionList(url = url)
    }
}