package com.gulfappdeveloper.project3.usecases.data_store_usecases.base_url_usecases

import com.gulfappdeveloper.project3.repositories.DataStoreRepository
import kotlinx.coroutines.flow.Flow

class ReadBaseUrlUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(): Flow<String> {
        return dataStoreRepository.readBaseUrl()
    }
}