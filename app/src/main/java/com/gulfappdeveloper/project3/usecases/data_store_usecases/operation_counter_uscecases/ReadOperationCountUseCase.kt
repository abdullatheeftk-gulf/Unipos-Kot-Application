package com.gulfappdeveloper.project3.usecases.data_store_usecases.operation_counter_uscecases

import com.gulfappdeveloper.project3.repositories.DataStoreRepository
import kotlinx.coroutines.flow.Flow

class ReadOperationCountUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(): Flow<Int> {
        return dataStoreRepository.readOperationCount()
    }
}