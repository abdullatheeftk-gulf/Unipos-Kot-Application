package com.gulfappdeveloper.project3.domain.remote.license

@kotlinx.serialization.Serializable
data class LicenseResponse(
    val message: LicenseMessage,
    val status: Int
)
