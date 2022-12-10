package com.gulfappdeveloper.project3.domain.firebase

import androidx.annotation.Keep
import java.util.*
@Keep
data class FirebaseError(
    val model: String = android.os.Build.MODEL ?: "nil",
    val manufacturer: String = android.os.Build.MANUFACTURER ?: "nil",
    val device: String = android.os.Build.DEVICE ?: "nil",
    val dateAndTime: String = Date().toString(),
    val time: Long = Date().time,
    val ipAddress: String = "",
    val errorCode: Int = 0,
    val errorMessage: String,
    val url: String = "",
)