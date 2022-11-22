package com.gulfappdeveloper.project3.domain.remote.get.kot_list

import kotlinx.serialization.SerialName
import java.util.Date

@kotlinx.serialization.Serializable
data class UserOrder(
    val cookingTime: Double,
    val dateAndTime: String,
    val fK_BranchId: Int,
    val fK_CustomerId: Int,
    val fK_FYearId: Int,
    val fK_KOTPrinterId: Int,
    val fK_StaffId: Int,
    val fK_UserId: Int,
    val kotMasterId: Int,
    val notes: String?,
    val orderType: String,
    val remarks: String?,
    val serialNo: Int,
    val tableId: Int?,
    val terminal: String,
    @SerialName("tockenNo")
    val tokenNo: Int,
    val totalAmount: Double,
    val totalQuantity: Double
)