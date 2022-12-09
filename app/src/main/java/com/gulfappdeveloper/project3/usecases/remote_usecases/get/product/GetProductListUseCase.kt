package com.gulfappdeveloper.project3.usecases.remote_usecases.get.product

import com.gulfappdeveloper.project3.domain.remote.get.GetDataFromRemote
import com.gulfappdeveloper.project3.domain.remote.get.product.Product
import com.gulfappdeveloper.project3.repositories.RemoteRepository
import kotlinx.coroutines.flow.Flow

class GetProductListUseCase(
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke(url: String): Flow<GetDataFromRemote<List<Product>>> {
        return remoteRepository.getProducts(url = url)
    }
}