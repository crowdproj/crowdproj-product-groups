import org.jetbrains.kotlin.gradle.tasks.KotlinNativeLink
import com.bmuschko.gradle.docker.tasks.image.Dockerfile
import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage

val ktorVersion: String by project
val serializationVersion: String by project
val datetimeVersion: String by project
val coroutinesVersion: String by project
val logbackVersion: String by project
val slf4jVersion: String by project

// ex: Converts to "io.ktor:ktor-ktor-server-netty:2.0.1" with only ktor("netty")
fun ktorServer(module: String, version: String? = this@Build_gradle.ktorVersion): Any =
    "io.ktor:ktor-server-$module:$version"
fun ktorClient(module: String, version: String? = this@Build_gradle.ktorVersion): Any =
    "io.ktor:ktor-client-$module:$version"


plugins {
    kotlin("multiplatform")
    id("io.ktor.plugin")
    id("com.bmuschko.docker-java-application")
    id("com.bmuschko.docker-remote-api")
}

application {
    mainClass.set("io.ktor.server.cio.EngineMain")
}

kotlin {
    jvm { withJava() }

    val nativeTarget = when (System.getProperty("os.name")) {
        "Linux" -> linuxX64("native")
        // Windows is currently not supported
        // Other supported targets are listed here: https://ktor.io/docs/native-server.html#targets
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    nativeTarget.apply {
        binaries {
            executable {
                entryPoint = "com.crowdproj.marketplace.product.group.app.ktor.main"
            }
        }
    }

    sourceSets {
        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                api("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("io.ktor:ktor-serialization:$ktorVersion")

                implementation(ktorClient("core"))
                implementation(ktorClient("cio"))

                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")

                implementation(ktorServer("content-negotiation"))
                implementation(ktorServer("auto-head-response"))
                implementation(ktorServer("caching-headers"))
                implementation(ktorServer("cors"))
                implementation(ktorServer("websockets"))
                implementation(ktorServer("config-yaml"))
                implementation(ktorServer("core"))
                implementation(ktorServer("cio"))
                implementation(ktorServer("auth"))

                implementation(project(":product-group-api-v1"))
                implementation(project(":product-group-common"))
                implementation(project(":product-group-mapper"))
                implementation(project(":product-group-stubs"))
                implementation(project(":product-group-log-mapper"))
                implementation(project(":product-group-api-log"))
                implementation(project(":product-group-common-logging"))
                implementation(project(":product-group-logging-kermit"))
                implementation(project(":product-group-fluentbit"))
                implementation(project(":product-group-biz"))
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(ktorServer("test-host"))
                implementation(ktorClient("content-negotiation"))
                implementation("io.ktor:ktor-client-mock:$ktorVersion")
            }
        }

        @Suppress("UNUSED_VARIABLE")
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }

        @Suppress("UNUSED_VARIABLE")
        val jvmMain by getting {
            dependencies {
                implementation("ch.qos.logback:logback-classic:$logbackVersion")
                implementation("ch.qos.logback:logback-access:$logbackVersion")

                implementation("org.slf4j:slf4j-api:$slf4jVersion")
            }
        }

        @Suppress("UNUSED_VARIABLE")
        val nativeTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

tasks {
    val linkReleaseExecutableNative by getting(KotlinNativeLink::class)

    val dockerDockerfile by creating(Dockerfile::class) {
        group = "docker"
        from("ubuntu:22.02")
        doFirst {
            copy {
                from(linkReleaseExecutableNative.binary.outputFile)
                into("${this@creating.temporaryDir}/app")
            }
        }
        copyFile("app", "/app")
        entryPoint("/app")
    }
    create("dockerBuildNativeImage", DockerBuildImage::class) {
        group = "docker"
        dependsOn(dockerDockerfile)
        images.add("${project.name}:${project.version}")
    }
}
