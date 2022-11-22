package com.gulfappdeveloper.project3.domain.remote.get.product

@kotlinx.serialization.Serializable
data class MultiSizeProduct(
    val barcode: String,
    val id: Int,
    val name: String,
    val productId: Int,
    val rate: Float
)