plugins {
    java
    id("org.zeroturnaround.gradle.jrebel") version "1.1.11" apply false
    id("com.github.johnrengelman.shadow") version "7.0.0" apply false
}

subprojects {
    apply(plugin = "java")

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("org.slf4j:slf4j-api:1.7.32")
        implementation("org.slf4j:slf4j-simple:1.7.32")

        implementation("com.google.guava:guava:30.1.1-jre")
        implementation("io.netty:netty-all:4.1.72.Final")

        implementation("org.json:json:20210307")
        implementation("com.google.code.gson:gson:2.8.8")
        implementation("com.googlecode.json-simple:json-simple:1.1.1")

        implementation("com.j256.two-factor-auth:two-factor-auth:1.3")
        implementation("net.sf.jopt-simple:jopt-simple:5.0.4")
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    tasks {
        test {
            enabled = false
        }
        compileJava {
            options.isWarnings = false
            options.isDeprecation = false
        }
    }

}