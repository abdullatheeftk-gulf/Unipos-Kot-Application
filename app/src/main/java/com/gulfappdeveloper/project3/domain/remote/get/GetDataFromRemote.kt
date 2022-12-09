package com.gulfappdeveloper.project3.domain.remote.get

import com.gulfappdeveloper.project3.domain.remote.error.Error

sealed class GetDataFromRemote<out T> {
    data class Success<T>(val data: T) : GetDataFromRemote<T>()
    data class Failed(val error: Error) : GetDataFromRemote<Nothing>()
}
