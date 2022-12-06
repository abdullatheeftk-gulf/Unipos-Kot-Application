package com.gulfappdeveloper.project3.presentation.screens.url_set_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gulfappdeveloper.project3.data.remote.HttpRoutes
import com.gulfappdeveloper.project3.domain.firebase.FirebaseError
import com.gulfappdeveloper.project3.domain.remote.get.GetDataFromRemote
import com.gulfappdeveloper.project3.navigation.root.RootNavScreens
import com.gulfappdeveloper.project3.presentation.presentation_util.UiEvent
import com.gulfappdeveloper.project3.usecases.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

private const val TAG = "SetBaseUrlScreenViewMod"
@HiltViewModel
class SetBaseUrlScreenViewModel @Inject constructor(
    private val useCase: UseCase
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun setBaseUrl(value: String) {
        Log.d(TAG, "setBaseUrl: ")
        viewModelScope.launch {
            useCase.saveBaseUrlUseCase(baseUrl = value)
            getWelcomeMessage(url = value + HttpRoutes.WELCOME_MESSAGE)
        }
    }

    private fun getWelcomeMessage(url: String) {
        sendUiEvent(UiEvent.ShowProgressBar)
        viewModelScope.launch {
            useCase.getWelcomeMessageUseCase(url = url).collectLatest { result ->
                sendUiEvent(UiEvent.CloseProgressBar)
                if (result is GetDataFromRemote.Success) {
                    sendUiEvent(UiEvent.Navigate(RootNavScreens.LocalRegisterScreen.route))
                }
                if (result is GetDataFromRemote.Failed) {
                    sendUiEvent(UiEvent.ShowSnackBar(message = "This Server with $url is down"))
                    useCase.insertErrorDataToFireStoreUseCase(
                        collectionName = "ErrorDataDev",
                        documentName = "UrlSettingScreen,getWelcomeMessage,${Date()}",
                        errorData = FirebaseError(
                            errorCode = result.error.code,
                            errorMessage = result.error.message ?: "",
                            url = url,
                            ipAddress = ""
                        )
                    )
                }
            }
        }
    }

    fun onErrorUrl(url: String) {
        sendUiEvent(UiEvent.ShowSnackBar(message = "Typed url $url is in wrong format"))
    }


    private fun sendUiEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }
}