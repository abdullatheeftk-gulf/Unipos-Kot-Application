package com.gulfappdeveloper.project3.navigation.root

import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dantsu.escposprinter.EscPosPrinter
import com.dantsu.escposprinter.textparser.PrinterTextParserImg
import com.gulfappdeveloper.project3.R
import com.gulfappdeveloper.project3.data.remote.HttpRoutes
import com.gulfappdeveloper.project3.domain.remote.get.GetDataFromRemote
import com.gulfappdeveloper.project3.domain.remote.get.TableOrder
import com.gulfappdeveloper.project3.domain.remote.get.dine_in.Section
import com.gulfappdeveloper.project3.domain.remote.get.dine_in.Table
import com.gulfappdeveloper.project3.domain.remote.get.product.Category
import com.gulfappdeveloper.project3.domain.remote.get.product.Product
import com.gulfappdeveloper.project3.domain.remote.post.Kot
import com.gulfappdeveloper.project3.domain.remote.post.KotItem
import com.gulfappdeveloper.project3.presentation.presentation_util.OrderMode
import com.gulfappdeveloper.project3.presentation.presentation_util.UiEvent
import com.gulfappdeveloper.project3.presentation.screens.dine_in_screen.components.util.DineInScreenEvent
import com.gulfappdeveloper.project3.presentation.screens.editing_screen.EditingScreenEvent
import com.gulfappdeveloper.project3.presentation.screens.product_display_screen.util.ProductDisplayScreenEvent
import com.gulfappdeveloper.project3.presentation.screens.review_screen.util.ReviewScreenEvent
import com.gulfappdeveloper.project3.presentation.screens.show_kot_screen.util.ShowKotScreenUiEvent
import com.gulfappdeveloper.project3.presentation.screens.splash_screen.util.SplashScreenEvent
import com.gulfappdeveloper.project3.presentation.screens.table_selection_screen.TableSelectionUiEvent
import com.gulfappdeveloper.project3.print.Printer
import com.gulfappdeveloper.project3.usecases.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

private const val TAG = "RootViewModel"

