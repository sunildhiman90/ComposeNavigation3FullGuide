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

        //for snapshot builds. Currently for navigation 3
        maven {
            url = uri("https://androidx.dev/snapshots/builds/13556278/artifacts/repository")
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        //for snapshot builds. Currently for navigation 3
        maven {
            url = uri("https://androidx.dev/snapshots/builds/13556278/artifacts/repository")
        }
    }
}

rootProject.name = "ComposeNavigation3FullGuide"
include(":app")
