package com.gulfappdeveloper.project3.repositories

import android.util.Log
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
import com.gulfappdeveloper.project3.domain.services.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "RemoteRepository"
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

    suspend fun getKotCancelPrivilege(url: String): Flow<GetDataFromRemote<KotCancelPrivilege>> {
        return apiService.getKotCancelPrivilege(url = url)
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

    suspend fun getMultiSizeProducts(url: String): Flow<GetDataFromRemote<List<MultiSizeProduct>>> {
        return apiService.getMultiSizeProduct(url = url)
    }

    suspend fun getSectionList(url: String): Flow<GetDataFromRemote<List<Section>>> {
        Log.d(TAG, "getSectionList: $url")
       /* apiService.getSectionList(url = url).collectLatest {
            if(it is GetDataFromRemote.Success){
                Log.e(TAG, "getSectionList: success", )
            }
            if(it is GetDataFromRemote.Failed){
                Log.i(TAG, "getSectionList: failed")
            }
        }*/
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

    // Put Kot
    suspend fun editKot(url: String, kot: Kot, callBack: suspend (Int, String) -> Unit) {
        apiService.editKOTDetails(url = url, kot = kot, callBack = callBack)
    }

    suspend fun editKOTBasics(
        url: String,
        editKOTBasic: EditKOTBasic,
        callBack: suspend (Int, String) -> Unit
    ) {
        apiService.editKOTBasics(url = url, editKOTBasic = editKOTBasic, callBack = callBack)
    }

    suspend fun getListOfPendingKOTs(url: String): Flow<GetDataFromRemote<List<UserOrder>>> {
        return apiService.getListOfPendingKOTs(url = url)
    }

    // Get kot
    suspend fun getKOTDetails(
        url: String
    ): Flow<GetDataFromRemote<Kot?>> {
        return apiService.getKOTDetails(url = url)
    }

    // Delete kot
    suspend fun deleteKOT(
        url: String,
        callBack: suspend (Int, String) -> Unit
    ) {
        apiService.deleteKOT(url = url, callBack = callBack)
    }

    suspend fun uniLicenseActivation(
        rioLabKey: String,
        url: String,
        licenseRequestBody: LicenseRequestBody
    ): Flow<GetDataFromRemote<LicenseResponse>> {
        return apiService.uniLicenseActivation(
            rioLabKey = rioLabKey,
            url = url,
            licenseRequestBody = licenseRequestBody
        )
    }


    suspend fun getIp4Address(url: String): Flow<GetDataFromRemote<String>> {
        return apiService.getIp4Address(url = url)
    }


}