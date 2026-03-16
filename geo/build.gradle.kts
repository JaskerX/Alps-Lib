plugins {
    id("buildlogic.java-conventions")
    alias(libs.plugins.freefair.lombok)
}

dependencies {
    compileOnly(libs.org.slf4j.slf4j.api)
    compileOnly(libs.org.json.json)
}

description = "AlpsLib-Geo"
version = "1.0.0"
