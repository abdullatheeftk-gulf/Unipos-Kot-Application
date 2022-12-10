package com.gulfappdeveloper.project3.domain.firebase

import androidx.annotation.Keep
import java.util.*
@Keep
data class FirebaseGeneralData(
    val manufacture: String = android.os.Build.MANUFACTURER ?: "nil",
    val model: String = android.os.Build.MODEL ?: "nil",
    val device: String = android.os.Build.DEVICE ?: "nil",
    val deviceId: String,
    val dateAndTime: String = Date().toString(),
    val time: Long = Date().time,
    val ipAddress: String = "",
)
