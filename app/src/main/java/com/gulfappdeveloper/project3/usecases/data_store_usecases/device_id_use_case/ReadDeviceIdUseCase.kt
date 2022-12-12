package com.gulfappdeveloper.project3.usecases.data_store_usecases.device_id_use_case

import com.gulfappdeveloper.project3.repositories.DataStoreRepository
import kotlinx.coroutines.flow.Flow

class ReadDeviceIdUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(): Flow<String>{
        return dataStoreRepository.readDeviceId()
    }
}