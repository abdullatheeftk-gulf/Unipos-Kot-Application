package com.gulfappdeveloper.project3.navigation.root

sealed class RootNavScreens(val route: String) {
    object SplashScreen : RootNavScreens("splash_screen")
    object UrlSetScreen : RootNavScreens("url_set_screen")
    object LocalRegisterScreen : RootNavScreens("local_register_screen")
    object HomeScreen : RootNavScreens("home_screen")
    object DineInScreen : RootNavScreens("dine_in_screen")
    object TableSelectionScreen : RootNavScreens("table_selection_screen")
    object ProductDisplayScreen : RootNavScreens("product_display_screen")
    object ReviewScreen : RootNavScreens("review_screen")
    object EditingScreen : RootNavScreens("editing_screen")
    object ShowKotScreen : RootNavScreens("show_kot_screen")
    object SettingsScreen : RootNavScreens("setting_screen")
}
