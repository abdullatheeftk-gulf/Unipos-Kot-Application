package com.gulfappdeveloper.project3.navigation.root

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.gulfappdeveloper.project3.data.remote.HttpRoutes
import com.gulfappdeveloper.project3.domain.remote.get.GetDataFromRemote
import com.gulfappdeveloper.project3.presentation.presentation_util.UiEvent
import com.gulfappdeveloper.project3.usecases.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "RootViewModel2 "

@HiltViewModel
class RootViewModel2  @Inject constructor(private val useCase: UseCase) :
    RootViewModel(useCase = useCase) {

    private val _localRegisterEvent = Channel<UiEvent>()
    val localRegisterEvent = _localRegisterEvent.receiveAsFlow()


    init {
        Log.i(TAG, "roor2: ")
        readSerialNo()
    }

    private fun readSerialNo(){
        viewModelScope.launch {
            useCase.readSerialNoCountUseCase().collectLatest {
                serialNo.value = it
            }
        }
    }


    fun onRegisterLocally(baseUrl: String, password: String) {

        sendLocalRegisterEvent(UiEvent.ShowProgressBar)

        viewModelScope.launch(Dispatchers.IO) {
            useCase.registerUserUseCase(url = baseUrl + HttpRoutes.LOGIN + password)
                .collectLatest { result ->

                    sendLocalRegisterEvent(UiEvent.CloseProgressBar)

                    if (result is GetDataFromRemote.Success) {
                        val user = result.data
                        updateSerialNo()
                        fKUserId.value = user.userId
                        sendLocalRegisterEvent(UiEvent.Navigate(route = RootNavScreens.HomeScreen.route))
                    }
                    if (result is GetDataFromRemote.Failed) {
                        Log.e(TAG, "onRegisterLocally: ${result.error} ")
                        sendLocalRegisterEvent(UiEvent.ShowSnackBar(message = "There Have Some Error :- ${result.error.message}"))
                    }
                }
        }

    }

    private fun updateSerialNo(){
        viewModelScope.launch(Dispatchers.IO) {
            useCase.updateSerialNoUseCase()
        }
    }


    private fun sendLocalRegisterEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            _localRegisterEvent.send(uiEvent)
        }
    }

    fun onErrorOnPassword() {
        sendLocalRegisterEvent(UiEvent.ShowSnackBar(message = "Password not entered"))
    }


}