@HiltViewModel
open class RootViewModel @Inject constructor(
    private val useCase: UseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private var isInitialLoadingFinished = false

    private val _splashScreenEvent = Channel<SplashScreenEvent>()
    val splashScreenEvent = _splashScreenEvent.receiveAsFlow()

    private val _productDisplayEvent = Channel<ProductDisplayScreenEvent>()
    val productDisplayScreenEvent = _productDisplayEvent.receiveAsFlow()

    private val _reviewScreenEvent = Channel<ReviewScreenEvent>()
    val reviewScreenEvent = _reviewScreenEvent.receiveAsFlow()

    private val _dineInScreenEvent = Channel<DineInScreenEvent>()
    val dineInScreenEvent = _dineInScreenEvent.receiveAsFlow()

    private val _tableSelectionUiEvent = Channel<TableSelectionUiEvent>()
    val tableSelectionUiEvent = _tableSelectionUiEvent.receiveAsFlow()

    private val _localRegisterEvent = Channel<UiEvent>()
    val localRegisterEvent = _localRegisterEvent.receiveAsFlow()

    private val _editingScreenEvent = Channel<EditingScreenEvent>()
    val editingScreenEvent = _editingScreenEvent.receiveAsFlow()

    private val _showKotScreenEvent = Channel<ShowKotScreenUiEvent>()
    val showKotScreenUiEvent = _showKotScreenEvent.receiveAsFlow()


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

    var productSearchText = mutableStateOf("")
        private set


    // For Dine in
    var selectedSection = mutableStateOf(1)
        private set

    val sectionList = mutableStateListOf<Section>()
    val tableList = mutableStateListOf<Table>()

    var selectedTable: MutableState<Table?> = mutableStateOf(null)
        private set

    var newTableOrder: MutableState<TableOrder?> = mutableStateOf(null)
        private set

    val tableOrderList = mutableStateListOf<TableOrder>()

    var showNewTableOrderAddButton = mutableStateOf(true)
        private set


    // For KOT
    val kotItemList = mutableStateListOf<KotItem>()

    var itemsCountInKot = mutableStateOf(0)
        private set

    var kotNetAmount = mutableStateOf(0f)
        private set

    var kotNotes = mutableStateOf("")
        private set

    var fKUserId = mutableStateOf(0)
        private set

    var serialNo = mutableStateOf(0)
        private set

    var orderName = mutableStateOf("")
        private set

    var tableId = mutableStateOf(0)
        private set

    var chairCount = mutableStateOf(0)
        private set

    // For editing only
    var kotMasterId = mutableStateOf(0)
        private set

    var editMode = mutableStateOf(false)
        private set

    //Dine In and Take Away
    var selectedOrderMode = mutableStateOf(OrderMode.NONE)
        private set


    // Ip address and Port use case

    var ipAddress = mutableStateOf("")
        private set

    var port = mutableStateOf("")
        private set


    init {

        sendSplashScreenEvent(SplashScreenEvent(UiEvent.ShowProgressBar))
        saveOperationCount()
        readOperationCount()
        readSerialNo()
        readBaseUrl()
        readIpAddress()
        readPortAddress()

    }

    fun setIsInitialLoadingIsNotFinished() {

        try {
            categoryList.removeAll {
                true
            }
            productList.removeAll {
                true
            }
            sectionList.removeAll {
                true
            }
            tableList.removeAll {
                true
            }
            readBaseUrl()
            isInitialLoadingFinished = false

            // Log.d(TAG, "setIsInitialLoadingIsNotFinished: $categoryList")

        } catch (e: Exception) {
            Log.e(TAG, "setInitialLoadingIsFinished:${e.message} ")
        }

        //Log.w(TAG, "setIsInitialLoadingIsNotFinished: $isInitialLoadingFinished", )
    }

    private fun saveOperationCount() {
        // Log.d(TAG, "saveOperationCount: ")
        viewModelScope.launch {
            useCase.updateOperationCountUseCase()
        }
    }

    private fun readOperationCount() {
        // Log.i(TAG, "readOperationCount: ")
        viewModelScope.launch {
            useCase.readOperationCountUseCase().collect {
                //  Log.d(TAG, "readOperationCount: $it")
                operationCount.value = it
            }
        }
    }

    private fun readSerialNo() {
        // Log.i(TAG, "readSerialNo: ")
        viewModelScope.launch {
            useCase.readSerialNoCountUseCase().collect {
                // Log.d(TAG, "readSerialNo: $it")
                serialNo.value = it

            }
        }
    }

    fun readIpAddress() {
        viewModelScope.launch {
            useCase.readIpAddressUseCase().collectLatest { ip ->
                ipAddress.value = ip
            }
        }
    }

    fun readPortAddress() {
        viewModelScope.launch {
            useCase.readPortAddressUseCase().collectLatest { value ->
                port.value = value
            }
        }
    }

    private fun readBaseUrl() {
        // Log.e(TAG, "readBaseUrl: ")
        viewModelScope.launch {
            useCase.readBaseUrlUseCase().collect {

                baseUrl.value = it
                // Log.w(TAG, "readBaseUrl: $it $isInitialLoadingFinished")
                if (!isInitialLoadingFinished) {
                    Log.i(TAG, "readBaseUrl: $it")
                    getWelcomeMessage()
                    getCategoryList()
                    getProductList(value = 0)
                    getSectionList()
                   // getTableList(value = 1, callFromDiningScreen = false)
                    isInitialLoadingFinished = true
                }

            }
        }
    }

    // Product display
    private fun getWelcomeMessage() {
        //  Log.d(TAG, "getWelcomeMessage: ")
        viewModelScope.launch {
            useCase.getWelcomeMessageUseCase(url = baseUrl.value + HttpRoutes.WELCOME_MESSAGE)
                .collectLatest { result ->
                    sendSplashScreenEvent(SplashScreenEvent(UiEvent.CloseProgressBar))
                    if (result is GetDataFromRemote.Success) {
                        //  Log.w(TAG, "getWelcomeMessage: ${result.data}")
                        message.value = result.data.message
                        navigateToNextScreenWithDelayForSplashScreen(route = RootNavScreens.LocalRegisterScreen.route)
                        // isInitialLoadingFinished = true
                    }
                    if (result is GetDataFromRemote.Failed) {
                        isInitialLoadingFinished = false
                        // Log.e(TAG, "getWelcomeMessage: ${result.error.code}")
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
                        try {
                            categoryList.removeAll {
                                true
                            }
                        } catch (e: Exception) {
                            //  Log.e(TAG, "getCategoryList: ${e.message}")
                        }
                        categoryList.addAll(result.data)
                        // isInitialLoadingFinished = true
                    }
                    if (result is GetDataFromRemote.Failed) {
                        isInitialLoadingFinished = false
                        // Log.e(TAG, "getCategoryList: ${result.error.code}")
                    }

                }
        }
    }

    fun setSelectedCategory(value: Int) {
        selectedCategory.value = value
        if (value == -1) {
            return
        }
        getProductList(value = value)
    }

    private fun getProductList(value: Int) {

        try {
            productList.removeAll {
                true
            }
            selectedCategory.value = value
        } catch (e: Exception) {
            // Log.e(TAG, "getProductList: ${e.message}")
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
                    // Log.e(TAG, "getProductList: ${e.message}")
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
                    Log.e(TAG, "getProductList: ${result.error.message} ${result.error.code} $url")
                }

            }
        }
    }


    fun productSearch() {
        selectedCategory.value = -1
        try {
            productList.removeAll {
                true
            }
        } catch (e: Exception) {
            // Log.e(TAG, "productSearch: ${e.message}")
        }

        sendProductDisplayEvent(ProductDisplayScreenEvent(UiEvent.ShowProgressBar))
        val url = baseUrl.value + HttpRoutes.PRODUCT_SEARCH + productSearchText.value
        viewModelScope.launch(Dispatchers.IO) {
            useCase.productSearchUseCase(
                url = url
            ).collectLatest { result ->
                try {
                    productList.removeAll {
                        true
                    }
                    selectedCategory.value = -1
                    productSearchText.value = ""
                } catch (e: Exception) {
                    // Log.e(TAG, "productSearch: ${e.message}")
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
                    // Log.e(TAG, "product Search: ${result.error.message} $url")
                }

            }
        }


    }

    fun setProductSearchText(value: String) {
        productSearchText.value = value
    }


    // Get Dine in details
    private fun getSectionList() {
        viewModelScope.launch {
            useCase.getSectionListUseCase(
                url = baseUrl.value + HttpRoutes.SECTION_LIST
            ).collectLatest { result ->
                if (result is GetDataFromRemote.Success) {
                    // Log.i(TAG, "getSectionList: ${result.data}")
                    try {
                        sectionList.removeAll {
                            true
                        }
                    } catch (e: Exception) {
                        // Log.e(TAG, "getSectionList: ${e.message}")
                    }
                    sectionList.addAll(result.data)
                }
                isInitialLoadingFinished = true
                if (result is GetDataFromRemote.Failed) {
                    isInitialLoadingFinished = false
                    // Log.e(TAG, "getSectionList: ${result.error}")
                }

            }
        }
    }

    fun setSelectedSection(value: Int) {
        selectedSection.value = value
        getTableList(value = value, callFromDiningScreen = false)
    }

    fun getTableList(value: Int, callFromDiningScreen: Boolean) {
        selectedSection.value = value
        try {
            tableList.removeAll {
                true
            }

        } catch (e: Exception) {
            Log.e(TAG, "getTableList: ${e.message}")
        }
        // if (!callFromDiningScreen) {

        sendDineInScreenEvent(DineInScreenEvent(UiEvent.ShowProgressBar))
        // }


        val url = baseUrl.value + HttpRoutes.TABLE_LIST + "${selectedSection.value}"

        viewModelScope.launch(Dispatchers.IO) {
            useCase.getTableListUseCase(
                url = url
            ).collectLatest { result ->

                try {
                    tableList.removeAll {
                        true
                    }

                } catch (e: Exception) {
                    Log.e(TAG, "getTableList: ${e.message}")
                }


                sendDineInScreenEvent(DineInScreenEvent(UiEvent.CloseProgressBar))


                if (result is GetDataFromRemote.Success) {
                  //  Log.i(TAG, "getTableList: ${result.data}")
                    tableList.addAll(result.data)
                    selectedSection.value = value
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

    fun setSelectedTable(table: Table) {
        selectedTable.value = table
        sendDineInScreenEvent(DineInScreenEvent(UiEvent.Navigate(route = RootNavScreens.TableSelectionScreen.route)))
        getTableOrderList(id = table.id)
    }

    private fun getTableOrderList(id: Int) {
        // Log.w(TAG, "getTableOrderList: $id")
        sendTableSelectionUiEvent(TableSelectionUiEvent(UiEvent.ShowProgressBar))
        try {
            tableOrderList.removeAll {
                true
            }
        } catch (e: Exception) {
            //  Log.e(TAG, "getTableOrderList: ${e.message}")
        }

        viewModelScope.launch(Dispatchers.IO) {
            useCase.getTableOrderListUseCase(
                url = baseUrl.value + HttpRoutes.TABLE_ORDER + id
            ).collectLatest { result ->
                sendTableSelectionUiEvent(TableSelectionUiEvent(UiEvent.CloseProgressBar))

                if (result is GetDataFromRemote.Success) {
                    tableOrderList.addAll(result.data)
                }
                if (result is GetDataFromRemote.Failed) {
                    sendTableSelectionUiEvent(TableSelectionUiEvent(UiEvent.ShowSnackBar("There have some Error :- ${result.error.message} code: ${result.error.code} url:- ${baseUrl.value}${HttpRoutes.TABLE_ORDER}$id")))

                    //  Log.e(TAG, "getTableOrderList: ${result.error}")
                }
            }
        }
    }


    fun onSelectedTable() {
        tableId.value = selectedTable.value?.id!!
        showNewTableOrderAddButton.value =
            selectedTable.value?.occupied!! < selectedTable.value?.noOfSeats!!

    }


    fun newOrderButtonClicked(orderName: String, noOfChairRequired: Int) {
        newTableOrder.value = TableOrder(
            chairCount = noOfChairRequired,
            fK_TableId = selectedTable.value?.id!!,
            fK_KOTInvoiceId = 0,
            id = 0,
            isBooked = false,
            isReserved = false,
            orderName = orderName,
            remarks = null
        )
        tableOrderList.add(
            element = newTableOrder.value!!
        )
        showNewTableOrderAddButton.value = false
    }

    fun onOrderNameChange(orderName: String) {
        newTableOrder.value = newTableOrder.value?.copy(orderName = orderName)
        this.orderName.value = orderName
    }

    fun onChairCountChange(chairCount: Int) {
        newTableOrder.value = newTableOrder.value?.copy(chairCount = chairCount)
        this.chairCount.value = chairCount
    }

    fun removeTableOrder() {
        try {
            tableOrderList.removeAll {
                it.id == 0
            }
        } catch (e: Exception) {
            //Log.e(TAG, "removeOrder: ", )
        }
    }


    //login local server
    fun onRegisterLocally(password: String) {

        sendLocalRegisterEvent(UiEvent.ShowProgressBar)

        viewModelScope.launch(Dispatchers.IO) {
            useCase.registerUserUseCase(url = baseUrl.value + HttpRoutes.LOGIN + password)
                .collectLatest { result ->

                    sendLocalRegisterEvent(UiEvent.CloseProgressBar)

                    if (result is GetDataFromRemote.Success) {
                        val user = result.data
                        updateSerialNo()
                        fKUserId.value = user.userId
                        sendLocalRegisterEvent(UiEvent.Navigate(route = RootNavScreens.HomeScreen.route))
                    }
                    if (result is GetDataFromRemote.Failed) {
                        //  Log.e(TAG, "onRegisterLocally: ${result.error} ")
                        sendLocalRegisterEvent(UiEvent.ShowSnackBar(message = "There Have Some Error :- ${result.error.message} code:-  ${result.error.code}+ url:- ${baseUrl.value}${HttpRoutes.LOGIN}${password}"))
                    }
                }
        }

    }

    private fun updateSerialNo() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.updateSerialNoUseCase()
        }
    }

    fun onErrorOnPassword() {
        sendLocalRegisterEvent(UiEvent.ShowSnackBar(message = "Password not entered"))
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

    fun onIncrementAndDecrementKotItemClicked(count: Int, productId: Int, index: Int) {
        /* kotItemList.map { kotItem ->
             if (kotItem.productId == productId) {
                 kotNetAmount.value -= kotItem.netAmount
                 kotItem.quantity = count.toFloat()
                 kotItem.netAmount = count * kotItem.rate
                 kotNetAmount.value += kotItem.netAmount
             }
         }*/
        kotItemList.mapIndexed { i, kotItem ->
            if (i == index && kotItem.productId == productId) {
                kotNetAmount.value -= kotItem.netAmount
                kotItem.quantity = count.toFloat()
                kotItem.netAmount = count * kotItem.rate
                kotNetAmount.value += kotItem.netAmount
            }
        }
    }

    fun onDeleteItemFromKotItemClicked(kotItem: KotItem, index: Int) {
        Log.e(TAG, "onDeleteItemFromKotItemClicked: $index")
        /*kotItemList.removeAll {
            if (it.productId == kotItem.productId && it.quantity == kotItem.quantity) {
                kotNetAmount.value -= it.netAmount
                itemsCountInKot.value -= 1
                true
            } else {
                false
            }
        }*/

        kotItemList.removeAt(index = index)
        kotNetAmount.value -= kotItem.netAmount
        itemsCountInKot.value -= 1


    }

    fun addKotNotes(value: String) {
        kotNotes.value = value
    }

    fun addNoteToKotItem(kotItem: KotItem, note: String, index: Int) {
        kotItemList.mapIndexed { i, item ->
            if (item.productId == kotItem.productId && i == index) {
                item.itemNote = note
            }
        }
    }

    fun onNewTableOrderSet() {
        orderName.value = newTableOrder.value?.orderName!!
        // tableId.value = newTableOrder.value?.id!!
        chairCount.value = newTableOrder.value?.chairCount!!
        //Log.w(TAG, "onTableOrderSet: ${tableId.value}")*/
        sendTableSelectionUiEvent(
            TableSelectionUiEvent(
                UiEvent.Navigate(
                    route = RootNavScreens.ProductDisplayScreen.route
                )
            )
        )
    }

    fun onEditTableOrderSet(kotMasterId: Int) {
        getKOTDetails(kotNumber = kotMasterId)
        sendTableSelectionUiEvent(
            TableSelectionUiEvent(
                UiEvent.Navigate(
                    route = RootNavScreens.ShowKotScreen.route
                )
            )
        )
    }


    fun onResetTableId() {
        tableId.value = 0
    }

    fun generateKot(deviceId: String) {
        if (kotItemList.isEmpty()) {
            sendReviewScreenEvent(ReviewScreenEvent(UiEvent.ShowSnackBar("No item in kot")))
            return
        }
        sendReviewScreenEvent(ReviewScreenEvent(UiEvent.ShowProgressBar))
        val kot = Kot(
            fK_UserId = fKUserId.value,
            kotDetails = kotItemList.toList(),
            notes = kotNotes.value,
            serialNo = serialNo.value,
            terminal = deviceId,
            tableId = tableId.value,
            orderName = orderName.value,
            chairCount = chairCount.value,
            kotMasterId = 1
        )

        /* Log.i(TAG, "generateKot: ${kotItemList.toList()}")
         Log.d(TAG, "generateKot: ${kotMasterId.value}")
         Log.e(TAG, "generateKot: ${tableId.value}")
         Log.w(TAG, "generateKot: ${kotNotes.value}")
         Log.i(TAG, "generateKot: ${chairCount.value}")
         Log.e(TAG, "generateKot: ${orderName.value}")
         Log.w(TAG, "generateKot: ip address ${ipAddress.value}")
         Log.i(TAG, "generateKot: port address ${port.value}")*/

        // Log.d(TAG, "generateKot: $kot")
        viewModelScope.launch(Dispatchers.IO) {
            useCase.generateKotUseCase(
                url = baseUrl.value + HttpRoutes.GENERATE_KOT,
                kot = kot
            ) { statusCode, message ->
                sendReviewScreenEvent(ReviewScreenEvent(UiEvent.CloseProgressBar))
                if (statusCode in 200..299) {
                    sendReviewScreenEvent(ReviewScreenEvent(UiEvent.ShowAlertDialog))

                    if (ipAddress.value.isNotEmpty() && ipAddress.value.isNotBlank() && port.value.isNotEmpty() && port.value.isNotEmpty()) {

                        val printer = Printer(
                            address = ipAddress.value,
                            port = port.value.toInt(),
                            timeOut = 30
                        )
                        val p = printer.pr

                        val text = getPrintText(print = p)
                        printer.printKot(text)


                    }

                } else {
                    sendReviewScreenEvent(ReviewScreenEvent(UiEvent.ShowSnackBar(message = "There have some error :- $message")))
                }
            }

        }
    }


    // Editing Section
    fun setEditMode(value: Boolean) {
        editMode.value = value
    }

    //Edit Kot
    fun editKot(deviceId: String) {
        if (kotItemList.isEmpty()) {
            sendReviewScreenEvent(ReviewScreenEvent(UiEvent.ShowSnackBar("No item in Kot")))
            return
        }
        sendReviewScreenEvent(ReviewScreenEvent(UiEvent.ShowProgressBar))

        /* Log.i(TAG, "editKot: ${kotItemList.toList()}")
         Log.d(TAG, "editKot: ${kotMasterId.value}")
         Log.e(TAG, "editKot: ${tableId.value}")
         Log.w(TAG, "editKot: ${kotNotes.value}")
         Log.i(TAG, "editKot: ${chairCount.value}")
         Log.e(TAG, "editKot: ${orderName.value}")
 */
        viewModelScope.launch(Dispatchers.IO) {
            val url = baseUrl.value + HttpRoutes.EDIT_KOT + kotMasterId.value
            val kot = Kot(
                fK_UserId = fKUserId.value,
                kotDetails = kotItemList.toList(),
                notes = kotNotes.value,
                serialNo = serialNo.value,
                terminal = deviceId,
                tableId = tableId.value,
                orderName = orderName.value,
                chairCount = chairCount.value,
                kotMasterId = kotMasterId.value
            )
            useCase.editKotUseCase(
                url = url,
                kot = kot
            ) { statusCode, statusMessage ->
                sendReviewScreenEvent(ReviewScreenEvent(UiEvent.CloseProgressBar))
                if (statusCode in 200..299) {
                    sendReviewScreenEvent(ReviewScreenEvent(UiEvent.ShowAlertDialog))
                } else {
                    sendReviewScreenEvent(ReviewScreenEvent(UiEvent.ShowSnackBar(message = "There have some error :- $statusMessage")))
                }
            }
        }
    }


    // Get KOT
    fun getKOTDetails(kotNumber: Int) {
        try {
            kotItemList.removeAll {
                true
            }
        } catch (e: Exception) {
            Log.e(TAG, "getKOTDetails: ")
        }
        sendEditScreenEvent(UiEvent.ShowProgressBar)
        sendShowKotUiEvent(UiEvent.ShowProgressBar)
        viewModelScope.launch(Dispatchers.IO) {
            useCase.getKOTDetailsUseCase(
                url = baseUrl.value + HttpRoutes.EDIT_KOT + kotNumber
            ).collectLatest { result ->
                sendEditScreenEvent(UiEvent.CloseProgressBar)
                sendShowKotUiEvent(UiEvent.CloseProgressBar)
                if (result is GetDataFromRemote.Success) {
                    val value = result.data
                    if (value == null) {
                        sendEditScreenEvent(UiEvent.ShowEmptyList)
                    } else {
                        try {
                            kotItemList.removeAll {
                                true
                            }
                        } catch (e: Exception) {
                            Log.e(TAG, "getKOTDetails: ")
                        }

                        //Adding data for editing
                        kotItemList.addAll(value.kotDetails)
                        itemsCountInKot.value = value.kotDetails.size
                        try {
                            kotItemList.forEach { kotItem ->
                                kotNetAmount.value += kotItem.netAmount
                            }
                        } catch (e: Exception) {
                            Log.e(TAG, "getKOTDetails: ${e.message}")
                        }
                        Log.e(TAG, "getKOTDetails: ${chairCount.value}")
                        Log.d(TAG, "getKOTDetails: ${orderName.value}")

                        kotMasterId.value = value.kotMasterId
                        tableId.value = value.tableId
                        kotNotes.value = value.notes ?: ""
                        chairCount.value = if (chairCount.value == 0) {
                            value.chairCount
                        } else {
                            chairCount.value
                        }
                        orderName.value = orderName.value.ifEmpty {
                            value.orderName ?: ""
                        }
                        Log.w(TAG, "getKOTDetails: ---------------")
                        Log.e(TAG, "getKOTDetails: ${chairCount.value}")
                        Log.d(TAG, "getKOTDetails: ${orderName.value}")



                        sendEditScreenEvent(UiEvent.Navigate(RootNavScreens.ShowKotScreen.route))


                    }
                    // Log.d(TAG, "getKOTDetails: ${result.data}")
                }
                if (result is GetDataFromRemote.Failed) {
                    // Log.e(TAG, "getKOTDetails: ${result.error}")
                    sendEditScreenEvent(UiEvent.ShowSnackBar("There have Some error:- ${result.error.message}"))
                }
            }
        }
    }

    fun deleteKot() {
        sendShowKotUiEvent(UiEvent.ShowProgressBar)
        viewModelScope.launch(Dispatchers.IO) {
            useCase.deleteKotUseCase(
                url = baseUrl.value + HttpRoutes.EDIT_KOT + kotMasterId.value,
                callBack = { statusCade, statusMessage ->
                    sendShowKotUiEvent(UiEvent.CloseProgressBar)
                    if (statusCade == 204) {
                        resetKot()
                        sendShowKotUiEvent(UiEvent.ShowSnackBar("Deleted kot successfully "))
                        sendShowKotUiEvent(UiEvent.Navigate(route = RootNavScreens.HomeScreen.route))
                    } else {
                        sendShowKotUiEvent(UiEvent.ShowSnackBar("There have some Error with message : $statusMessage"))
                    }
                }
            )
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

    private fun sendDineInScreenEvent(dineInScreenEvent: DineInScreenEvent) {
        viewModelScope.launch {
            _dineInScreenEvent.send(dineInScreenEvent)
        }
    }

    /*fun filledTableClicked() {
        sendDineInScreenEvent(DineInScreenEvent(UiEvent.ShowSnackBar("This Table is full")))
    }*/

    private fun sendTableSelectionUiEvent(tableSelectionUiEvent: TableSelectionUiEvent) {
        viewModelScope.launch {
            _tableSelectionUiEvent.send(tableSelectionUiEvent)
        }
    }

    private fun sendLocalRegisterEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            _localRegisterEvent.send(uiEvent)
        }
    }

    private fun sendEditScreenEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            _editingScreenEvent.send(EditingScreenEvent(uiEvent = uiEvent))
        }
    }

    private fun sendShowKotUiEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            _showKotScreenEvent.send(ShowKotScreenUiEvent(uiEvent = uiEvent))
        }
    }


    fun setOrderMode(orderMode: OrderMode) {
        selectedOrderMode.value = orderMode
    }

    fun resetKot() {


        try {
            kotItemList.removeAll {
                true
            }
        } catch (e: Exception) {
            Log.e(TAG, "resetKot: ${e.message}")
        }

        itemsCountInKot.value = 0
        kotNetAmount.value = 0f
        kotNotes.value = ""

        kotMasterId.value = 0

        // Dine in features  imp:- table id will reset in separate function
        orderName.value = ""
        chairCount.value = 1

        selectedTable.value = null
        newTableOrder.value = null
        try {
            tableOrderList.removeAll {
                true
            }
        } catch (e: Exception) {
            Log.e(TAG, "resetKot: ${e.message}")
        }


        // product search
        productSearchText.value = ""

        // edit mode
        editMode.value = false

        // reset selected order mode
        selectedOrderMode.value = OrderMode.NONE

    }

    private fun getPrintText(print: EscPosPrinter): String {
        val date = SimpleDateFormat("dd/MM/yyyy h:mm:ss a", Locale.getDefault()).format(Date())
        val res = context.resources.getDrawableForDensity(
            R.drawable.unipospro_logo_full,
            DisplayMetrics.DENSITY_MEDIUM
        )
        var kotItemsString = ""
        kotItemList.forEachIndexed { index, kotItem ->
            kotItemsString += "[L]${index + 1}, ${kotItem.productName}[R]${kotItem.quantity}\n"
        }

        return "[C]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(
            print,
            res
        ) + "</img>\n" +
                "[C]<u>font size='big'>UNIPOSPRO</u>\n" +
                "[C]Date:- $date\n" +
                "[L]\n" +
                "[C]============================================\n" +
                kotItemsString + "\n" +
                "[C]<barcode type='ean13' height='10'>831254784551</barcode>\n" +
                "[C]<qrcode size='25'>123456789</qrcode>"
    }


}