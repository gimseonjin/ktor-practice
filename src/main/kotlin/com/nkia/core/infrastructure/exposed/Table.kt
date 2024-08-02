package com.nkia.core.infrastructure.exposed

import com.nkia.common.enumList
import com.nkia.menu.domain.MenuCategory
import com.nkia.user.domain.UserRole
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.datetime

object MenuTable : LongIdTable(name = "cafe_menu") {
    val name = varchar("menu_name", length = 50)
    val price = integer("price")
    val category = enumerationByName("category", 10, MenuCategory::class)
    val image = text("image")
}

object UserTable : LongIdTable(name = "cafe_user") {
    val nickname = varchar("nickname", length = 50)
    val password = varchar("password", length = 100)
    val roles = enumList("roles", UserRole::class.java, 20)
}

object OrderTable : LongIdTable(name = "cafe_order") {
    val orderCode = varchar("order_code", length = 50)
    val cafeUserId = reference("cafe_user_id", UserTable)
    val cafeMenuId = reference("cafe_menu_id", MenuTable)
    val price = integer("price")
    val status = enumerationByName("status", 10, MenuCategory::class)
    val orderedAt = datetime("ordered_at")
}