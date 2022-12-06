package com.gulfappdeveloper.project3.domain.firebase

import java.util.*

data class FirebaseGeneralData(
    val manufacture:String = android.os.Build.MANUFACTURER ?: "nil",
    val model:String = android.os.Build.MODEL ?: "nil",
    val device:String = android.os.Build.DEVICE ?: "nil",
    val dateAndTime:String = Date().toString(),
    val time:Long = Date().time,
    val ipAddress:String = "",

    )