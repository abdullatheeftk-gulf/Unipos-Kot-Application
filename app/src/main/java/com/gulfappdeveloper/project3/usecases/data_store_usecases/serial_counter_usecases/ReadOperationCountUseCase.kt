package com.gulfappdeveloper.project3.usecases.data_store_usecases.serial_counter_usecases

import com.gulfappdeveloper.project3.repositories.DataStoreRepository
import kotlinx.coroutines.flow.Flow

class ReadSerialNoCountUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(): Flow<Int> {
        return dataStoreRepository.readSerialNo()
    }
}