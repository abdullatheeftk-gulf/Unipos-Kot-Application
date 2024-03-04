package com.gulfappdeveloper.project3.domain.remote.license

@kotlinx.serialization.Serializable
data class LicenseResponse(
    val message: String,
    val duration: Int,
    val licenseType: String,
    val status: Int,
    val startDate: String,
    val expiryDate: String?,
)
