package com.gulfappdeveloper.project3.domain.remote.get.dine_in

import kotlinx.serialization.Serializable

@Serializable
data class Section(
    val id: Int,
    val name: String
)