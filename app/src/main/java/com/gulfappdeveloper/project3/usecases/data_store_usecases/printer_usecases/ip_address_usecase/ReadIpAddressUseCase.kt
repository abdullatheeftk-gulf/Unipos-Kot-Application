package com.gulfappdeveloper.project3.usecases.data_store_usecases.printer_usecases.ip_address_usecase

import com.gulfappdeveloper.project3.repositories.DataStoreRepository
import kotlinx.coroutines.flow.Flow

class ReadIpAddressUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke():Flow<String>{
        return dataStoreRepository.readIpAddress()
    }
}