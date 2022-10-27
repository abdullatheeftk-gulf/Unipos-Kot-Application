package com.gulfappdeveloper.project3.domain.remote.get

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TableOrder(
    val chairCount: Int?,
    val fK_KOTInvoiceId: Int,
    val fK_TableId: Int,
    val id: Int,
    val isBooked: Boolean,
    @SerialName("isReseved")
    val isReserved: Boolean,
    val orderName: String,
    val remarks: String?
)