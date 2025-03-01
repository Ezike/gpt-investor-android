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
  }
}

rootProject.name = "GPT Investor"

include(":app")

include(":remote:remote")

include(":remote:remotetest")

include(":navigation:navigation")

include(":navigation:navigationimpl")

include(":database")

include(":company:company-db")

include(":company:companyimpl")

include(":theme")

include(":home:home-db")

include(":home:homeimpl")
