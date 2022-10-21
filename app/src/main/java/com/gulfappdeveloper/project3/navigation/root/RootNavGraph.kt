package com.gulfappdeveloper.project3.navigation.root

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gulfappdeveloper.project3.R
import com.gulfappdeveloper.project3.presentation.screens.home_screen.HomeScreen
import com.gulfappdeveloper.project3.presentation.screens.splash_screen.SplashScreen
import com.gulfappdeveloper.project3.presentation.screens.url_set_screen.SetBaseUrlScreen

@Composable
fun RootNavGraph(
    rootViewModel: RootViewModel = hiltViewModel(),
    hideKeyBoard: () -> Unit,
    onScanButtonClicked: () -> Unit,
    changeStatusBarColor: (Int) -> Unit,
    navHostController: NavHostController
) {

    NavHost(
        navController = navHostController,
        startDestination = RootNavScreens.SplashScreen.route
    ) {

        composable(route = RootNavScreens.SplashScreen.route) {
            changeStatusBarColor(R.color.white)
            SplashScreen(
                navHostController = navHostController,
                rootViewModel = rootViewModel
            )
        }
        composable(route = RootNavScreens.UrlSetScreen.route) {
            changeStatusBarColor(R.color.my_prime_color)
            SetBaseUrlScreen(
                rootViewModel = rootViewModel,
                navHostController = navHostController,
                hideKeyboard = hideKeyBoard
            )
        }
        composable(route = RootNavScreens.HomeScreen.route){
            changeStatusBarColor(R.color.white)
            HomeScreen(
                navHostController = navHostController,
                onScanButtonClicked = onScanButtonClicked
            )
        }
    }

}