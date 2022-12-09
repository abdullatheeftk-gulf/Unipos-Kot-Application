package com.gulfappdeveloper.project3.domain.datastore

@kotlinx.serialization.Serializable
data class UniLicenseDetails(
    val licenseKey: String,
    val licenseType: String,
    val expiryDate: String
)
