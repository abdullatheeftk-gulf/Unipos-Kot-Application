package com.gulfappdeveloper.project3.presentation.presentation_util

sealed class UiEvent {
    object ShowProgressBar : UiEvent()
    object CloseProgressBar : UiEvent()
    object ShowAlertDialog : UiEvent()
    object CloseAlertDialog : UiEvent()
    data class ShowToastMessage(val message: String) : UiEvent()
    data class ShowSnackBar(val message: String) : UiEvent()
    data class Navigate(val route: String) : UiEvent()
    object ShowButton1 : UiEvent()
    object ShowEmptyList : UiEvent()
    object ShowList : UiEvent()
    object HideKeyboard : UiEvent()
}
