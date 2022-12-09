package com.gulfappdeveloper.project3.usecases.data_store_usecases.operation_counter_uscecases

import com.gulfappdeveloper.project3.repositories.DataStoreRepository


class UpdateOperationCountUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke() {
        dataStoreRepository.updateOperationCount()
    }
}