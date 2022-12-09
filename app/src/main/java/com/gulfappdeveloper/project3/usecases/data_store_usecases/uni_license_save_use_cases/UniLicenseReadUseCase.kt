package com.gulfappdeveloper.project3.usecases.data_store_usecases.uni_license_save_use_cases

import com.gulfappdeveloper.project3.repositories.DataStoreRepository
import kotlinx.coroutines.flow.Flow

class UniLicenseReadUseCase(
    private val dataStoreRepository: DataStoreRepository
) {

    operator fun invoke(): Flow<String> {
        return dataStoreRepository.readUniLicenseKey()
    }
}