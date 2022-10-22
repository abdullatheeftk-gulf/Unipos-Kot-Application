package com.gulfappdeveloper.project3.domain.remote.get.dine_in

import kotlinx.serialization.Serializable

@Serializable
data class Table(
    val id: Int,
    val noOfSeats: Int,
    val occupied: Int,
    val sectionId: Int,
    val tableName: String
)