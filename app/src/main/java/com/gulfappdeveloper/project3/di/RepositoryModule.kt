package com.gulfappdeveloper.project3.di

import com.gulfappdeveloper.project3.repositories.DataStoreRepository
import com.gulfappdeveloper.project3.repositories.RemoteRepository
import com.gulfappdeveloper.project3.usecases.UseCase
import com.gulfappdeveloper.project3.usecases.data_store_usecases.base_url_usecases.ReadBaseUrlUseCase
import com.gulfappdeveloper.project3.usecases.data_store_usecases.base_url_usecases.SaveBaseUrlUseCase
import com.gulfappdeveloper.project3.usecases.data_store_usecases.operation_counter_uscecases.ReadOperationCountUseCase
import com.gulfappdeveloper.project3.usecases.data_store_usecases.operation_counter_uscecases.UpdateOperationCountUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.GetCategoryListUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.GetProductListUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.GetWelcomeMessageUseCase
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


            readOperationCountUseCase = ReadOperationCountUseCase(dataStoreRepository = dataStoreRepository),
            readBaseUrlUseCase = ReadBaseUrlUseCase(dataStoreRepository = dataStoreRepository),

            getWelcomeMessageUseCase = GetWelcomeMessageUseCase(remoteRepository = remoteRepository),
            getCategoryListUseCase = GetCategoryListUseCase(remoteRepository = remoteRepository),
            getProductListUseCase = GetProductListUseCase(remoteRepository = remoteRepository),

            generateKotUseCase = GenerateKotUseCase(remoteRepository = remoteRepository),

            )
    }
}