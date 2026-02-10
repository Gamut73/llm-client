pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "LLM Client"
include(":android-sample:app")
include(":llm-client")
include(":kotlin-sample")
include(":ollama-client")

gradle.beforeProject {
    extra["libraryGroupId"] = "io.github.gamut73"
    extra["libraryVersion"] = "1.2.5"
    extra["developerId"] = "Gamut73"
    extra["developerName"] = "Fitzcaraldo"
    extra["developerEmail"] = "the.28th.artificery@gmail.com"
}
