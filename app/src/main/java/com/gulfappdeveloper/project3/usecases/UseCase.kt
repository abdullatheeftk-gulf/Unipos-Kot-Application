package com.gulfappdeveloper.project3.usecases

import com.gulfappdeveloper.project3.usecases.data_store_usecases.base_url_usecases.ReadBaseUrlUseCase
import com.gulfappdeveloper.project3.usecases.data_store_usecases.base_url_usecases.SaveBaseUrlUseCase
import com.gulfappdeveloper.project3.usecases.data_store_usecases.operation_counter_uscecases.ReadOperationCountUseCase
import com.gulfappdeveloper.project3.usecases.data_store_usecases.operation_counter_uscecases.UpdateOperationCountUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.product.GetCategoryListUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.product.GetProductListUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.GetWelcomeMessageUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.dine_in.GetSectionListUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.dine_in.GetTableListUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.login.RegisterUserUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.post.GenerateKotUseCase

data class UseCase(
    val updateOperationCountUseCase: UpdateOperationCountUseCase,
    val saveBaseUrlUseCase: SaveBaseUrlUseCase,

    val readOperationCountUseCase: ReadOperationCountUseCase,
    val readBaseUrlUseCase: ReadBaseUrlUseCase,


    val getWelcomeMessageUseCase: GetWelcomeMessageUseCase,

    val registerUserUseCase: RegisterUserUseCase,

    val getCategoryListUseCase: GetCategoryListUseCase,
    val getProductListUseCase: GetProductListUseCase,

    val getSectionListUseCase:GetSectionListUseCase,
    val getTableListUseCase: GetTableListUseCase,

    val generateKotUseCase:GenerateKotUseCase,



    )
