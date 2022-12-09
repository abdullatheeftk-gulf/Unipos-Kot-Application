package com.gulfappdeveloper.project3.domain.remote.get.kot_cancel_privilege_checker

import kotlinx.serialization.SerialName


@kotlinx.serialization.Serializable
data class KotCancelPrivilege(
    @SerialName("isPrevilaged")
    val isPrivileged: Boolean,
    val userId: Int
)