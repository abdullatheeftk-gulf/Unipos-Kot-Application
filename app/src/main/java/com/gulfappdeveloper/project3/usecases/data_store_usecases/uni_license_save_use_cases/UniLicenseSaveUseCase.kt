package com.gulfappdeveloper.project3.usecases.data_store_usecases.uni_license_save_use_cases

import com.gulfappdeveloper.project3.repositories.DataStoreRepository

class UniLicenseSaveUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(uniLicenseString: String) {
        dataStoreRepository.saveUniLicenseKey(uniLicenseString = uniLicenseString)
    }
}