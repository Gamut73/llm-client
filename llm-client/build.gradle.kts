plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    id("com.vanniktech.maven.publish") version "0.36.0"
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
    implementation(libs.google.genai)
    api(project(":ollama-client"))
    implementation(libs.kotlinx.coroutines.core)
}

mavenPublishing {
    coordinates("io.github.gamut73", "llm_client", "1.1.1")

    pom {
        name.set("LLM Client Library")
        description.set("Kotlin library for different llm implementations")
        inceptionYear.set("2025")
        url.set("https://github.com/Gamut73/llm-client")

        licenses {
            license {
                name.set("MIT")
                url.set("https://opensource.org/licenses/MIT")
            }
        }

        developers {
            developer {
                id.set("Gamut73")
                name.set("Fitzcaraldo")
                email.set("the.28th.artificery@gmail.com")
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