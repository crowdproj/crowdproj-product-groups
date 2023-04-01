rootProject.name = "crowdproj-product-groups"

pluginManagement {
    val kotlinVersion: String by settings
    val ktorPluginVersion: String by settings
    val openapiVersion: String by settings
    val bmuschkoVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion apply false
        id("io.ktor.plugin") version ktorPluginVersion apply false
        id("org.openapi.generator") version openapiVersion apply false
        id("com.bmuschko.docker-java-application") version bmuschkoVersion apply false
        id("com.bmuschko.docker-remote-api") version bmuschkoVersion apply false
    }
}

include("api")
include("common")
include("mappers")
include("stubs")
include("app-ktor")
include("app-kafka")
include("biz")
