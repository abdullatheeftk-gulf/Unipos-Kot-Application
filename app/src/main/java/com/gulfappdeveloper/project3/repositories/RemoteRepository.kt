package com.gulfappdeveloper.project3.repositories

import com.gulfappdeveloper.project3.domain.remote.get.GetDataFromRemote
import com.gulfappdeveloper.project3.domain.remote.get.product.Category
import com.gulfappdeveloper.project3.domain.remote.get.product.Product
import com.gulfappdeveloper.project3.domain.remote.get.welcome.WelcomeMessage
import com.gulfappdeveloper.project3.domain.remote.post.Kot
import com.gulfappdeveloper.project3.domain.services.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RemoteRepository @Inject constructor(
    private val apiService: ApiService
) {

    // Get
    suspend fun getWelcomeMessage(url: String): Flow<GetDataFromRemote<WelcomeMessage>> {
        return apiService.getWelcomeMessage(url = url)
    }

    suspend fun getCategory(url: String): Flow<GetDataFromRemote<List<Category>>> {
        return apiService.getCategory(url = url)
    }

    suspend fun getProducts(url: String): Flow<GetDataFromRemote<List<Product>>> {
        return apiService.getProducts(url = url)
    }


    // Post
    suspend fun generateKOT(url: String, kot: Kot, callBack: suspend (Int, String) -> Unit) {
        apiService.generateKOT(url = url, kot = kot,callBack = callBack)
    }
}