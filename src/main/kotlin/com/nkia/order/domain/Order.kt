package com.nkia.order.domain

import com.nkia.common.BaseModel
import com.nkia.common.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Order(
    val orderCode: String,
    val cafeMenuId: Long,
    val cafeUserId: Long,
    val price: Int,
    var status: OrderStatus,
    @Serializable(with = LocalDateTimeSerializer::class)
    val orderedAt: LocalDateTime,
) : BaseModel()