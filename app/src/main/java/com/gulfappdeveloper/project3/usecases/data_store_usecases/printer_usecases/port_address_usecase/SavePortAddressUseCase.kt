package com.gulfappdeveloper.project3.usecases.data_store_usecases.printer_usecases.port_address_usecase

import com.gulfappdeveloper.project3.repositories.DataStoreRepository

class SavePortAddressUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(portAddress:String){
        dataStoreRepository.savePortAddress(portAddress = portAddress)
    }
}