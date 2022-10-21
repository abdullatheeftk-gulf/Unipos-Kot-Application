package com.gulfappdeveloper.project3.domain.remote.post

import kotlinx.serialization.Serializable

@Serializable
data class KotItem(
    val barcode: String,
    val fK_KOTMasterId: Int=1,
    val itemNote: String="",
    val kotSettingsId: Int=1,
    val netAmount: Float,
    val productId: Int,
    val quantity: Float,
    val rate: Float
)