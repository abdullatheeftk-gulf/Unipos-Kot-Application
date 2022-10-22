package com.gulfappdeveloper.project3.domain.services

import com.gulfappdeveloper.project3.domain.remote.get.GetDataFromRemote
import com.gulfappdeveloper.project3.domain.remote.get.product.Category
import com.gulfappdeveloper.project3.domain.remote.get.product.Product
import com.gulfappdeveloper.project3.domain.remote.get.welcome.WelcomeMessage
import com.gulfappdeveloper.project3.domain.remote.post.Kot
import kotlinx.coroutines.flow.Flow

interface ApiService {

    // Get
    suspend fun getWelcomeMessage(url: String): Flow<GetDataFromRemote<WelcomeMessage>>
    suspend fun getCategory(url: String):Flow<GetDataFromRemote<List<Category>>>
    suspend fun getProducts(url:String):Flow<GetDataFromRemote<List<Product>>>

    // Post
    suspend fun generateKOT(url: String,kot: Kot, callBack:suspend (Int,String)->Unit)
}