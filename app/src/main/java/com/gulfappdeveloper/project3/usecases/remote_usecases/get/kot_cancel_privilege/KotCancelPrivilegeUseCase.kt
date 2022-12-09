package com.gulfappdeveloper.project3.usecases.remote_usecases.get.kot_cancel_privilege

import com.gulfappdeveloper.project3.domain.remote.get.GetDataFromRemote
import com.gulfappdeveloper.project3.repositories.RemoteRepository
import kotlinx.coroutines.flow.Flow

class KotCancelPrivilegeUseCase(
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke(url:String):Flow<GetDataFromRemote<com.gulfappdeveloper.project3.domain.remote.get.kot_cancel_privilege_checker.KotCancelPrivilege>>{
        return remoteRepository.getKotCancelPrivilege(url = url)
    }
}