package com.gulfappdeveloper.project3

import android.content.Context
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.gulfappdeveloper.project3.navigation.root.RootNavGraph
import com.gulfappdeveloper.project3.ui.theme.Project3Theme
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "MainActivity"
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val deviceId = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)

      //  Log.d("MainActivity", "onCreate: ${BuildConfig.BUILD_TYPE}")
       // Log.e(TAG, "onCreate: ${!isLicenseExpired("08-01-2023")}", )
        setContent {
            Project3Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    val navHostController = rememberNavController()

                    RootNavGraph(
                        hideKeyboard = {
                            hideSoftKeyboard()
                        },
                        onScanButtonClicked = { },
                        changeStatusBarColor = { color ->
                            window.statusBarColor = ContextCompat.getColor(this, color)
                        },
                        navHostController = navHostController,
                        deviceId = deviceId,
                    )
                }
            }
        }
    }

    private fun hideSoftKeyboard() {
        this.currentFocus?.let { view ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /*private fun isLicenseExpired(eDate: String): Boolean {

        val expDate: Date = SimpleDateFormat(
            "dd-MM-yyyy",
            Locale.getDefault()
        ).parse(eDate)!!

        return expDate >= Date()
    }*/

}




