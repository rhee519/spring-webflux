plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "spring-webflux"

include("model")
include("head-office")
include("branch-office")
include("example")