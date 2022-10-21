package com.gulfappdeveloper.project3.usecases.data_store_usecases.base_url_usecases

import com.gulfappdeveloper.project3.repositories.DataStoreRepository

class SaveBaseUrlUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(baseUrl:String){
        dataStoreRepository.saveBaseUrl(baseUrl = baseUrl)
    }
}