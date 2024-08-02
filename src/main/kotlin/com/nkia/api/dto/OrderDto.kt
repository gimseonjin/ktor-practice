package com.nkia.api.dto

import com.nkia.common.LocalDateTimeSerializer
import com.nkia.order.domain.OrderStatus
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

class OrderDto{

    @Serializable
    data class CreateRequest(val menuId: Long)

    @Serializable
    data class DisplayResponse(
        val orderCode: String,
        val menuName: String,
        val customerName: String,
        val price: Int,
        var status: OrderStatus,
        @Serializable(with = LocalDateTimeSerializer::class)
        val orderedAt: LocalDateTime,
        var id: Long? = null,
    )
}
