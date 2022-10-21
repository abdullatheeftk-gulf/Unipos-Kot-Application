package com.gulfappdeveloper.project3.navigation.root

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gulfappdeveloper.project3.data.remote.HttpRoutes
import com.gulfappdeveloper.project3.domain.remote.get.GetDataFromRemote
import com.gulfappdeveloper.project3.presentation.presentation_util.UiEvent
import com.gulfappdeveloper.project3.presentation.screens.splash_screen.util.SplashScreenEvent
import com.gulfappdeveloper.project3.usecases.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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

    var operationCount = mutableStateOf(0)
        private set

    var baseUrl = mutableStateOf(HttpRoutes.BASE_URL)
        private set

    var message = mutableStateOf("")
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
                        navigateToNextScreenWithDelay(route = RootNavScreens.HomeScreen.route)
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

    private fun navigateToNextScreenWithDelay(route:String){
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
}