package com.gulfappdeveloper.project3.navigation.root

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gulfappdeveloper.project3.data.remote.HttpRoutes
import com.gulfappdeveloper.project3.domain.remote.get.GetDataFromRemote
import com.gulfappdeveloper.project3.domain.remote.get.dine_in.Section
import com.gulfappdeveloper.project3.domain.remote.get.dine_in.Table
import com.gulfappdeveloper.project3.domain.remote.get.product.Category
import com.gulfappdeveloper.project3.domain.remote.get.product.Product
import com.gulfappdeveloper.project3.domain.remote.post.Kot
import com.gulfappdeveloper.project3.domain.remote.post.KotItem
import com.gulfappdeveloper.project3.presentation.presentation_util.UiEvent
import com.gulfappdeveloper.project3.presentation.screens.dine_in_screen.components.util.DineInScreenEvent
import com.gulfappdeveloper.project3.presentation.screens.product_display_screen.util.ProductDisplayScreenEvent
import com.gulfappdeveloper.project3.presentation.screens.review_screen.util.ReviewScreenEvent
import com.gulfappdeveloper.project3.presentation.screens.splash_screen.util.SplashScreenEvent
import com.gulfappdeveloper.project3.usecases.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "RootViewModel"

@HiltViewModel
class RootViewModel @Inject constructor(
    private val useCase: UseCase
) : ViewModel() {

    private val _splashScreenEvent = Channel<SplashScreenEvent>()
    val splashScreenEvent = _splashScreenEvent.receiveAsFlow()

    private val _productDisplayEvent = Channel<ProductDisplayScreenEvent>()
    val productDisplayScreenEvent = _productDisplayEvent.receiveAsFlow()

    private val _reviewScreenEvent = Channel<ReviewScreenEvent>()
    val reviewScreenEvent = _reviewScreenEvent.receiveAsFlow()

    private val _dineInScreenEvent = Channel<DineInScreenEvent>()
    val dineInScreenEvent = _dineInScreenEvent.receiveAsFlow()


    // Operation count application
    var operationCount = mutableStateOf(0)
        private set

    // Base Url
    var baseUrl = mutableStateOf(HttpRoutes.BASE_URL)
        private set


    // Welcome message in splash screen
    var message = mutableStateOf("")
        private set


    // For product display
    var selectedCategory = mutableStateOf(0)
        private set

    val categoryList = mutableStateListOf<Category>()
    val productList = mutableStateListOf<Product>()


    // For Dine in
    var selectedSection = mutableStateOf(1)
        private set

    val sectionList = mutableStateListOf<Section>()
    val tableList = mutableStateListOf<Table>()


    // For KOT
    val kotItemList = mutableStateListOf<KotItem>()

    var itemsCountInKot = mutableStateOf(0)
        private set

    var kotNetAmount = mutableStateOf(0f)
        private set

    var kotNotes = mutableStateOf("")
        private set


    init {
        sendSplashScreenEvent(SplashScreenEvent(UiEvent.ShowProgressBar))
        saveOperationCount()
    }

    private fun saveOperationCount() {
        viewModelScope.launch {
            useCase.updateOperationCountUseCase()
            readOperationCount()
        }
    }

    private fun readOperationCount() {
        viewModelScope.launch {
            useCase.readOperationCountUseCase().collectLatest {
                // Log.d(TAG, "readOperationCount: $it")
                operationCount.value = it
                readBaseUrl()
            }
        }
    }

    private fun readBaseUrl() {
        viewModelScope.launch {
            useCase.readBaseUrlUseCase().collectLatest {
                // Log.i(TAG, "readBaseUrl: $it")
                baseUrl.value = it
                getWelcomeMessage()
                getCategoryList()
                getProductList(value = 0)
                getSectionList()
                getTableList(value = 1)
            }
        }
    }

    // Product display
    private fun getWelcomeMessage() {
        viewModelScope.launch {
            useCase.getWelcomeMessageUseCase(url = baseUrl.value + HttpRoutes.WELCOME_MESSAGE)
                .collectLatest { result ->
                    sendSplashScreenEvent(SplashScreenEvent(UiEvent.CloseProgressBar))
                    if (result is GetDataFromRemote.Success) {
                        // Log.w(TAG, "getWelcomeMessage: ${result.data}", )
                        message.value = result.data.message
                        navigateToNextScreenWithDelayForSplashScreen(route = RootNavScreens.HomeScreen.route)
                    }
                    if (result is GetDataFromRemote.Failed) {
                        Log.e(TAG, "getWelcomeMessage: ${result.error.code}")
                        when (result.error.code) {
                            in 300..399 -> {
                                sendSplashScreenEvent(
                                    SplashScreenEvent(
                                        UiEvent.ShowSnackBar(
                                            message = result.error.message
                                                ?: "There have some problem on application"
                                        )
                                    )
                                )
                            }
                            in 400..600 -> {
                                sendSplashScreenEvent(SplashScreenEvent(UiEvent.ShowButton1))
                                sendSplashScreenEvent(
                                    SplashScreenEvent(
                                        UiEvent.ShowSnackBar(
                                            message = "${result.error.message}, Server down. Please change Base URL"
                                        )
                                    )
                                )

                            }
                            in 601..605 -> {
                                sendSplashScreenEvent(SplashScreenEvent(UiEvent.ShowButton1))
                                sendSplashScreenEvent(
                                    SplashScreenEvent(
                                        UiEvent.ShowSnackBar(
                                            message = result.error.message
                                                ?: "There have some problem on application"
                                        )
                                    )
                                )
                            }
                            else -> {
                                sendSplashScreenEvent(
                                    SplashScreenEvent(
                                        UiEvent.ShowSnackBar(
                                            message = result.error.message
                                                ?: "There have some problem on application"
                                        )
                                    )
                                )
                            }

                        }
                    }
                }
        }
    }

    private fun getCategoryList() {
        viewModelScope.launch {
            useCase.getCategoryListUseCase(url = baseUrl.value + HttpRoutes.CATEGORY_LIST)
                .collectLatest { result ->
                    if (result is GetDataFromRemote.Success) {
                        categoryList.addAll(result.data)
                    }
                    if (result is GetDataFromRemote.Failed) {
                        Log.e(TAG, "getCategoryList: ${result.error.code}")
                    }

                }
        }
    }

    fun setSelectedCategory(value: Int) {
        selectedCategory.value = value
        getProductList(value = value)
    }

    private fun getProductList(value: Int) {

        try {
            productList.removeAll {
                true
            }
        } catch (e: Exception) {
            Log.e(TAG, "getProductList: ${e.message}")
        }


        sendProductDisplayEvent(ProductDisplayScreenEvent(UiEvent.ShowProgressBar))

        val url = baseUrl.value + HttpRoutes.PRODUCT_LIST + "${selectedCategory.value}"

        viewModelScope.launch(Dispatchers.IO) {
            useCase.getProductListUseCase(
                url = url
            ).collectLatest { result ->
                try {
                    productList.removeAll {
                        true
                    }
                    selectedCategory.value = value
                } catch (e: Exception) {
                    Log.e(TAG, "getProductList: ${e.message}")
                }


                sendProductDisplayEvent(ProductDisplayScreenEvent(UiEvent.CloseProgressBar))

                if (result is GetDataFromRemote.Success) {
                    productList.addAll(result.data)
                    if (result.data.isEmpty()) {
                        sendProductDisplayEvent(ProductDisplayScreenEvent(UiEvent.ShowEmptyList))
                    } else {
                        sendProductDisplayEvent(ProductDisplayScreenEvent(UiEvent.ShowList))
                    }
                }
                if (result is GetDataFromRemote.Failed) {
                    sendProductDisplayEvent(ProductDisplayScreenEvent(UiEvent.ShowEmptyList))
                    // Log.e(TAG, "getProductList: ${result.error.message} $url")
                }

            }
        }
    }


    // Get Dine in details
    private fun getSectionList() {
        viewModelScope.launch {
            useCase.getSectionListUseCase(
                url = baseUrl.value + HttpRoutes.SECTION_LIST
            ).collectLatest { result ->
                if (result is GetDataFromRemote.Success) {
                    Log.i(TAG, "getSectionList: ${result.data}")
                    sectionList.addAll(result.data)
                }
                if (result is GetDataFromRemote.Failed) {
                    Log.e(TAG, "getSectionList: ${result.error}")
                }

            }
        }
    }

    fun setSelectedSection(value: Int) {
        selectedSection.value = value
        getTableList(value = value)
    }

    private fun getTableList(value: Int) {
        try {
            tableList.removeAll {
                true
            }
        } catch (e: Exception) {
            Log.e(TAG, "getTableList: ${e.message}")
        }


        sendDineInScreenEvent(DineInScreenEvent(UiEvent.ShowProgressBar))

        val url = baseUrl.value + HttpRoutes.TABLE_LIST + "${selectedSection.value}"

        viewModelScope.launch(Dispatchers.IO) {
            useCase.getTableListUseCase(
                url = url
            ).collectLatest { result ->

                try {
                    tableList.removeAll {
                        true
                    }
                    selectedSection.value = value
                } catch (e: Exception) {
                    Log.e(TAG, "getTableList: ${e.message}")
                }


                sendDineInScreenEvent(DineInScreenEvent(UiEvent.CloseProgressBar))


                if (result is GetDataFromRemote.Success) {
                    tableList.addAll(result.data)
                    if (result.data.isEmpty()) {
                        sendDineInScreenEvent(DineInScreenEvent(UiEvent.ShowEmptyList))
                    } else {
                        sendDineInScreenEvent(DineInScreenEvent(UiEvent.ShowList))
                    }
                }
                if (result is GetDataFromRemote.Failed) {
                    sendDineInScreenEvent(DineInScreenEvent(UiEvent.ShowEmptyList))
                    Log.e(TAG, "getTableList: ${result.error.message} $url")
                }

            }
        }
    }


    // KOT
    fun addProductToKOT(count: Int, product: Product) {
        val kotItem = KotItem(
            barcode = product.barcode,
            netAmount = count * product.rate,
            quantity = count.toFloat(),
            rate = product.rate,
            productId = product.id,
            productName = product.name
        )
        kotItemList.add(kotItem)
        itemsCountInKot.value += 1
        kotNetAmount.value += count * product.rate

    }

    fun onIncrementAndDecrementKotItemClicked(count: Int, productId: Int) {
        kotItemList.map { kotItem ->
            if (kotItem.productId == productId) {
                kotNetAmount.value -= kotItem.netAmount
                kotItem.quantity = count.toFloat()
                kotItem.netAmount = count * kotItem.rate
                kotNetAmount.value += kotItem.netAmount
            }
        }
    }

    fun onDeleteItemFromKotItemClicked(kotItem: KotItem) {
        kotItemList.removeAll {
            if (it.productId == kotItem.productId) {
                kotNetAmount.value -= it.netAmount
                itemsCountInKot.value -= 1
                true
            } else {
                false
            }
        }
    }

    fun addKotNotes(value: String) {
        kotNotes.value = value
    }

    fun addNoteToKotItem(kotItem: KotItem, note: String) {
        kotItemList.map { item ->
            if (item.productId == kotItem.productId) {
                item.itemNote = note
            }
        }
    }


    fun resetKot() {
        kotItemList.removeAll {
            true
        }
        itemsCountInKot.value = 0
        kotNetAmount.value = 0f
        kotNotes.value = ""

        // Need to do reset dine in features
    }

    fun generateKot(deviceId: String) {
        sendReviewScreenEvent(ReviewScreenEvent(UiEvent.ShowProgressBar))
        val kot = Kot(
            fK_UserId = 1,
            kotDetails = kotItemList.toList(),
            notes = kotNotes.value,
            serialNo = 1,
            terminal = deviceId
        )
        viewModelScope.launch(Dispatchers.IO) {
            useCase.generateKotUseCase(
                url = baseUrl.value + HttpRoutes.GENERATE_KOT,
                kot = kot
            ) { statusCode, message ->
                sendReviewScreenEvent(ReviewScreenEvent(UiEvent.CloseProgressBar))
                if (statusCode in 200..299) {
                    sendReviewScreenEvent(ReviewScreenEvent(UiEvent.ShowAlertDialog))
                } else {
                    sendReviewScreenEvent(ReviewScreenEvent(UiEvent.ShowSnackBar(message = "There have some error :- $message")))
                }
            }

        }
    }


    // Miscellaneous
    fun showSnackBarInProductDisplayScreen(message: String) {
        sendProductDisplayEvent(ProductDisplayScreenEvent(UiEvent.ShowSnackBar(message)))
    }

    private fun navigateToNextScreenWithDelayForSplashScreen(route: String) {
        viewModelScope.launch {
            delay(2000)
            sendSplashScreenEvent(SplashScreenEvent(UiEvent.Navigate(route = route)))
        }
    }

    private fun sendSplashScreenEvent(splashScreenEvent: SplashScreenEvent) {
        viewModelScope.launch {
            _splashScreenEvent.send(splashScreenEvent)
        }
    }

    private fun sendProductDisplayEvent(productDisplayScreenEvent: ProductDisplayScreenEvent) {
        viewModelScope.launch {
            _productDisplayEvent.send(productDisplayScreenEvent)
        }
    }

    private fun sendReviewScreenEvent(reviewScreenEvent: ReviewScreenEvent) {
        viewModelScope.launch {
            _reviewScreenEvent.send(reviewScreenEvent)
        }
    }

    private fun sendDineInScreenEvent(dineInScreenEvent: DineInScreenEvent){
        viewModelScope.launch {
            _dineInScreenEvent.send(dineInScreenEvent)
        }
    }


}