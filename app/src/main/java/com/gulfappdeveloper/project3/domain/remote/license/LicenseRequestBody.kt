package com.gulfappdeveloper.project3.domain.remote.license

@kotlinx.serialization.Serializable
data class LicenseRequestBody(
    val licenseKey:String,
    val macId:String,
    val ipAddress:String,
)
