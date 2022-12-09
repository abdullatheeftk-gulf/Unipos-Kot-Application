package com.gulfappdeveloper.project3.domain.remote.get.product

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: Int,
    val name: String
)
