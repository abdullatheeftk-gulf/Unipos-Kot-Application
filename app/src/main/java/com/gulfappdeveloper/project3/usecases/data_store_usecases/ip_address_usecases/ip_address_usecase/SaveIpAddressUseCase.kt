package com.gulfappdeveloper.project3.usecases.data_store_usecases.ip_address_usecases.ip_address_usecase

import com.gulfappdeveloper.project3.repositories.DataStoreRepository

class SaveIpAddressUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(ipAddress: String) {
        dataStoreRepository.saveIpAddress(ipAddress = ipAddress)
    }
}