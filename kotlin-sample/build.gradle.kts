plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    application
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
    implementation(project(":llm-client"))
    implementation(libs.kotlinx.coroutines.core)
}

application {
    mainClass.set("org.artificery.kotlin_sample.MainKt")
}

tasks {
    val fatJar = register<Jar>("fatJar") {
        dependsOn.addAll(
            listOf(
                "compileJava",
                "compileKotlin",
                "processResources",
                ":llm-client:jar",
                ":ollama-client:jar",
            )
        )
        archiveClassifier.set("llm-client-kotlin-cli-sample")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        manifest { attributes(mapOf("Main-Class" to application.mainClass)) }
        val sourcesMain = sourceSets.main.get()
        val contents = configurations.runtimeClasspath.get()
            .map { if (it.isDirectory) it else zipTree(it) } +
                sourcesMain.output
        from(contents)
    }
    build {
        dependsOn(fatJar)
    }
}