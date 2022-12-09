package com.gulfappdeveloper.project3.usecases.remote_usecases.license.uni_license_activation_use_case

import com.gulfappdeveloper.project3.domain.remote.get.GetDataFromRemote
import com.gulfappdeveloper.project3.domain.remote.license.LicenseRequestBody
import com.gulfappdeveloper.project3.domain.remote.license.LicenseResponse
import com.gulfappdeveloper.project3.repositories.RemoteRepository
import kotlinx.coroutines.flow.Flow

class UniLicenseActivationUseCase(
    private val remoteRepository: RemoteRepository
) {

    suspend operator fun invoke(
        rioLabKey: String,
        url: String,
        licenseRequestBody: LicenseRequestBody
    ): Flow<GetDataFromRemote<LicenseResponse>> {
        return remoteRepository.uniLicenseActivation(
            rioLabKey = rioLabKey,
            url = url,
            licenseRequestBody = licenseRequestBody
        )
    }
}