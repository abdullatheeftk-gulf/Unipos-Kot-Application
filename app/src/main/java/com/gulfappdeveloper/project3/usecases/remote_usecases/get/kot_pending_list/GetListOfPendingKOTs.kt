package com.gulfappdeveloper.project3.usecases.remote_usecases.get.kot_pending_list

import com.gulfappdeveloper.project3.domain.remote.get.GetDataFromRemote
import com.gulfappdeveloper.project3.domain.remote.get.kot_list.UserOrder
import com.gulfappdeveloper.project3.repositories.RemoteRepository
import kotlinx.coroutines.flow.Flow

class GetListOfPendingKOTs(
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke(url: String): Flow<GetDataFromRemote<List<UserOrder>>> {
        return remoteRepository.getListOfPendingKOTs(url = url)
    }
}