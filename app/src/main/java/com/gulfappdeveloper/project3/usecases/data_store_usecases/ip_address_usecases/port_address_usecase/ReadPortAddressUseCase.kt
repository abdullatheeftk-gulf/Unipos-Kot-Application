package com.gulfappdeveloper.project3.usecases.data_store_usecases.ip_address_usecases.port_address_usecase

import com.gulfappdeveloper.project3.repositories.DataStoreRepository
import kotlinx.coroutines.flow.Flow

class ReadPortAddressUseCase(
    private val dataStoreRepository: DataStoreRepository
) {

    operator fun invoke(): Flow<String> {
        return dataStoreRepository.readPortAddress()
    }
}