package com.gulfappdeveloper.project3.repositories

import com.gulfappdeveloper.project3.domain.remote.get.GetDataFromRemote
import com.gulfappdeveloper.project3.domain.remote.get.TableOrder
import com.gulfappdeveloper.project3.domain.remote.get.dine_in.Section
import com.gulfappdeveloper.project3.domain.remote.get.dine_in.Table
import com.gulfappdeveloper.project3.domain.remote.get.login.User
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

    suspend fun registerUser(url: String): Flow<GetDataFromRemote<User>> {
        return apiService.registerUser(url = url)
    }

    suspend fun getCategory(url: String): Flow<GetDataFromRemote<List<Category>>> {
        return apiService.getCategory(url = url)
    }

    suspend fun getProducts(url: String): Flow<GetDataFromRemote<List<Product>>> {
        return apiService.getProducts(url = url)
    }

    suspend fun productSearch(url: String): Flow<GetDataFromRemote<List<Product>>> {
        return apiService.productSearch(url = url)
    }

    suspend fun getSectionList(url: String): Flow<GetDataFromRemote<List<Section>>> {
        return apiService.getSectionList(url = url)
    }

    suspend fun getTableList(url: String): Flow<GetDataFromRemote<List<Table>>> {
        return apiService.getTableList(url = url)
    }

    suspend fun getTableOrders(url: String): Flow<GetDataFromRemote<List<TableOrder>>> {
        return apiService.getTableOrders(url = url)
    }


    // Post kot
    suspend fun generateKOT(url: String, kot: Kot, callBack: suspend (Int, String) -> Unit) {
        apiService.generateKOT(url = url, kot = kot, callBack = callBack)
    }

    // Get kot
    suspend fun getKOTDetails(
        url: String
    ): Flow<GetDataFromRemote<Kot?>> {
      return  apiService.getKOTDetails(url = url)
    }

    // Delete kot
    suspend fun deleteKOT(
        url: String,
        callBack: suspend (Int, String) -> Unit
    ) {
        apiService.deleteKOT(url = url,callBack = callBack)
    }




}