rootProject.name = "crowdproj-product-groups"

pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion apply false

        id("org.openapi.generator") version openapiVersion apply false
    }
}

include("api")
