plugins {
    application
    id("com.github.johnrengelman.shadow") version "7.0.0"
}
repositories {
    maven("https://m2.dv8tion.net/releases")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":world"))
    implementation("net.dv8tion:JDA:4.3.0_327") {
        exclude(module = "opus-java")
    }
    lombok()
}

application {
    mainClass.set("io.ruin.central.CentralServer")
}