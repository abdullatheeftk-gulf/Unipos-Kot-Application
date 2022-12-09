package com.gulfappdeveloper.project3.usecases.remote_usecases.get.product

import com.gulfappdeveloper.project3.repositories.RemoteRepository

class GetMultiSizeProduct(
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke(url: String) = remoteRepository.getMultiSizeProducts(url = url)
}