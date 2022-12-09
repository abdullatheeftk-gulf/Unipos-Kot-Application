package com.gulfappdeveloper.project3.domain.remote.put

@kotlinx.serialization.Serializable
data class EditKOTBasic(
    val orderName: String,
    val chairCount: Int

)
