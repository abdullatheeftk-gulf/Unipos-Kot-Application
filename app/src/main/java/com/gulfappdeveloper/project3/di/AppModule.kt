package com.gulfappdeveloper.project3.di

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.gulfappdeveloper.project3.data.data_store.DataStoreServiceImpl
import com.gulfappdeveloper.project3.data.firebase.FirebaseServiceImpl
import com.gulfappdeveloper.project3.domain.services.DataStoreService
import com.gulfappdeveloper.project3.domain.services.FirebaseService
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


    @Provides
    @Singleton
    fun provideFirebaseFireStoreDb() = FirebaseFirestore.getInstance()



    @Provides
    @Singleton
    fun provideFirebaseService(fdb: FirebaseFirestore): FirebaseService {
        return  FirebaseServiceImpl(fdb)
    }


}