package com.gulfappdeveloper.project3.domain.remote.get.product

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id:Int,
    val name:String,
    val categoryId:Int,
    val rate:Float,
    val barcode:String,
    @SerialName("multisizecount")
    val multiSizeCount:Int
)