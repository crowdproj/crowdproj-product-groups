plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm {}
    linuxX64 {}

    sourceSets {
        val coroutinesVersion: String by project

        all { languageSettings.optIn("kotlin.RequiresOptIn") }

        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting {
            dependencies {
                val kotlinCorVersion: String by project
                implementation("com.crowdproj:kotlin-cor:$kotlinCorVersion")
                implementation(kotlin("stdlib-common"))

                implementation(project(":product-group-common"))
                implementation(project(":product-group-logging-common"))
                implementation(project(":product-group-stubs"))
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))

                implementation(project(":product-group-logging-common"))

                implementation(project(":product-group-repo-stubs"))
                implementation(project(":product-group-repo-tests"))
                implementation(project(":product-group-repo-in-memory"))

                api("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}
