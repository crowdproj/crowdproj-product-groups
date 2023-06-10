rootProject.name = "crowdproj-product-groups"

pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings
    val nexusStagingVersion: String by settings
    val ktorPluginVersion: String by settings
    val bmuschkoVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false

        id("io.ktor.plugin") version ktorPluginVersion apply false
        id("org.openapi.generator") version openapiVersion apply false
        id("io.codearte.nexus-staging") version nexusStagingVersion

        id("com.bmuschko.docker-java-application") version bmuschkoVersion apply false
        id("com.bmuschko.docker-remote-api") version bmuschkoVersion apply false
    }
}



include("product-group-api-v1")
include("product-group-common")
include("product-group-mapper")
include("product-group-app-ktor")
include("product-group-stubs")

include("product-group-api-log")
include("product-group-logging-common")
include("product-group-logging-kermit")
include("product-group-log-mapper")

include("product-group-repo-in-memory")
include("product-group-repo-tests")
include("product-group-repo-stubs")
include("product-group-repo-gremlin")

include("product-group-fluentbit")
include("product-group-biz")
