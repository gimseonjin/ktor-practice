package com.nkia.common

import kotlinx.serialization.Serializable

@Serializable
abstract class BaseModel(
    var id: Long? = null
)