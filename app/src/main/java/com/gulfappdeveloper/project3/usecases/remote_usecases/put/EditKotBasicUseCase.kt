package com.gulfappdeveloper.project3.usecases.remote_usecases.put

import com.gulfappdeveloper.project3.domain.remote.put.EditKOTBasic
import com.gulfappdeveloper.project3.repositories.RemoteRepository

class EditKotBasicUseCase(
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke(
        url: String, editKOTBasic: EditKOTBasic, callBack: suspend (Int, String) -> Unit
    ) {
        remoteRepository.editKOTBasics(
            url = url, editKOTBasic = editKOTBasic, callBack = callBack
        )
    }
}