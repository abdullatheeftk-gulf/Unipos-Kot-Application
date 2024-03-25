package com.gulfappdeveloper.project3.navigation.root

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gulfappdeveloper.project3.BuildConfig
import com.gulfappdeveloper.project3.data.remote.HttpRoutes
import com.gulfappdeveloper.project3.domain.datastore.UniLicenseDetails
import com.gulfappdeveloper.project3.domain.firebase.FirebaseError
import com.gulfappdeveloper.project3.domain.firebase.FirebaseGeneralData
import com.gulfappdeveloper.project3.domain.remote.get.GetDataFromRemote
import com.gulfappdeveloper.project3.domain.remote.get.dine_in.Section
import com.gulfappdeveloper.project3.domain.remote.get.dine_in.Table
import com.gulfappdeveloper.project3.domain.remote.get.dine_in.TableOrder
import com.gulfappdeveloper.project3.domain.remote.get.kot_list.UserOrder
import com.gulfappdeveloper.project3.domain.remote.get.product.Category
import com.gulfappdeveloper.project3.domain.remote.get.product.MultiSizeProduct
import com.gulfappdeveloper.project3.domain.remote.get.product.Product
import com.gulfappdeveloper.project3.domain.remote.license.LicenseRequestBody
import com.gulfappdeveloper.project3.domain.remote.post.Kot
import com.gulfappdeveloper.project3.domain.remote.post.KotItem
import com.gulfappdeveloper.project3.domain.remote.put.EditKOTBasic
import com.gulfappdeveloper.project3.presentation.presentation_util.OrderMode
import com.gulfappdeveloper.project3.presentation.presentation_util.UiEvent
import com.gulfappdeveloper.project3.presentation.screens.dine_in_screen.components.util.DineInScreenEvent
import com.gulfappdeveloper.project3.presentation.screens.editing_screen.EditingScreenEvent
import com.gulfappdeveloper.project3.presentation.screens.product_display_screen.util.ProductDisplayScreenEvent
import com.gulfappdeveloper.project3.presentation.screens.review_screen.util.ReviewScreenEvent
import com.gulfappdeveloper.project3.presentation.screens.show_kot_screen.util.ShowKotScreenUiEvent
import com.gulfappdeveloper.project3.presentation.screens.splash_screen.util.SplashScreenEvent
import com.gulfappdeveloper.project3.presentation.screens.table_selection_screen.TableSelectionUiEvent
import com.gulfappdeveloper.project3.presentation.screens.uni_license_act_screen.util.UniLicenseActScreenEvent
import com.gulfappdeveloper.project3.usecases.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

private const val TAG = "RootViewModel"

