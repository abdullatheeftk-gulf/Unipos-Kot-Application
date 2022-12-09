package com.gulfappdeveloper.project3

import android.content.Context
import android.os.Bundle
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.creative.ipfyandroid.Ipfy
import com.creative.ipfyandroid.IpfyClass
import com.gulfappdeveloper.project3.navigation.root.RootNavGraph
import com.gulfappdeveloper.project3.ui.theme.Project3Theme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val deviceId = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
        Ipfy.init(this, IpfyClass.IPv4)

        setContent {
            Project3Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    // Get Ip address
                    var publicIpAddress:String by remember {
                        mutableStateOf("")
                    }

                    Ipfy.getInstance().getPublicIpObserver().observe(this){
                        publicIpAddress = it.currentIpAddress ?: ""
                    }

                   // Log.d(TAG, "onCreate: $publicIpAddress")
                    
                    
                    
                    val navHostController = rememberNavController()

                   // if (publicIpAddress.isNotEmpty() && publicIpAddress.isNotBlank()) {
                        RootNavGraph(
                            hideKeyboard = {
                                hideSoftKeyboard()
                            },
                            onScanButtonClicked = {  },
                            changeStatusBarColor = { color ->
                                window.statusBarColor = ContextCompat.getColor(this, color)
                            },
                            navHostController = navHostController,
                            deviceId = deviceId,
                            publicIpAddress = publicIpAddress
                        )
                        //CrashTest()
                        
                   // }
                    /*else{
                        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
                        Scaffold() {
                            it.calculateTopPadding()
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Top,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Spacer(modifier = Modifier.height(150.dp))
                                Image(
                                    painter = painterResource(id = R.drawable.manthy),
                                    contentDescription = null,
                                    modifier = Modifier.size(250.dp)
                                )
                            }
                        }
                    }*/
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
    
}


/*@Composable
fun CrashTest() {
   
    Scaffold() {
        it.calculateTopPadding()
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            Button(onClick = { throw RuntimeException("Test ${Date()}") }) {
                Text(text = "Test")
            }
        }
    }
}*/

