package com.gulfappdeveloper.project3.usecases

import com.gulfappdeveloper.project3.usecases.data_store_usecases.base_url_usecases.ReadBaseUrlUseCase
import com.gulfappdeveloper.project3.usecases.data_store_usecases.base_url_usecases.SaveBaseUrlUseCase
import com.gulfappdeveloper.project3.usecases.data_store_usecases.operation_counter_uscecases.ReadOperationCountUseCase
import com.gulfappdeveloper.project3.usecases.data_store_usecases.operation_counter_uscecases.UpdateOperationCountUseCase
import com.gulfappdeveloper.project3.usecases.data_store_usecases.printer_usecases.ip_address_usecase.ReadIpAddressUseCase
import com.gulfappdeveloper.project3.usecases.data_store_usecases.printer_usecases.ip_address_usecase.SaveIpAddressUseCase
import com.gulfappdeveloper.project3.usecases.data_store_usecases.printer_usecases.port_address_usecase.ReadPortAddressUseCase
import com.gulfappdeveloper.project3.usecases.data_store_usecases.printer_usecases.port_address_usecase.SavePortAddressUseCase
import com.gulfappdeveloper.project3.usecases.data_store_usecases.serial_counter_usecases.ReadSerialNoCountUseCase
import com.gulfappdeveloper.project3.usecases.data_store_usecases.serial_counter_usecases.UpdateSerialNoUseCase
import com.gulfappdeveloper.project3.usecases.firebase_usecases.InsertErrorDataToFireStoreUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.delete.DeleteKotUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.product.GetCategoryListUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.product.GetProductListUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.GetWelcomeMessageUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.dine_in.GetSectionListUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.dine_in.GetTableListUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.dine_in.GetTableOrderListUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.kot.GetKOTDetailsUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.kot_pending_list.GetListOfPendingKOTs
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.login.RegisterUserUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.product.GetMultiSizeProduct
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.product.ProductSearchUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.post.GenerateKotUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.put.EditKotBasicUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.put.EditKotUseCase

data class UseCase(
    val updateOperationCountUseCase: UpdateOperationCountUseCase,
    val saveBaseUrlUseCase: SaveBaseUrlUseCase,
    val updateSerialNoUseCase: UpdateSerialNoUseCase,
    val saveIpAddressUseCase: SaveIpAddressUseCase,
    val savePortAddressUseCase: SavePortAddressUseCase,

    val readOperationCountUseCase: ReadOperationCountUseCase,
    val readBaseUrlUseCase: ReadBaseUrlUseCase,
    val readSerialNoCountUseCase: ReadSerialNoCountUseCase,
    val readIpAddressUseCase: ReadIpAddressUseCase,
    val readPortAddressUseCase: ReadPortAddressUseCase,


    val getWelcomeMessageUseCase: GetWelcomeMessageUseCase,

    val registerUserUseCase: RegisterUserUseCase,

    val getCategoryListUseCase: GetCategoryListUseCase,
    val getProductListUseCase: GetProductListUseCase,
    val productSearchUseCase : ProductSearchUseCase,
    val getMultiSizeProduct: GetMultiSizeProduct,

    val getSectionListUseCase:GetSectionListUseCase,
    val getTableListUseCase: GetTableListUseCase,
    val getTableOrderListUseCase: GetTableOrderListUseCase,

    // Generate kot
    val generateKotUseCase:GenerateKotUseCase,
    // Edit Kot
    val editKotUseCase: EditKotUseCase,
    val editKotBasicUseCase: EditKotBasicUseCase,
    // get list of pending KOTs
    val getListOfPendingKOTs: GetListOfPendingKOTs,
    // Get KOT details for editing
    val getKOTDetailsUseCase: GetKOTDetailsUseCase,
    // Delete KOT
    val deleteKotUseCase: DeleteKotUseCase,


    // firebase
    val insertErrorDataToFireStoreUseCase: InsertErrorDataToFireStoreUseCase

)