@HiltViewModel
class RootViewModel @Inject constructor(
    private val useCase: UseCase,
) : ViewModel() {


    private var isInitialLoadingFinished = false

    private val _splashScreenEvent = Channel<SplashScreenEvent>()
    val splashScreenEvent = _splashScreenEvent.receiveAsFlow()

    private val _uniLicenseActScreenEvent = Channel<UniLicenseActScreenEvent>()
    val uniLicenseActScreenEvent = _uniLicenseActScreenEvent.receiveAsFlow()

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
    private var operationCount = mutableStateOf(0)

    private val _deviceIdSate = mutableStateOf("")
    val deviceIdState: State<String> = _deviceIdSate


    // Base Url
    var baseUrl = mutableStateOf(HttpRoutes.BASE_URL)
        private set


    // Welcome message in splash screen
    var message = mutableStateOf("")
        private set

    // Kot Cancel privilege
    var kotCancelPrivilege = mutableStateOf(true)
        private set


    // For product display
    var selectedCategory = mutableStateOf(0)
        private set

    val categoryList = mutableStateListOf<Category>()
    val productList = mutableStateListOf<Product>()
    val multiSizeProductList = mutableStateListOf<MultiSizeProduct>()


    var productSearchText = mutableStateOf("")
        private set


    // For Dine in
    var selectedSection = mutableStateOf(1)
        private set

    val sectionList = mutableStateListOf<Section>()
    val tableList = mutableStateListOf<Table>()

    /*var selectedTable: MutableState<Table?> = mutableStateOf(null)
        private set*/

    private val _selectedTable: MutableState<Table?> = mutableStateOf(null)
    val selectedTable: State<Table?> = _selectedTable

    private val _newTableOrder: MutableState<TableOrder?> = mutableStateOf(null)
    val newTableOrder: State<TableOrder?> = _newTableOrder


    val tableOrderList = mutableStateListOf<TableOrder>()

    var showNewTableOrderAddButton = mutableStateOf(true)
        private set


    // For KOT
    val kotItemList = mutableStateListOf<KotItem>()

    var itemsCountInKot = mutableIntStateOf(0)
        private set

    var kotNetAmount = mutableFloatStateOf(0f)
        private set

    // it will add in ReviewScreen
    var kotNotes = mutableStateOf("")
        private set

    // user id from the login response
    private var fKUserId = mutableIntStateOf(0)

    // no of times login the app
    private var serialNo = mutableIntStateOf(0)

    // order name is added from dine in tables
    private var orderName = mutableStateOf("")

    var tableId = mutableIntStateOf(0)
        private set

    private val _chairCount = mutableIntStateOf(0)
    val chairCount: State<Int> = _chairCount


    // For editing only
    var kotMasterId = mutableIntStateOf(0)
        private set

    var editMode = mutableStateOf(false)
        private set

    // kot which is not completed for a user
    val kotPendingList = mutableStateListOf<UserOrder>()

    // Dine In and Take Away
    var selectedOrderMode = mutableStateOf(OrderMode.NONE)
        private set


    // Ip address and Port use case for printing purpose
    var ipAddress = mutableStateOf("")
        private set

    /*var port = mutableStateOf("")
        private set*/


    //firebase
    private val collectionName = "ErrorData"

    // public ip address
    private var publicIpAddress = ""

    // Unipos License activation
    var licenseKeyActivationError = mutableStateOf("")
        private set

    // unipos license details
    var uniLicenseDetails: MutableState<UniLicenseDetails?> = mutableStateOf(null)
        private set

    // kot type to display in show kot screen
    private val _kotType = mutableStateOf(false)
    val kotType: State<Boolean> = _kotType


    init {
        sendSplashScreenEvent(SplashScreenEvent(UiEvent.ShowProgressBar))

        readDeviceId()
        // saveOperationCount()
        //readOperationCount()
        readSerialNo()
        readBaseUrl()
        readIpAddress()
        //readPortAddress()

    }

    // call to get ip4 address
    fun setPublicIpAddress() {
        getIp4Address()
    }

    fun setIsInitialLoadingIsNotFinished() {

        try {
            categoryList.clear()
            productList.clear()
            sectionList.clear()
            tableList.clear()
            readBaseUrl()
            isInitialLoadingFinished = false

            // Log.d(TAG, "setIsInitialLoadingIsNotFinished: $categoryList")

        } catch (e: Exception) {
            //Log.e(TAG, "setInitialLoadingIsFinished:${e.message} ")
            e.printStackTrace()
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
                // Log.d(TAG, "readOperationCount: $it")
                operationCount.value = it
            }
        }
    }

    private fun readDeviceId() {
        viewModelScope.launch {
            useCase.readDeviceIdUseCase().collectLatest {
                _deviceIdSate.value = it
                // Log.w(TAG, "readDeviceId: ${_deviceIdSate.value}")
            }
        }
    }

    // No of login
    private fun readSerialNo() {
        // Log.i(TAG, "readSerialNo: ")
        viewModelScope.launch {
            useCase.readSerialNoCountUseCase().collect {
                // Log.d(TAG, "readSerialNo: $it")
                serialNo.intValue = it

            }
        }
    }

    private fun readIpAddress() {
        viewModelScope.launch {
            useCase.readIpAddressUseCase().collectLatest { ip ->
                ipAddress.value = ip
            }
        }
    }

    /* private fun readPortAddress() {
         viewModelScope.launch {
             useCase.readPortAddressUseCase().collectLatest { value ->
                 //port.value = value
             }
         }
     }*/

    private fun readBaseUrl() {
        // Log.e(TAG, "readBaseUrl: ")
        viewModelScope.launch {
            useCase.readBaseUrlUseCase().collect {

                baseUrl.value = it
                //Log.w(TAG, "readBaseUrl: base url $it , initial loading $isInitialLoadingFinished")
                if (!isInitialLoadingFinished) {
                    //Log.i(TAG, "readBaseUrl: $it")
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
                        message.value = result.data.message

                        readUniLicenseKeyDetails()

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
                        useCase.insertErrorDataToFireStoreUseCase(
                            collectionName = collectionName,
                            documentName = "getWelcomeMessage,${Date()}",
                            errorData = FirebaseError(
                                errorMessage = result.error.message ?: "",
                                errorCode = result.error.code,
                                url = baseUrl.value + HttpRoutes.WELCOME_MESSAGE,
                                ipAddress = publicIpAddress
                            )
                        )
                    }
                }
        }
    }


    // get category list
    private fun getCategoryList() {
        viewModelScope.launch {
            useCase.getCategoryListUseCase(url = baseUrl.value + HttpRoutes.CATEGORY_LIST)
                .collectLatest { result ->
                    if (result is GetDataFromRemote.Success) {
                        try {
                            categoryList.clear()
                        } catch (e: Exception) {
                            //  Log.e(TAG, "getCategoryList: ${e.message}")
                        }
                        categoryList.addAll(result.data)
                        // isInitialLoadingFinished = true
                    }
                    if (result is GetDataFromRemote.Failed) {
                        isInitialLoadingFinished = false
                        useCase.insertErrorDataToFireStoreUseCase(
                            collectionName = collectionName,
                            documentName = "getCategoryList,${Date()}",
                            errorData = FirebaseError(
                                errorMessage = result.error.message ?: "",
                                errorCode = result.error.code,
                                url = baseUrl.value + baseUrl.value + HttpRoutes.CATEGORY_LIST,
                                ipAddress = publicIpAddress
                            )
                        )

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
            productList.clear()
            selectedCategory.value = value
        } catch (e: Exception) {
            // Log.e(TAG, "getProductList: ${e.message}")
        }


        sendProductDisplayEvent(ProductDisplayScreenEvent(UiEvent.ShowProgressBar))

        val url = baseUrl.value + HttpRoutes.PRODUCT_LIST + "${selectedCategory.value}"

        viewModelScope.launch() {
            useCase.getProductListUseCase(
                url = url
            ).collectLatest { result ->
                try {
                    productList.clear()
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
                    //Log.e(TAG, "getProductList: ${result.error.message} ${result.error.code} $url")
                    useCase.insertErrorDataToFireStoreUseCase(
                        collectionName = collectionName,
                        documentName = "getProductList,${Date()}",
                        errorData = FirebaseError(
                            errorMessage = result.error.message ?: "",
                            errorCode = result.error.code,
                            url = url,
                            ipAddress = publicIpAddress
                        )
                    )
                }

            }
        }
    }

    fun getMultiSizeProduct(id: Int) {
        try {
            multiSizeProductList.clear()
        } catch (e: Exception) {
            // Log.e(TAG, "getMultiSizeProduct: ${e.message}")
        }


        val url = baseUrl.value + HttpRoutes.MULTI_SIZE_PRODUCT + id
        viewModelScope.launch(Dispatchers.IO) {
            useCase.getMultiSizeProduct(url = url).collectLatest { result ->
                if (result is GetDataFromRemote.Success) {
                    // Log.w(TAG, "getMultiSizeProduct: $url ${it.data}")
                    multiSizeProductList.addAll(result.data)
                }
                if (result is GetDataFromRemote.Failed) {
                    // Log.e(TAG, "getMultiSizeProduct: ${it.error}")
                    useCase.insertErrorDataToFireStoreUseCase(
                        collectionName = collectionName,
                        documentName = "getMultiSizeProduct,${Date()}",
                        errorData = FirebaseError(
                            errorMessage = result.error.message ?: "",
                            errorCode = result.error.code,
                            url = baseUrl.value + HttpRoutes.MULTI_SIZE_PRODUCT + id,
                            ipAddress = publicIpAddress
                        )
                    )
                }
            }
        }
    }


    fun productSearch() {
        selectedCategory.value = -1
        try {
            productList.clear()
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
                    productList.clear()
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
                    useCase.insertErrorDataToFireStoreUseCase(
                        collectionName = collectionName,
                        documentName = "productSearch,${Date()}",
                        errorData = FirebaseError(
                            errorMessage = result.error.message ?: "",
                            errorCode = result.error.code,
                            url = baseUrl.value + HttpRoutes.PRODUCT_SEARCH + productSearchText.value,
                            ipAddress = publicIpAddress
                        )
                    )
                }

            }
        }


    }

    fun setProductSearchText(value: String) {
        productSearchText.value = value
    }


    // Get Dine in details
    private fun getSectionList() {
        val url = baseUrl.value + HttpRoutes.SECTION_LIST
        Log.d(TAG, "getSectionList: $url")
        viewModelScope.launch {
            useCase.getSectionListUseCase(
                url = url
            ).collect { result ->
                Log.d(TAG, "getSectionList: ")
                if (result is GetDataFromRemote.Success) {

                    try {
                        sectionList.clear()
                    } catch (e: Exception) {
                        Log.e(TAG, "getSectionList: ${e.message}")
                    }
                    sectionList.addAll(result.data)
                }
                isInitialLoadingFinished = true
                if (result is GetDataFromRemote.Failed) {
                    isInitialLoadingFinished = false
                    useCase.insertErrorDataToFireStoreUseCase(
                        collectionName = collectionName,
                        documentName = "getSectionList,${Date()}",
                        errorData = FirebaseError(
                            errorMessage = result.error.message ?: "",
                            errorCode = result.error.code,
                            url = url,
                            ipAddress = publicIpAddress
                        )
                    )
                }

            }
        }
    }


    fun getTableList(value: Int) {

        selectedSection.value = value
        try {
            tableList.clear()

        } catch (e: Exception) {
            // Log.e(TAG, "getTableList: ${e.message}")
        }

        sendDineInScreenEvent(DineInScreenEvent(UiEvent.ShowProgressBar))

        val url = baseUrl.value + HttpRoutes.TABLE_LIST + "${selectedSection.value}"

        viewModelScope.launch(Dispatchers.IO) {
            useCase.getTableListUseCase(
                url = url
            ).collectLatest { result ->

                try {
                    tableList.clear()

                } catch (e: Exception) {
                    // Log.e(TAG, "getTableList: ${e.message}")
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
                    // Log.e(TAG, "getTableList: ${result.error.message} $url")
                    useCase.insertErrorDataToFireStoreUseCase(
                        collectionName = collectionName,
                        documentName = "getTableList,${Date()}",
                        errorData = FirebaseError(
                            errorMessage = result.error.message ?: "",
                            errorCode = result.error.code,
                            url = url,
                            ipAddress = publicIpAddress
                        )
                    )
                }

            }
        }
    }

    fun setSelectedTable(table: Table) {
        _selectedTable.value = table
        sendDineInScreenEvent(DineInScreenEvent(UiEvent.Navigate(route = RootNavScreens.TableSelectionScreen.route)))
        getTableOrderList(id = table.id)
    }

    private fun getTableOrderList(id: Int) {
        var chairs = 0
        // Log.w(TAG, "getTableOrderList: $id")
        sendTableSelectionUiEvent(TableSelectionUiEvent(UiEvent.ShowProgressBar))
        try {
            tableOrderList.clear()
        } catch (e: Exception) {
            //  Log.e(TAG, "getTableOrderList: ${e.message}")
        }

        viewModelScope.launch(Dispatchers.IO) {
            useCase.getTableOrderListUseCase(
                url = baseUrl.value + HttpRoutes.TABLE_ORDER + id
            ).collectLatest { result ->
                sendTableSelectionUiEvent(TableSelectionUiEvent(UiEvent.CloseProgressBar))

                if (result is GetDataFromRemote.Success) {

                    result.data.forEach {
                        chairs += it.chairCount ?: 0
                    }

                    _selectedTable.value?.let {
                        showNewTableOrderAddButton.value = it.noOfSeats > chairs
                    }


                    tableOrderList.addAll(result.data)
                }
                if (result is GetDataFromRemote.Failed) {
                    sendTableSelectionUiEvent(TableSelectionUiEvent(UiEvent.ShowSnackBar("There have some Error :- ${result.error.message} code: ${result.error.code} url:- ${baseUrl.value}${HttpRoutes.TABLE_ORDER}$id")))
                    useCase.insertErrorDataToFireStoreUseCase(
                        collectionName = collectionName,
                        documentName = "getTableOrderList,${Date()}",
                        errorData = FirebaseError(
                            errorMessage = result.error.message ?: "",
                            errorCode = result.error.code,
                            url = baseUrl.value + HttpRoutes.TABLE_ORDER + id,
                            ipAddress = publicIpAddress
                        )
                    )
                    //  Log.e(TAG, "getTableOrderList: ${result.error}")
                }
            }
        }
    }


    fun onSelectedTable() {
        //   Log.d("Test", "onSelectedTable:")
        try {
            tableId.intValue = _selectedTable.value?.id!!
            showNewTableOrderAddButton.value =
                _selectedTable.value?.occupied!! < _selectedTable.value?.noOfSeats!!
        } catch (e: Exception) {
            //   Log.e("Test", "onSelectedTable: ${e.message} ")
        }

    }


    fun newOrderButtonClicked(orderName: String, noOfChairRequired: Int) {
        if (_selectedTable.value == null) {
            sendTableSelectionUiEvent(
                TableSelectionUiEvent(UiEvent.ShowSnackBar("There have some error on Selected table, Selected table is null func: newOrderButtonClicked"))
            )
            return
        }
        _selectedTable.value?.let {
            _newTableOrder.value = TableOrder(
                chairCount = noOfChairRequired,
                fK_TableId = it.id,
                fK_KOTInvoiceId = 0,
                id = 0,
                isBooked = false,
                isReserved = false,
                orderName = orderName,
                remarks = null
            )
        }

        tableOrderList.removeAll {
            it.fK_KOTInvoiceId == 0
        }
        tableOrderList.add(
            element = _newTableOrder.value!!
        )
        showNewTableOrderAddButton.value = false
    }

    /*  fun onOrderNameChange(orderName: String) {
          newTableOrder.value = newTableOrder.value?.copy(orderName = orderName)
          this.orderName.value = orderName
      }

      fun onChairCountChange(chairCount: Int) {
          newTableOrder.value = newTableOrder.value?.copy(chairCount = chairCount)
          this.chairCount.value = chairCount
      }*/

    fun removeTableOrderAndResetSelectedTableAndTableId() {
        try {
            tableOrderList.removeAll {
                it.id == 0
            }
        } catch (e: Exception) {
            //Log.e(TAG, "removeOrder: ", )
        }
        //new code added
        _selectedTable.value = null
        resetTableId()
    }


    // login local server
    fun onRegisterLocally(password: String) {

        sendLocalRegisterEvent(UiEvent.ShowProgressBar)

        viewModelScope.launch(Dispatchers.IO) {
            useCase.registerUserUseCase(url = baseUrl.value + HttpRoutes.LOGIN + password)
                .collectLatest { result ->

                    sendLocalRegisterEvent(UiEvent.CloseProgressBar)

                    if (result is GetDataFromRemote.Success) {
                        val user = result.data
                        updateSerialNo()
                        fKUserId.intValue = user.userId
                        sendLocalRegisterEvent(UiEvent.Navigate(route = RootNavScreens.HomeScreen.route))
                    }
                    if (result is GetDataFromRemote.Failed) {
                        //  Log.e(TAG, "onRegisterLocally: ${result.error} ")
                        sendLocalRegisterEvent(UiEvent.ShowSnackBar(message = "There Have Some Error :- ${result.error.message} code:-  ${result.error.code}+ url:- ${baseUrl.value}${HttpRoutes.LOGIN}${password}"))
                        useCase.insertErrorDataToFireStoreUseCase(
                            collectionName = collectionName,
                            documentName = "onRegisterLocally,${Date()}",
                            errorData = FirebaseError(
                                errorCode = result.error.code,
                                errorMessage = result.error.message!!,
                                url = "${baseUrl.value}${HttpRoutes.LOGIN}${password}",
                                ipAddress = publicIpAddress
                            )
                        )
                    }
                }
        }

    }

    fun getKotCancelPrivilege() {
        viewModelScope.launch(Dispatchers.IO) {
            val url = "${baseUrl.value}${HttpRoutes.KOT_CANCEL_PRIVILEGE}${fKUserId.intValue}"
            useCase.kotCancelPrivilegeUseCase(url = url).collectLatest { result ->
                when (result) {
                    is GetDataFromRemote.Success -> {
                        //  Log.e(TAG, "getKotCancelPrivilege: ${result.data.isPrivileged}")
                        kotCancelPrivilege.value = result.data.isPrivileged
                    }

                    is GetDataFromRemote.Failed -> {

                        useCase.insertErrorDataToFireStoreUseCase(
                            collectionName = collectionName,
                            documentName = "getKotCancelPrivilege,${Date()}",
                            errorData = FirebaseError(
                                errorCode = result.error.code,
                                errorMessage = result.error.message ?: "",
                                url = url,
                                ipAddress = publicIpAddress
                            )
                        )
                    }


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
        try {
            val kotItem = KotItem(
                barcode = product.barcode,
                netAmount = count * product.rate,
                quantity = count.toFloat(),
                rate = product.rate,
                productId = product.id,
                productName = product.name
            )
            kotItemList.add(kotItem)
            itemsCountInKot.intValue += 1
            kotNetAmount.floatValue += count * product.rate
        }catch (e:Exception){
            viewModelScope.launch(Dispatchers.IO) {
                useCase.insertErrorDataToFireStoreUseCase(
                    collectionName = "arrayOutOfBoundException funcaddProductToKOT",
                    documentName = "${Date()}",
                    errorData = FirebaseError(
                        errorMessage = e.message ?: "there have some problem on editing"
                    )
                )
            }
        }

    }

    fun onIncrementAndDecrementKotItemClicked(count: Int, productId: Int, index: Int) {
        try {


            kotItemList.mapIndexed { i, kotItem ->
                if (i == index && kotItem.productId == productId) {
                    kotNetAmount.floatValue -= kotItem.netAmount
                    kotItem.quantity = count.toFloat()
                    kotItem.netAmount = count * kotItem.rate
                    kotNetAmount.floatValue += kotItem.netAmount
                }
            }
        } catch (e: Exception) {
            sendReviewScreenEvent(
                ReviewScreenEvent(
                    UiEvent.ShowSnackBar(
                        message = e.message ?: "There have some problem while editing"
                    )
                )
            )
            viewModelScope.launch(Dispatchers.IO) {
                useCase.insertErrorDataToFireStoreUseCase(
                    collectionName = "arrayOutOfBoundException func onIncrementAndDecrementKotItemClicked",
                    documentName = "${Date()}",
                    errorData = FirebaseError(
                        errorMessage = e.message ?: "there have some problem on editing"
                    )
                )
            }

        }
    }

    fun onDeleteItemFromKotItemClicked(kotItem: KotItem, index: Int) {

        try {
            kotItemList.removeAt(index = index)
            kotNetAmount.floatValue -= kotItem.netAmount
            itemsCountInKot.intValue -= 1
        } catch (e: Exception) {
            sendReviewScreenEvent(
                ReviewScreenEvent(
                    UiEvent.ShowSnackBar(
                        message = e.message ?: "There have some problem while deleting"
                    )
                )
            )
            viewModelScope.launch(Dispatchers.IO) {
                useCase.insertErrorDataToFireStoreUseCase(
                    collectionName = "arrayOutOfBoundException func onDeleteItemFromKotItemClicked",
                    documentName = "${Date()}",
                    errorData = FirebaseError(
                        errorMessage = e.message ?: "there have some problem on deleting"
                    )
                )
            }

        }


    }

    fun addKotNotes(value: String) {
        kotNotes.value = value
    }

    fun addNoteToKotItem(kotItem: KotItem, note: String, index: Int) {
        try {
            kotItemList.mapIndexed { i, item ->
                if (item.productId == kotItem.productId && i == index) {
                    item.itemNote = note
                }
            }
        }catch (e:Exception){
            sendReviewScreenEvent(
                ReviewScreenEvent(
                    UiEvent.ShowSnackBar(
                        message = e.message ?: "There have some problem while deleting"
                    )
                )
            )
            viewModelScope.launch(Dispatchers.IO) {
                useCase.insertErrorDataToFireStoreUseCase(
                    collectionName = "arrayOutOfBoundException func addNoteToKotItem",
                    documentName = "${Date()}",
                    errorData = FirebaseError(
                        errorMessage = e.message ?: "there have some problem on deleting"
                    )
                )
            }
        }
    }

    fun onNewTableOrderSet() {
        _newTableOrder.value?.let {
            orderName.value = it.orderName
            // tableId.value = newTableOrder.value?.id!!
            it.chairCount?.let { cc ->
                _chairCount.intValue = cc
            }

        }

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


    private fun resetTableId() {
        tableId.intValue = 0
    }

    // kot generation
    fun generateKot(deviceId: String) {
        if (tableId.intValue == 0 && _chairCount.intValue != 0) {
            sendReviewScreenEvent(ReviewScreenEvent(UiEvent.ShowSnackBar("tableId and chair count should be 0")))
            //  Log.e("Test", "generateKot: ${tableId.value} ${chairCount.value}")
            return
        }
        if (tableId.intValue != 0 && _chairCount.intValue == 0) {
            sendReviewScreenEvent(ReviewScreenEvent(UiEvent.ShowSnackBar("tableId and chair count should be 0")))
            //Log.e("Test", "generateKot: ${tableId.value} ${chairCount.value}")
            return
        }
        if (kotItemList.isEmpty()) {
            sendReviewScreenEvent(ReviewScreenEvent(UiEvent.ShowSnackBar("No item in kot")))
            return
        }
        sendReviewScreenEvent(ReviewScreenEvent(UiEvent.ShowProgressBar))
        val kot = Kot(
            fK_UserId = fKUserId.intValue,
            kotDetails = kotItemList.toList(),
            notes = kotNotes.value,
            serialNo = serialNo.intValue,
            terminal = _deviceIdSate.value.ifEmpty { deviceId },
            tableId = tableId.intValue,
            orderName = orderName.value,
            chairCount = _chairCount.intValue,
            // For generation it will be 1
            kotMasterId = 1
        )
        val url = baseUrl.value + HttpRoutes.GENERATE_KOT


        // Log.d(TAG, "generateKot: $kot")
        viewModelScope.launch(Dispatchers.IO) {
            useCase.generateKotUseCase(
                url = url,
                kot = kot
            ) { statusCode, message ->
                //  Log.w("Test", "generateKot: $kot")
                sendReviewScreenEvent(ReviewScreenEvent(UiEvent.CloseProgressBar))
                if (statusCode in 200..299) {
                    sendReviewScreenEvent(ReviewScreenEvent(UiEvent.ShowAlertDialog))

                } else {
                    // Log.e(TAG, "generateKot: $", )
                    sendReviewScreenEvent(ReviewScreenEvent(UiEvent.ShowSnackBar(message = "There have some error Error:- code = $statusCode,$message \nurl:- $url\nkot:- $kot")))
                    useCase.insertErrorDataToFireStoreUseCase(
                        collectionName = collectionName,
                        documentName = "generateKot,${Date()}",
                        errorData = FirebaseError(
                            errorCode = statusCode,
                            errorMessage = "$message, kot:- $kot",
                            url = url,
                            ipAddress = publicIpAddress
                        )
                    )
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
        if (tableId.intValue == 0 && _chairCount.intValue != 0) {
            sendReviewScreenEvent(ReviewScreenEvent(UiEvent.ShowSnackBar("tableId and chair count should be 0")))
            // Log.e("Test", "generateKot: ${tableId.value} ${chairCount.value}")
            return
        }
        if (tableId.intValue != 0 && _chairCount.intValue == 0) {
            sendReviewScreenEvent(ReviewScreenEvent(UiEvent.ShowSnackBar("tableId and chair count should be 0")))
            //Log.e("Test", "generateKot: ${tableId.value} ${chairCount.value}")
            return
        }
        if (kotItemList.isEmpty()) {
            sendReviewScreenEvent(ReviewScreenEvent(UiEvent.ShowSnackBar("No item in Kot")))
            return
        }
        sendReviewScreenEvent(ReviewScreenEvent(UiEvent.ShowProgressBar))

        viewModelScope.launch(Dispatchers.IO) {
            val url = baseUrl.value + HttpRoutes.EDIT_KOT + kotMasterId.intValue
            val kot = Kot(
                fK_UserId = fKUserId.intValue,
                kotDetails = kotItemList.toList(),
                notes = kotNotes.value,
                serialNo = serialNo.intValue,
                terminal = _deviceIdSate.value.ifEmpty { deviceId },
                tableId = tableId.intValue,
                orderName = orderName.value,
                chairCount = _chairCount.intValue,
                kotMasterId = kotMasterId.intValue
            )

            useCase.editKotUseCase(
                url = url,
                kot = kot
            ) { statusCode, statusMessage ->
                //Log.e(TAG, "editKot: $kot", )
                sendReviewScreenEvent(ReviewScreenEvent(UiEvent.CloseProgressBar))
                if (statusCode in 200..299) {
                    sendReviewScreenEvent(ReviewScreenEvent(UiEvent.ShowAlertDialog))
                } else {
                    useCase.insertErrorDataToFireStoreUseCase(
                        collectionName = collectionName,
                        documentName = "editKot,${Date()}",
                        errorData = FirebaseError(
                            errorCode = statusCode,
                            errorMessage = "$statusMessage, kot:- $kot",
                            url = url,
                            ipAddress = publicIpAddress
                        )
                    )
                    sendReviewScreenEvent(ReviewScreenEvent(UiEvent.ShowSnackBar(message = "There have some error :-Error message:-  $statusMessage, ErrorCode:- $statusCode")))
                }
            }
        }
    }

    fun editOrderNameAndChairCount(
        id: Int,
        orderName: String,
        chairSelected: Int,
        tableId: Int
    ) {
        sendProductDisplayEvent(
            ProductDisplayScreenEvent(
                uiEvent = UiEvent.ShowProgressBar
            )
        )
        val url = baseUrl.value + HttpRoutes.EDIT_ORDER_NAME_AND_CHAIR_COUNT + id
        val editKOTBasic = EditKOTBasic(
            orderName = orderName,
            chairCount = chairSelected
        )
        viewModelScope.launch(Dispatchers.IO) {
            useCase.editKotBasicUseCase(
                url = url,
                editKOTBasic = editKOTBasic
            ) { statusCode, statusMessage ->

                if (statusCode in 200..299) {
                    sendProductDisplayEvent(
                        ProductDisplayScreenEvent(
                            uiEvent = UiEvent.ShowAlertDialog
                        )
                    )
                    getTableOrderList(id = tableId)
                    //Log.i(TAG, "editOrderNameAndChairCount: $statusCode $statusMessage")
                } else {
                    sendProductDisplayEvent(
                        ProductDisplayScreenEvent(
                            uiEvent = UiEvent.CloseProgressBar
                        )
                    )
                    sendProductDisplayEvent(
                        ProductDisplayScreenEvent(
                            uiEvent = UiEvent.ShowSnackBar(
                                message = "$statusCode $statusMessage"
                            )
                        )
                    )
                    useCase.insertErrorDataToFireStoreUseCase(
                        collectionName = collectionName,
                        documentName = "editOrderNameAndChairCount,${Date()}",
                        errorData = FirebaseError(
                            errorCode = statusCode,
                            errorMessage = statusMessage + "kot basic:- $editKOTBasic",
                            url = url,
                            ipAddress = publicIpAddress
                        )
                    )

                }
            }
        }

    }

    // false is for dine in


    // Get KOT
    fun getKOTDetails(kotNumber: Int) {
        _kotType.value = false
        try {
            kotItemList.clear()
        } catch (e: Exception) {
            //Log.e(TAG, "getKOTDetails: ")
        }
        /*if (isOrderFromEditScreen) {
            sendEditScreenEvent(UiEvent.ShowProgressBar)
        }*/
        sendShowKotUiEvent(UiEvent.ShowProgressBar)
        viewModelScope.launch(Dispatchers.IO) {
            useCase.getKOTDetailsUseCase(
                url = baseUrl.value + HttpRoutes.EDIT_KOT + kotNumber
            ).collectLatest { result ->
                /*if (isOrderFromEditScreen) {
                    sendEditScreenEvent(UiEvent.CloseProgressBar)
                }*/
                sendShowKotUiEvent(UiEvent.CloseProgressBar)
                if (result is GetDataFromRemote.Success) {
                    val value = result.data
                    if (value == null) {
                        /*if (isOrderFromEditScreen) {
                            sendEditScreenEvent(UiEvent.ShowEmptyList)
                        }*/
                    } else {
                        // Log.e("Test", "getKOTDetails: ${value.tableId} ${value.chairCount}")
                        _kotType.value = value.tableId <= 0
                        //Log.w("Test", "getKOTDetails: ${_kotType.value}")
                        try {
                            kotItemList.clear()
                        } catch (e: Exception) {
                            //  Log.e(TAG, "getKOTDetails: ")
                        }

                        //Adding data for editing
                        kotItemList.addAll(value.kotDetails)

                        itemsCountInKot.intValue = value.kotDetails.size
                        try {
                            kotItemList.forEach { kotItem ->
                                kotNetAmount.floatValue += kotItem.netAmount
                            }
                        } catch (e: Exception) {
                            //  Log.e(TAG, "getKOTDetails: ${e.message}")
                        }
                        // Log.e(TAG, "getKOTDetails: ${chairCount.value}")
                        // Log.d(TAG, "getKOTDetails: ${orderName.value}")

                        kotMasterId.intValue = value.kotMasterId
                        tableId.intValue = value.tableId
                        kotNotes.value = value.notes ?: ""
                        _chairCount.intValue = if (_chairCount.intValue == 0) {
                            value.chairCount
                        } else {
                            _chairCount.intValue
                        }
                        orderName.value = orderName.value.ifEmpty {
                            value.orderName ?: ""
                        }

                    }

                }
                if (result is GetDataFromRemote.Failed) {
                    // Log.e(TAG, "getKOTDetails: ${result.error}")
                    sendEditScreenEvent(UiEvent.ShowSnackBar("There have Some error:- ${result.error.message}"))
                    useCase.insertErrorDataToFireStoreUseCase(
                        collectionName = collectionName,
                        documentName = "getKOTDetails,${Date()}",
                        errorData = FirebaseError(
                            errorCode = result.error.code,
                            errorMessage = result.error.message ?: "",
                            url = baseUrl.value + HttpRoutes.EDIT_KOT + kotNumber,
                            ipAddress = publicIpAddress
                        )
                    )
                }
            }
        }
    }

    fun deleteKot() {
        val url = baseUrl.value + HttpRoutes.EDIT_KOT + kotMasterId.intValue
        sendShowKotUiEvent(UiEvent.ShowProgressBar)
        viewModelScope.launch(Dispatchers.IO) {
            useCase.deleteKotUseCase(
                url = url,
                callBack = { statusCode, statusMessage ->
                    sendShowKotUiEvent(UiEvent.CloseProgressBar)

                    if (statusCode == 204) {
                        resetKot()
                        removeTableOrderAndResetSelectedTableAndTableId()
                        sendShowKotUiEvent(UiEvent.ShowSnackBar("Deleted kot successfully "))
                        sendShowKotUiEvent(UiEvent.Navigate(route = RootNavScreens.HomeScreen.route))
                    } else {

                        sendShowKotUiEvent(UiEvent.ShowSnackBar("There have some Error with message : $statusMessage"))
                        useCase.insertErrorDataToFireStoreUseCase(
                            collectionName = collectionName,
                            documentName = "deleteKot,${Date()}",
                            errorData = FirebaseError(
                                errorCode = statusCode,
                                errorMessage = statusMessage,
                                url = baseUrl.value + HttpRoutes.EDIT_KOT + kotMasterId.intValue,
                                ipAddress = publicIpAddress
                            )
                        )
                    }
                }
            )
        }
    }


    // Miscellaneous
    fun showSnackBarInProductDisplayScreen(message: String) {
        sendProductDisplayEvent(ProductDisplayScreenEvent(UiEvent.ShowSnackBar(message)))
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

    fun removeUnOrderedTableOrder() {
        tableOrderList.removeAll {
            it.fK_KOTInvoiceId == 0
        }
    }

    fun resetKot() {


        try {
            kotItemList.clear()
        } catch (e: Exception) {
            // Log.e(TAG, "resetKot: ${e.message}")
        }

        itemsCountInKot.intValue = 0
        kotNetAmount.floatValue = 0f
        kotNotes.value = ""

        kotMasterId.intValue = 0

        // Dine in features  imp:- table id will reset in separate function
        orderName.value = ""

        _chairCount.intValue = 0
        //newly added
        //  tableId.value = 0

        _selectedTable.value = null
        _newTableOrder.value = null
        try {
            tableOrderList.clear()
        } catch (e: Exception) {
            // Log.e(TAG, "resetKot: ${e.message}")
        }


        // product search
        productSearchText.value = ""

        // edit mode
        editMode.value = false

        // reset selected order mode
        selectedOrderMode.value = OrderMode.NONE

        // Log.e("Test", "resetKot: ${tableId.value}")


    }


    fun getListOfPendingKOTs() {
        try {
            kotPendingList.clear()
        } catch (e: Exception) {
            // Log.e(TAG, "getListOfPendingKOTs: ${e.message}")
        }
        sendEditScreenEvent(UiEvent.ShowProgressBar)
        val url = baseUrl.value + HttpRoutes.LIST_KOT_OF_USER + fKUserId.intValue
        viewModelScope.launch(Dispatchers.IO) {
            useCase.getListOfPendingKOTs(url = url).collectLatest { result ->
                sendEditScreenEvent(UiEvent.CloseProgressBar)
                if (result is GetDataFromRemote.Success) {
                    val list = result.data
                    if (list.isEmpty()) {
                        sendEditScreenEvent(UiEvent.ShowEmptyList)
                    } else {
                        kotPendingList.addAll(result.data)
                        sendEditScreenEvent(UiEvent.ShowList)
                    }
                }
                if (result is GetDataFromRemote.Failed) {

                    sendEditScreenEvent(UiEvent.ShowSnackBar("There have some Error:-url:- $url, error:- ${result.error.message} code:- ${result.error.code} "))
                    sendEditScreenEvent(UiEvent.ShowEmptyList)
                    useCase.insertErrorDataToFireStoreUseCase(
                        collectionName = collectionName,
                        documentName = "getListOfPendingKOTs,${Date()}",
                        errorData = FirebaseError(
                            errorCode = result.error.code,
                            errorMessage = result.error.message ?: "",
                            url = url,
                            ipAddress = publicIpAddress
                        )
                    )

                }
            }
        }
    }

    fun kotSearch(value: String) {
        val id = value.toInt()
        kotPendingList.removeAll {
            it.kotMasterId != id
        }
    }


    // saving device id in dataStore
    fun saveDeviceIdInDataStore(deviceId: String) {
        if (_deviceIdSate.value.isEmpty()) {
            // Log.d(TAG, "saveDeviceIdInDataStore: $deviceId")
            viewModelScope.launch {
                useCase.saveDeviceIdUseCase(deviceId = deviceId)
            }
        }
    }


    // Unipospro license activation
    fun uniLicenseActivation(deviceId: String, licenseKey: String) {

        sendUniLicenseActScreenEvent(UiEvent.ShowProgressBar)

        val url = HttpRoutes.UNI_LICENSE_ACTIVATION_URL
        val header = HttpRoutes.UNI_LICENSE_HEADER
        val licenseRequestBody = LicenseRequestBody(
            licenseKey = licenseKey,
            macId = _deviceIdSate.value.ifEmpty { deviceId },
            ipAddress = publicIpAddress
        )
        viewModelScope.launch(Dispatchers.IO) {
            useCase.uniLicenseActivationUseCase(
                url = url,
                rioLabKey = header,
                licenseRequestBody = licenseRequestBody
            ).collectLatest { result ->

                sendUniLicenseActScreenEvent(UiEvent.CloseProgressBar)

                if (result is GetDataFromRemote.Success) {
                    //val licenseType = result.data.message.licenseType
                    val licenseType = result.data.licenseType
                    val expiryDate = result.data.expiryDate
                    expiryDate?.let { ed ->
                        if (licenseType == "demo") {
                            if (!checkForLicenseExpiryDate(ed)) {
                                sendUniLicenseActScreenEvent(UiEvent.ShowSnackBar("Expired License"))
                                licenseKeyActivationError.value = "Expired License"
                                return@collectLatest
                            }
                        }
                    }
                    // sendUniLicenseActScreenEvent(UiEvent.Navigate(route = RootNavScreens.LocalRegisterScreen.route))
                    val licenceInformation = UniLicenseDetails(
                        licenseType = result.data.licenseType,
                        licenseKey = licenseKey,
                        expiryDate = result.data.expiryDate ?: ""
                    )

                    uniLicenseDetails.value = licenceInformation



                    sendUniLicenseActScreenEvent(UiEvent.ShowAlertDialog)

                    val licenseString = Json.encodeToString(licenceInformation)
                    useCase.uniLicenseSaveUseCase(uniLicenseString = licenseString)
                    viewModelScope.launch(Dispatchers.IO) {
                        useCase.insertGeneralDataToFirebaseUseCase(
                            collectionName = "UserInformation",
                            firebaseGeneralData = FirebaseGeneralData(
                                deviceId = deviceId,
                                ipAddress = publicIpAddress,
                                uniLicense = licenseKey
                            )
                        )
                    }
                }
                if (result is GetDataFromRemote.Failed) {

                    licenseKeyActivationError.value = result.error.message ?: "Error on Activation"
                    sendUniLicenseActScreenEvent(UiEvent.ShowSnackBar(message = "code:- ${result.error.code}, error:- ${result.error.message}"))

                    useCase.insertErrorDataToFireStoreUseCase(
                        collectionName = collectionName,
                        documentName = "uniLicenseActivation,${Date()}",
                        errorData = FirebaseError(
                            errorCode = result.error.code,
                            errorMessage = result.error.message ?: "Error on Activation",
                            url = url,
                            ipAddress = publicIpAddress
                        )
                    )
                }

            }
        }
    }

    // Read unipos license details
    private fun readUniLicenseKeyDetails() {
        viewModelScope.launch {
            useCase.uniLicenseReadUseCase().collectLatest { value ->
                // checking for saved license details

                // Log.d("Lath", "readUniLicenseKeyDetails: $value")
                if (value.isNotEmpty() && value.isNotBlank()) {

                    val licenseDetails = Json.decodeFromString<UniLicenseDetails>(value)

                    uniLicenseDetails.value = licenseDetails

                    // check saved license is demo
                    if (licenseDetails.licenseType == "demo" && licenseDetails.expiryDate.isNotEmpty()) {

                        // check for license expired
                        if (!checkForLicenseExpiryDate(licenseDetails.expiryDate)) {
                            // demo license expired
                            sendUniLicenseActScreenEvent(UiEvent.ShowSnackBar("Your licence is expired"))
                            checkForPublicIpAddressStatus()

                        } else {
                            // demo license not expired
                            sendSplashScreenEvent(SplashScreenEvent(UiEvent.Navigate(route = RootNavScreens.LocalRegisterScreen.route)))
                        }
                    }
                    if (licenseDetails.licenseType == "permanent") {
                        // license is permanent
                        sendSplashScreenEvent(SplashScreenEvent(UiEvent.Navigate(route = RootNavScreens.LocalRegisterScreen.route)))
                    }
                } else {
                    checkForPublicIpAddressStatus()
                }
            }
        }
    }

    private fun checkForLicenseExpiryDate(eDate: String): Boolean {

        val expDate: Date = SimpleDateFormat(
            "dd-MM-yyyy",
            Locale.getDefault()
        ).parse(eDate)!!

        return expDate >= Date()
    }


    fun setUnitActivationErrorValue(value: String) {
        licenseKeyActivationError.value = value
    }


    private fun sendUniLicenseActScreenEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            _uniLicenseActScreenEvent.send(UniLicenseActScreenEvent(uiEvent))
        }
    }


    private fun checkForPublicIpAddressStatus() {
        viewModelScope.launch {
            (1..180).forEach {
                if (publicIpAddress.isNotEmpty() && publicIpAddress.isNotBlank()) {
                    sendSplashScreenEvent(
                        SplashScreenEvent(
                            UiEvent.Navigate(route = RootNavScreens.UniLicenseActScreen.route)
                        )
                    )

                    return@launch
                }
                delay(1000L)
                if (it % 30 == 0) {
                    getIp4Address()
                }
                if (it == 180) {
                    sendSplashScreenEvent(
                        SplashScreenEvent(
                            UiEvent.ShowSnackBar("There have some error on reading Public Ip address. Please restart application")
                        )
                    )
                    return@launch
                }
            }

        }


    }


    private fun getIp4Address() {
        val url = HttpRoutes.SEE_IP4
        viewModelScope.launch {
            useCase.getIp4AddressUseCase(url = url).collectLatest { result ->

                when (result) {
                    is GetDataFromRemote.Success -> {
                        publicIpAddress = result.data
                        //Log.e(TAG, "getIp4Address: $publicIpAddress")
                    }

                    is GetDataFromRemote.Failed -> {
                        //Log.d(TAG, "getIp4Address: $result")
                        useCase.insertErrorDataToFireStoreUseCase(
                            collectionName = collectionName,
                            documentName = "getIp4Address,${Date()}",
                            errorData = FirebaseError(
                                errorCode = result.error.code,
                                errorMessage = result.error.message ?: "Error on Activation",
                                url = url,
                                ipAddress = publicIpAddress
                            )
                        )
                    }
                }

            }
        }
    }


    fun showSnackBarToShowErrorOnShowKotScreenEditButtonClicked(error: String) {
        viewModelScope.launch {
            useCase.insertErrorDataToFireStoreUseCase(
                collectionName = "editKotError",
                documentName = Date().toString(),
                errorData = FirebaseError(errorMessage = error)
            )
        }
        sendShowKotUiEvent(UiEvent.ShowSnackBar(error))
    }


}