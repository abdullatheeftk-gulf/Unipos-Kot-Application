package com.gulfappdeveloper.project3.domain.remote.post

import kotlinx.serialization.Serializable

@Serializable
data class Kot(
    val chairCount: Int = 0,
    // it is user id which will get from api when login
    val fK_UserId: Int,
    val kotDetails: List<KotItem>,
    // at the time of kot generation it will be 1
    val kotMasterId: Int,
    // kot notes which can add on Reviewscreen
    var notes: String? = "",
    // order name, it will be empty for take away and it is adding at dine in order
    val orderName: String? = "",
    // This function is not implemented
    var remarks: String? = "",
    // No of time login this app
    val serialNo: Int,
    val tableId: Int = 0,
    // Device id
    val terminal: String
)