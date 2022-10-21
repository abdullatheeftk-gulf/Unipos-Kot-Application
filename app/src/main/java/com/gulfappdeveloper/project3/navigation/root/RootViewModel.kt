package com.gulfappdeveloper.project3.navigation.root

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gulfappdeveloper.project3.data.remote.HttpRoutes
import com.gulfappdeveloper.project3.domain.remote.get.GetDataFromRemote
import com.gulfappdeveloper.project3.domain.remote.get.product.Category
import com.gulfappdeveloper.project3.domain.remote.get.product.Product
import com.gulfappdeveloper.project3.domain.remote.post.KotItem
import com.gulfappdeveloper.project3.presentation.presentation_util.UiEvent
import com.gulfappdeveloper.project3.presentation.screens.product_display_screen.util.ProductDisplayScreenEvent
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

    var operationCount = mutableStateOf(0)
        private set

    var baseUrl = mutableStateOf(HttpRoutes.BASE_URL)
        private set

    var message = mutableStateOf("")
        private set


    var selectedCategory = mutableStateOf(0)
        private set


    val categoryList = mutableStateListOf<Category>()
    val productList = mutableStateListOf<Product>()

    val kotItemList = mutableStateListOf<KotItem>()
    var itemsCountInKot = mutableStateOf(0)
        private set
    var kotNetAmount = mutableStateOf(0f)
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
            }
        }
    }

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
}