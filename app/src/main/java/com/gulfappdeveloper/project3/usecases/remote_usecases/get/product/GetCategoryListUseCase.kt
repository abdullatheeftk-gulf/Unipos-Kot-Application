package com.gulfappdeveloper.project3.usecases.remote_usecases.get.product

import com.gulfappdeveloper.project3.domain.remote.get.GetDataFromRemote
import com.gulfappdeveloper.project3.domain.remote.get.product.Category
import com.gulfappdeveloper.project3.repositories.RemoteRepository
import kotlinx.coroutines.flow.Flow


class GetCategoryListUseCase(
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke(url: String): Flow<GetDataFromRemote<List<Category>>> {
        return remoteRepository.getCategory(url = url)
    }
}