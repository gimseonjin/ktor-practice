package com.nkia.core.infrastructure.exposed

import com.nkia.common.BaseModel
import com.nkia.common.BaseRepository
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateStatement
import org.jetbrains.exposed.sql.transactions.transaction


interface ExposedBaseRepository<TABLE : LongIdTable, DOMAIN : BaseModel> : BaseRepository<DOMAIN> {
    val table: TABLE
    fun create(domain: DOMAIN): DOMAIN = dbQuery {
        val id = table.insertAndGetId(toRow(domain))
        domain.id = id.value
        domain
    }

    override fun save(domain: DOMAIN): DOMAIN {
        return if (domain.id == null) {
            create(domain)
        } else {
            update(domain)
        }
    }

    override fun findAll(): List<DOMAIN> = dbQuery {
        table.selectAll().map { toDomain(it) }
    }

    override fun findById(id: Long): DOMAIN? = dbQuery {
        table.selectAll().where { table.id eq id }
            .map { toDomain(it) }
            .singleOrNull()
    }

    fun update(domain: DOMAIN): DOMAIN = dbQuery {
        table.update(
            where = { table.id eq domain.id },
            body = updateRow(domain)
        )
        domain
    }

    override fun deleteById(id: Long) = dbQuery {
        table.deleteWhere { table.id eq id }
        return@dbQuery
    }

    fun toRow(domain: DOMAIN): TABLE.(InsertStatement<EntityID<Long>>) -> Unit
    fun toDomain(row: ResultRow): DOMAIN
    fun updateRow(domain: DOMAIN): TABLE.(UpdateStatement) -> Unit

    private fun <T> dbQuery(block: () -> T): T = transaction {
        addLogger(StdOutSqlLogger)
        block()
    }
}