package com.gulfappdeveloper.project3.domain.remote.get.welcome

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WelcomeMessage(
    @SerialName("oemName")
    val message:String
)
