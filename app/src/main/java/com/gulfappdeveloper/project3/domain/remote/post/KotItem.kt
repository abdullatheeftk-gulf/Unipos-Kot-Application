package com.gulfappdeveloper.project3.domain.remote.post

import kotlinx.serialization.Serializable

@Serializable
data class KotItem(
    val barcode: String,
    val fK_KOTMasterId: Int=1,
    var itemNote: String="",
    val kotSettingsId: Int=1,
    var netAmount: Float,
    val productId: Int,
    val productName:String,
    var quantity: Float,
    val rate: Float
)