package com.gulfappdeveloper.project3.usecases.data_store_usecases.serial_counter_usecases

import com.gulfappdeveloper.project3.repositories.DataStoreRepository


class UpdateSerialNoUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke() {
        dataStoreRepository.updateSerialNo()
    }
}