package com.gulfappdeveloper.project3.di

import com.gulfappdeveloper.project3.repositories.DataStoreRepository
import com.gulfappdeveloper.project3.repositories.FirebaseRepository
import com.gulfappdeveloper.project3.repositories.RemoteRepository
import com.gulfappdeveloper.project3.usecases.UseCase
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
import com.gulfappdeveloper.project3.usecases.data_store_usecases.uni_license_save_use_cases.UniLicenseReadUseCase
import com.gulfappdeveloper.project3.usecases.data_store_usecases.uni_license_save_use_cases.UniLicenseSaveUseCase
import com.gulfappdeveloper.project3.usecases.firebase_usecases.InsertErrorDataToFireStoreUseCase
import com.gulfappdeveloper.project3.usecases.firebase_usecases.InsertGeneralDataToFirebaseUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.delete.DeleteKotUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.GetWelcomeMessageUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.dine_in.GetSectionListUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.dine_in.GetTableListUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.dine_in.GetTableOrderListUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.kot.GetKOTDetailsUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.kot_cancel_privilege.KotCancelPrivilegeUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.kot_pending_list.GetListOfPendingKOTs
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.login.RegisterUserUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.product.GetCategoryListUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.product.GetMultiSizeProduct
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.product.GetProductListUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.get.product.ProductSearchUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.license.uni_license_activation_use_case.UniLicenseActivationUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.post.GenerateKotUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.put.EditKotBasicUseCase
import com.gulfappdeveloper.project3.usecases.remote_usecases.put.EditKotUseCase
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
        firebaseRepository: FirebaseRepository
    ): UseCase {
        return UseCase(

            updateOperationCountUseCase = UpdateOperationCountUseCase(dataStoreRepository = dataStoreRepository),
            saveBaseUrlUseCase = SaveBaseUrlUseCase(dataStoreRepository = dataStoreRepository),
            updateSerialNoUseCase = UpdateSerialNoUseCase(dataStoreRepository = dataStoreRepository),
            saveIpAddressUseCase = SaveIpAddressUseCase(dataStoreRepository = dataStoreRepository),
            savePortAddressUseCase = SavePortAddressUseCase(dataStoreRepository = dataStoreRepository),
            uniLicenseSaveUseCase = UniLicenseSaveUseCase(dataStoreRepository = dataStoreRepository),

            readOperationCountUseCase = ReadOperationCountUseCase(dataStoreRepository = dataStoreRepository),
            readBaseUrlUseCase = ReadBaseUrlUseCase(dataStoreRepository = dataStoreRepository),
            readSerialNoCountUseCase = ReadSerialNoCountUseCase(dataStoreRepository = dataStoreRepository),
            readIpAddressUseCase = ReadIpAddressUseCase(dataStoreRepository = dataStoreRepository),
            readPortAddressUseCase = ReadPortAddressUseCase(dataStoreRepository = dataStoreRepository),
            uniLicenseReadUseCase = UniLicenseReadUseCase(dataStoreRepository = dataStoreRepository),

            getWelcomeMessageUseCase = GetWelcomeMessageUseCase(remoteRepository = remoteRepository),

            registerUserUseCase = RegisterUserUseCase(remoteRepository = remoteRepository),

            kotCancelPrivilegeUseCase = KotCancelPrivilegeUseCase(remoteRepository = remoteRepository),

            getCategoryListUseCase = GetCategoryListUseCase(remoteRepository = remoteRepository),
            getProductListUseCase = GetProductListUseCase(remoteRepository = remoteRepository),
            productSearchUseCase = ProductSearchUseCase(remoteRepository = remoteRepository),
            getMultiSizeProduct = GetMultiSizeProduct(remoteRepository = remoteRepository),

            getSectionListUseCase = GetSectionListUseCase(remoteRepository = remoteRepository),
            getTableListUseCase = GetTableListUseCase(remoteRepository = remoteRepository),
            getTableOrderListUseCase = GetTableOrderListUseCase(remoteRepository = remoteRepository),

            generateKotUseCase = GenerateKotUseCase(remoteRepository = remoteRepository),
            editKotUseCase = EditKotUseCase(remoteRepository = remoteRepository),
            editKotBasicUseCase = EditKotBasicUseCase(remoteRepository = remoteRepository),
            getListOfPendingKOTs = GetListOfPendingKOTs(remoteRepository = remoteRepository),
            getKOTDetailsUseCase = GetKOTDetailsUseCase(remoteRepository = remoteRepository),
            deleteKotUseCase = DeleteKotUseCase(remoteRepository = remoteRepository),


            insertErrorDataToFireStoreUseCase = InsertErrorDataToFireStoreUseCase(firebaseRepository = firebaseRepository),
            insertGeneralDataToFirebaseUseCase = InsertGeneralDataToFirebaseUseCase(
                firebaseRepository = firebaseRepository
            ),

            uniLicenseActivationUseCase = UniLicenseActivationUseCase(remoteRepository = remoteRepository)
        )
    }
}