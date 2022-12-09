package com.gulfappdeveloper.project3.usecases.remote_usecases.get.dine_in

import com.gulfappdeveloper.project3.domain.remote.get.GetDataFromRemote
import com.gulfappdeveloper.project3.domain.remote.get.dine_in.TableOrder
import com.gulfappdeveloper.project3.repositories.RemoteRepository
import kotlinx.coroutines.flow.Flow

class GetTableOrderListUseCase(
    private val remoteRepository: RemoteRepository
) {

    suspend operator fun invoke(url: String): Flow<GetDataFromRemote<List<TableOrder>>> {
        return remoteRepository.getTableOrders(url = url)
    }
}