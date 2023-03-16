rootProject.name = "crowdproj-product-groups"

pluginManagement {
    val kotlinVersion: String by settings
    val kotestVersion: String by settings
    val openapiVersion: String by settings
    val nexusStagingVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false

        id("org.openapi.generator") version openapiVersion apply false
        id("io.codearte.nexus-staging") version nexusStagingVersion
    }
}



include("product-group-api-v1")
include("product-group-common")
include("product-group-mapper")
