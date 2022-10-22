package com.gulfappdeveloper.project3.usecases

import com.gulfappdeveloper.project3.usecases.data_store_usecases.base_url_usecases.ReadBaseUrlUseCase
import com.gulfappdeveloper.project3.usecases.data_store_usecases.base_url_usecases.SaveBaseUrlUseCase
import com.gulfappdeveloper.project3.usecases.data_store_usecases.operation_counter_uscecases.ReadOperationCountUseCase
import com.gulfappdeveloper.project3.usecases.data_store_usecases.operation_counter_uscecases.UpdateOperationCountUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.GetCategoryListUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.GetProductListUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.GetWelcomeMessageUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.post.GenerateKotUseCase

data class UseCase(
    val updateOperationCountUseCase: UpdateOperationCountUseCase,
    val saveBaseUrlUseCase: SaveBaseUrlUseCase,

    val readOperationCountUseCase: ReadOperationCountUseCase,
    val readBaseUrlUseCase: ReadBaseUrlUseCase,


    val getWelcomeMessageUseCase: GetWelcomeMessageUseCase,
    val getCategoryListUseCase: GetCategoryListUseCase,
    val getProductListUseCase: GetProductListUseCase,

    val generateKotUseCase:GenerateKotUseCase,



    )
