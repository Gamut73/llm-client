val libraryGroupId: String by extra
val libraryVersion: String by extra
val developerId: String by extra
val developerName: String by extra
val developerEmail: String by extra

plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    kotlin("plugin.serialization") version "2.0.20"
    id("com.vanniktech.maven.publish")
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
}

mavenPublishing {
    coordinates(libraryGroupId, "ollama_client", libraryVersion)

    pom {
        name.set("Kotlin OLLAMA Client Library")
        description.set("Kotlin library for connecting to OLLAMA server")
        inceptionYear.set("2026")
        url.set("https://github.com/Gamut73/llm-client")

        licenses {
            license {
                name.set("MIT")
                url.set("https://opensource.org/licenses/MIT")
            }
        }

        developers {
            developer {
                id.set(developerId)
                name.set(developerName)
                email.set(developerEmail)
            }
        }

        scm {
            url.set("https://github.com/Gamut73/llm-client")
            connection.set("scm:git:git://github.com/Gamut73/llm-client.git")
            developerConnection.set("scm:git:ssh://git@github.com/Gamut73/llm-client.git")
        }
    }

    publishToMavenCentral()
    signAllPublications()
}
