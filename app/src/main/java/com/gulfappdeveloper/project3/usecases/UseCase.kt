package com.gulfappdeveloper.project3.usecases

import com.gulfappdeveloper.project3.usecases.data_store_usecases.base_url_usecases.ReadBaseUrlUseCase
import com.gulfappdeveloper.project3.usecases.data_store_usecases.base_url_usecases.SaveBaseUrlUseCase
import com.gulfappdeveloper.project3.usecases.data_store_usecases.operation_counter_uscecases.ReadOperationCountUseCase
import com.gulfappdeveloper.project3.usecases.data_store_usecases.operation_counter_uscecases.UpdateOperationCountUseCase
import com.gulfappdeveloper.project3.usecases.data_store_usecases.serial_counter_usecases.ReadSerialNoCountUseCase
import com.gulfappdeveloper.project3.usecases.data_store_usecases.serial_counter_usecases.UpdateSerialNoUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.delete.DeleteKotUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.product.GetCategoryListUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.product.GetProductListUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.GetWelcomeMessageUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.dine_in.GetSectionListUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.dine_in.GetTableListUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.dine_in.GetTableOrderListUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.kot.GetKOTDetailsUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.login.RegisterUserUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.product.ProductSearchUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.post.GenerateKotUseCase

data class UseCase(
    val updateOperationCountUseCase: UpdateOperationCountUseCase,
    val saveBaseUrlUseCase: SaveBaseUrlUseCase,
    val updateSerialNoUseCase: UpdateSerialNoUseCase,

    val readOperationCountUseCase: ReadOperationCountUseCase,
    val readBaseUrlUseCase: ReadBaseUrlUseCase,
    val readSerialNoCountUseCase: ReadSerialNoCountUseCase,


    val getWelcomeMessageUseCase: GetWelcomeMessageUseCase,

    val registerUserUseCase: RegisterUserUseCase,

    val getCategoryListUseCase: GetCategoryListUseCase,
    val getProductListUseCase: GetProductListUseCase,
    val productSearchUseCase : ProductSearchUseCase,

    val getSectionListUseCase:GetSectionListUseCase,
    val getTableListUseCase: GetTableListUseCase,
    val getTableOrderListUseCase: GetTableOrderListUseCase,

    val generateKotUseCase:GenerateKotUseCase,
    // Get KOT details for editing
    val getKOTDetailsUseCase: GetKOTDetailsUseCase,
    // Delete KOT
    val deleteKotUseCase: DeleteKotUseCase


)
