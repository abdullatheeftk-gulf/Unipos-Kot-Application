package com.gulfappdeveloper.project3

import android.content.Context
import android.os.Bundle
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

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Project3Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navHostController = rememberNavController()
                    RootNavGraph(
                        hideKeyBoard = {
                            hideSoftKeyBoard()
                        },
                        onScanButtonClicked = { /*TODO*/ },
                        changeStatusBarColor = {color->
                            window.statusBarColor = ContextCompat.getColor(this, color)
                        },
                        navHostController = navHostController
                    )
                }
            }
        }
    }

    private fun hideSoftKeyBoard() {
        this.currentFocus?.let { view ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}

