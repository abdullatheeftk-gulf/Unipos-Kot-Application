package com.gulfappdeveloper.project3.usecases.data_store_usecases.device_id_use_case

import com.gulfappdeveloper.project3.repositories.DataStoreRepository

class SaveDeviceIdUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(deviceId:String){
        dataStoreRepository.saveDeviceId(deviceId = deviceId)
    }
}