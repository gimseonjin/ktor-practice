package com.nkia.menu.domain

import com.nkia.common.BaseModel
import kotlinx.serialization.Serializable

@Serializable
data class Menu(
    val name: String,
    val price: Int,
    val category: MenuCategory,
    val image: String
) : BaseModel()