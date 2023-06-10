package com.crowdproj.marketplace.product.group.app.ktor.plugins

import com.crowdproj.marketplace.product.group.app.ktor.config.ConfigPaths
import com.crowdproj.marketplace.product.group.app.ktor.config.GremlinConfig
import com.crowdproj.marketplace.product.group.common.repo.IPrgrpRepository
import com.crowdproj.marketplace.product.group.repo.gremlin.PrgrpGremlinRepo
import com.crowdproj.marketplace.product.group.repo.inmemory.PrgrpInMemoryRepo
import io.ktor.server.application.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes


actual fun Application.getDatabaseConf(type: PrgrpDbType): IPrgrpRepository {
    val dbSettingPath = "${ConfigPaths.repository}.${type.confName}"
    val dbSetting = environment.config.propertyOrNull(dbSettingPath)?.getString()?.lowercase()
    return when (dbSetting) {
        "in-memory", "inmemory", "memory", "mem" -> initInMemory()
        "arcade", "arcadedb", "graphdb", "gremlin", "g", "a" -> initGremliln()
        else -> throw IllegalArgumentException(
            "$dbSettingPath must be set in application.yml to one of: " +
                    "'inmemory', 'postgres', 'cassandra', 'gremlin'"
        )
    }
}

private fun Application.initGremliln(): IPrgrpRepository {
    val config = GremlinConfig(environment.config)
    return PrgrpGremlinRepo(
        hosts = config.host,
        port = config.port,
        user = config.user,
        pass = config.pass,
        enableSsl = config.enableSsl,
    )
}

private fun Application.initInMemory(): IPrgrpRepository {
    val ttlSetting = environment.config.propertyOrNull("db.prod")?.getString()?.let {
        Duration.parse(it)
    }
    return PrgrpInMemoryRepo(ttl = ttlSetting ?: 10.minutes)
}