package com.gulfappdeveloper.project3.usecases.remote_usecases.ip4

import com.gulfappdeveloper.project3.domain.remote.get.GetDataFromRemote
import com.gulfappdeveloper.project3.domain.remote.seeip.SeeIp
import com.gulfappdeveloper.project3.repositories.RemoteRepository
import kotlinx.coroutines.flow.Flow

class GetIp4AddressUseCase(
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke(url:String):Flow<GetDataFromRemote<SeeIp>> {
        return remoteRepository.getIp4Address(url = url)
    }
}