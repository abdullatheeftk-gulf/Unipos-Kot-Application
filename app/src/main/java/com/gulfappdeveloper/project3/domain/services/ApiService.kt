package com.gulfappdeveloper.project3.domain.services

import com.gulfappdeveloper.project3.domain.remote.get.GetDataFromRemote
import com.gulfappdeveloper.project3.domain.remote.get.dine_in.Section
import com.gulfappdeveloper.project3.domain.remote.get.dine_in.Table
import com.gulfappdeveloper.project3.domain.remote.get.dine_in.TableOrder
import com.gulfappdeveloper.project3.domain.remote.get.kot_cancel_privilege_checker.KotCancelPrivilege
import com.gulfappdeveloper.project3.domain.remote.get.kot_list.UserOrder
import com.gulfappdeveloper.project3.domain.remote.get.login.User
import com.gulfappdeveloper.project3.domain.remote.get.product.Category
import com.gulfappdeveloper.project3.domain.remote.get.product.MultiSizeProduct
import com.gulfappdeveloper.project3.domain.remote.get.product.Product
import com.gulfappdeveloper.project3.domain.remote.get.welcome.WelcomeMessage
import com.gulfappdeveloper.project3.domain.remote.license.LicenseRequestBody
import com.gulfappdeveloper.project3.domain.remote.license.LicenseResponse
import com.gulfappdeveloper.project3.domain.remote.post.Kot
import com.gulfappdeveloper.project3.domain.remote.put.EditKOTBasic
import com.gulfappdeveloper.project3.domain.remote.seeip.SeeIp
import kotlinx.coroutines.flow.Flow

interface ApiService {

    // Welcome message
    suspend fun getWelcomeMessage(url: String): Flow<GetDataFromRemote<WelcomeMessage>>

    // register user with password
    suspend fun registerUser(url: String): Flow<GetDataFromRemote<User>>

    // To get kot cancel privilege
    suspend fun getKotCancelPrivilege(url: String): Flow<GetDataFromRemote<KotCancelPrivilege>>

    // Product
    suspend fun getCategory(url: String): Flow<GetDataFromRemote<List<Category>>>
    suspend fun getProducts(url: String): Flow<GetDataFromRemote<List<Product>>>
    suspend fun productSearch(url: String): Flow<GetDataFromRemote<List<Product>>>
    suspend fun getMultiSizeProduct(url: String): Flow<GetDataFromRemote<List<MultiSizeProduct>>>

    // Dine in
    suspend fun getSectionList(url: String): Flow<GetDataFromRemote<List<Section>>>
    suspend fun getTableList(url: String): Flow<GetDataFromRemote<List<Table>>>
    suspend fun getTableOrders(url: String): Flow<GetDataFromRemote<List<TableOrder>>>

    // Post
    suspend fun generateKOT(url: String, kot: Kot, callBack: suspend (Int, String) -> Unit)

    // Get Kot for editing
    suspend fun getKOTDetails(url: String): Flow<GetDataFromRemote<Kot?>>

    // Put edited kot
    suspend fun editKOTDetails(url: String, kot: Kot, callBack: suspend (Int, String) -> Unit)
    suspend fun editKOTBasics(
        url: String,
        editKOTBasic: EditKOTBasic,
        callBack: suspend (Int, String) -> Unit
    )

    suspend fun getListOfPendingKOTs(url: String): Flow<GetDataFromRemote<List<UserOrder>>>

    // Delete kot
    suspend fun deleteKOT(url: String, callBack: suspend (Int, String) -> Unit)


    // Unipos license
    suspend fun uniLicenseActivation(
        rioLabKey: String,
        url: String,
        licenseRequestBody: LicenseRequestBody
    ): Flow<GetDataFromRemote<LicenseResponse>>

    // get ip address
    suspend fun getIp4Address(url: String):Flow<GetDataFromRemote<SeeIp>>

}