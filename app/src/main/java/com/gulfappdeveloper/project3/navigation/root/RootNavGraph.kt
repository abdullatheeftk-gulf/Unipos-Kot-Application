package com.gulfappdeveloper.project3.navigation.root

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gulfappdeveloper.project3.R
import com.gulfappdeveloper.project3.presentation.screens.auth_screens.local_auth_screen.LocalRegister
import com.gulfappdeveloper.project3.presentation.screens.dine_in_screen.DineInScreen
import com.gulfappdeveloper.project3.presentation.screens.home_screen.HomeScreen
import com.gulfappdeveloper.project3.presentation.screens.product_display_screen.ProductDisplayScreen
import com.gulfappdeveloper.project3.presentation.screens.review_screen.ReviewScreen
import com.gulfappdeveloper.project3.presentation.screens.splash_screen.SplashScreen
import com.gulfappdeveloper.project3.presentation.screens.table_selection_screen.TableSelectionScreen
import com.gulfappdeveloper.project3.presentation.screens.url_set_screen.SetBaseUrlScreen

@Composable
fun RootNavGraph(
    rootViewModel: RootViewModel = hiltViewModel(),
    deviceId: String,
    hideKeyboard: () -> Unit,
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
                hideKeyboard = hideKeyboard
            )
        }
        composable(route = RootNavScreens.LocalRegisterScreen.route) {
            changeStatusBarColor(R.color.my_prime_color)
            LocalRegister(
                rootViewModel = rootViewModel,
                navHostController = navHostController,
                hideKeyboard = hideKeyboard
            )
        }
        composable(route = RootNavScreens.HomeScreen.route) {
            changeStatusBarColor(R.color.white)
            HomeScreen(
                navHostController = navHostController,
                onScanButtonClicked = onScanButtonClicked,
                rootViewModel = rootViewModel
            )
        }

        composable(route = RootNavScreens.DineInScreen.route) {
            changeStatusBarColor(R.color.my_prime_color)
            DineInScreen(
                navHostController = navHostController,
                rootViewModel = rootViewModel,
                hideKeyboard = hideKeyboard
            )

        }

        composable(route = RootNavScreens.TableSelectionScreen.route){
            changeStatusBarColor(R.color.my_prime_color)
            TableSelectionScreen(
                navHostController = navHostController,
                rootViewModel = rootViewModel
            )
        }

        composable(route = RootNavScreens.ProductDisplayScreen.route) {
            changeStatusBarColor(R.color.my_prime_color)
            ProductDisplayScreen(
                navHostController = navHostController,
                rootViewModel = rootViewModel
            )
        }
        composable(route = RootNavScreens.ReviewScreen.route) {
            changeStatusBarColor(R.color.my_prime_color)
            ReviewScreen(
                navHostController = navHostController,
                rootViewModel = rootViewModel,
                deviceId = deviceId
            )
        }
    }

}