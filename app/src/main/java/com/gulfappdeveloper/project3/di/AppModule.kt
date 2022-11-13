package com.gulfappdeveloper.project3.di

import android.content.Context
import com.gulfappdeveloper.project3.data.data_store.DataStoreServiceImpl
import com.gulfappdeveloper.project3.domain.services.DataStoreService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataSoreService(@ApplicationContext context: Context): DataStoreService {
        return DataStoreServiceImpl(context = context)
    }


}