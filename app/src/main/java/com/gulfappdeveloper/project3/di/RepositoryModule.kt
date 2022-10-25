package com.gulfappdeveloper.project3.di

import com.gulfappdeveloper.project3.repositories.DataStoreRepository
import com.gulfappdeveloper.project3.repositories.RemoteRepository
import com.gulfappdeveloper.project3.usecases.UseCase
import com.gulfappdeveloper.project3.usecases.data_store_usecases.base_url_usecases.ReadBaseUrlUseCase
import com.gulfappdeveloper.project3.usecases.data_store_usecases.base_url_usecases.SaveBaseUrlUseCase
import com.gulfappdeveloper.project3.usecases.data_store_usecases.operation_counter_uscecases.ReadOperationCountUseCase
import com.gulfappdeveloper.project3.usecases.data_store_usecases.operation_counter_uscecases.UpdateOperationCountUseCase
import com.gulfappdeveloper.project3.usecases.data_store_usecases.serial_counter_usecases.ReadSerialNoCountUseCase
import com.gulfappdeveloper.project3.usecases.data_store_usecases.serial_counter_usecases.UpdateSerialNoUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.GetWelcomeMessageUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.dine_in.GetSectionListUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.dine_in.GetTableListUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.login.RegisterUserUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.product.GetCategoryListUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.product.GetProductListUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.post.GenerateKotUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUseCase(
        remoteRepository: RemoteRepository,
        dataStoreRepository: DataStoreRepository,
    ): UseCase {
        return UseCase(

            updateOperationCountUseCase = UpdateOperationCountUseCase(dataStoreRepository = dataStoreRepository),
            saveBaseUrlUseCase = SaveBaseUrlUseCase(dataStoreRepository = dataStoreRepository),
            updateSerialNoUseCase = UpdateSerialNoUseCase(dataStoreRepository = dataStoreRepository),

            readOperationCountUseCase = ReadOperationCountUseCase(dataStoreRepository = dataStoreRepository),
            readBaseUrlUseCase = ReadBaseUrlUseCase(dataStoreRepository = dataStoreRepository),
            readSerialNoCountUseCase = ReadSerialNoCountUseCase(dataStoreRepository = dataStoreRepository),

            getWelcomeMessageUseCase = GetWelcomeMessageUseCase(remoteRepository = remoteRepository),

            registerUserUseCase = RegisterUserUseCase(remoteRepository = remoteRepository),

            getCategoryListUseCase = GetCategoryListUseCase(remoteRepository = remoteRepository),
            getProductListUseCase = GetProductListUseCase(remoteRepository = remoteRepository),

            getSectionListUseCase = GetSectionListUseCase(remoteRepository = remoteRepository),
            getTableListUseCase = GetTableListUseCase(remoteRepository = remoteRepository),

            generateKotUseCase = GenerateKotUseCase(remoteRepository = remoteRepository),

            )
    }
}