package com.nkia.core.infrastructure.exposed

import com.nkia.menu.domain.Menu
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateStatement

class MenuRepositoryImpl(
    override val table: MenuTable
) : ExposedBaseRepository<MenuTable, Menu> {

    override fun toRow(domain: Menu): MenuTable.(InsertStatement<EntityID<Long>>) -> Unit {
        return {
            it[name] = domain.name
            it[price] = domain.price
            it[category] = domain.category
            it[image] = domain.image
        }
    }

    override fun toDomain(row: ResultRow): Menu {
        return Menu(
            name = row[table.name],
            price = row[table.price],
            category = row[table.category],
            image = row[table.image]
        ).apply { id = row[table.id].value }
    }

    override fun updateRow(domain: Menu): MenuTable.(UpdateStatement) -> Unit {
        return {
            it[name] = domain.name
            it[price] = domain.price
            it[category] = domain.category
            it[image] = domain.image
        }
    }
}