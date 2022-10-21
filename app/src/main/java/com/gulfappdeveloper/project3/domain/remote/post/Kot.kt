package com.gulfappdeveloper.project3.domain.remote.post

import kotlinx.serialization.Serializable

@Serializable
data class Kot(
    val chairCount: Int=0,
    val fK_UserId: Int,
    val kotItems: List<KotItem>,
    val kotMasterId: Int =1,
    var notes: String ="",
    val orderName: String="",
    var remarks: String = "",
    val serialNo: Int,
    val tableId: Int=0,
    val terminal: String
)