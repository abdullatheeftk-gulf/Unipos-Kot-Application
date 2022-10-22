package com.gulfappdeveloper.project3.domain.remote.get.login

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val passWord: String,
    val userId: Int,
    val userName: String
)