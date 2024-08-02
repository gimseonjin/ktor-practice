package com.nkia.config

import com.nkia.common.enumList
import com.nkia.core.infrastructure.exposed.MenuTable
import com.nkia.core.infrastructure.exposed.OrderTable
import com.nkia.core.infrastructure.exposed.UserTable
import com.nkia.menu.domain.MenuCategory
import com.nkia.user.domain.UserRole
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import org.h2.tools.Server
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.transactions.transaction
import javax.sql.DataSource

fun Application.configureDatabase() {
    configureH2()
    connectDatabase()
    initData()
}

fun Application.configureH2() {
    val h2Server = Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092")
    environment.monitor.subscribe(ApplicationStarted) { application ->
        h2Server.start()
        application.environment.log.info("H2 server started. ${h2Server.url}")
    }

    environment.monitor.subscribe(ApplicationStopped) { application ->
        h2Server.stop()
        application.environment.log.info("H2 server stopped. ${h2Server.url}")
    }
}


private fun connectDatabase() {
    val config =
        HikariConfig().apply {
            jdbcUrl = "jdbc:h2:mem:cafedb"
            driverClassName = "org.h2.Driver"
            validate()
        }

    val dataSource: DataSource = HikariDataSource(config)
    Database.connect(dataSource)
}

private fun initData() {
    transaction {
        addLogger(StdOutSqlLogger)

        SchemaUtils.create(
            MenuTable,
            UserTable,
            OrderTable
        )
    }
}
