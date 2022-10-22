package com.gulfappdeveloper.project3.domain.services

import com.gulfappdeveloper.project3.domain.remote.get.GetDataFromRemote
import com.gulfappdeveloper.project3.domain.remote.get.dine_in.Section
import com.gulfappdeveloper.project3.domain.remote.get.dine_in.Table
import com.gulfappdeveloper.project3.domain.remote.get.login.User
import com.gulfappdeveloper.project3.domain.remote.get.product.Category
import com.gulfappdeveloper.project3.domain.remote.get.product.Product
import com.gulfappdeveloper.project3.domain.remote.get.welcome.WelcomeMessage
import com.gulfappdeveloper.project3.domain.remote.post.Kot
import kotlinx.coroutines.flow.Flow

interface ApiService {

    // Welcome message
    suspend fun getWelcomeMessage(url: String): Flow<GetDataFromRemote<WelcomeMessage>>

    // register user with password
    suspend fun registerUser(url:String):Flow<GetDataFromRemote<User>>

    // Product
    suspend fun getCategory(url: String):Flow<GetDataFromRemote<List<Category>>>
    suspend fun getProducts(url:String):Flow<GetDataFromRemote<List<Product>>>

    // Dine in
    suspend fun getSectionList(url:String):Flow<GetDataFromRemote<List<Section>>>
    suspend fun getTableList(url: String):Flow<GetDataFromRemote<List<Table>>>

    // Post
    suspend fun generateKOT(url: String,kot: Kot, callBack:suspend (Int,String)->Unit)
}