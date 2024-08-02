package com.nkia.user.domain

import com.nkia.common.BaseModel
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val nickname: String,
    val password: String,
    val roles: List<UserRole>,
) : BaseModel()