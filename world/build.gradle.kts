plugins {
    kotlin("jvm")
    application
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

apply<org.jire.gradle.kilim.KilimPlugin>()

application {
    mainClass.set("io.ruin.Server")

    val jvmArgs = mutableListOf("-Xmx8g", "-Xms8g")
  //  jvmArgs.add("-agentpath:C:/JRebel/lib/jrebel64.dll")

    applicationDefaultJvmArgs = jvmArgs
}

repositories {
    maven("https://m2.dv8tion.net/releases")
    flatDir {
        dirs("libs")
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":update"))
    implementation(
        files("libs/everythingrs-api.jar")
    )

    implementation("net.dv8tion:JDA:4.3.0_327") {
        exclude(module = "opus-java")
    }

    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")

    implementation("com.typesafe:config:1.4.1")
    implementation("org.jsoup:jsoup:1.14.2")
    implementation("io.undertow:undertow-core:2.2.10.Final")
    implementation("com.mashape.unirest:unirest-java:1.4.9")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.12.5")

    lombok()
}

tasks {
    compileKotlin {
        kotlinOptions {
            incremental = true
            jvmTarget = "11"
            suppressWarnings = true
        }
    }
    compileTestKotlin {
        enabled = false
        kotlinOptions {
            incremental = true
            jvmTarget = "11"
            suppressWarnings = true
        }
    }

    jar {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }
    processResources {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }
    run.invoke {
        dependsOn(named<org.jire.gradle.kilim.WeaveTask>("weave"))
    }
}