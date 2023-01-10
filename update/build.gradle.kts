plugins {
    application
    id("com.github.johnrengelman.shadow") apply false
}

dependencies {
    implementation(project(":core"))
    implementation("it.unimi.dsi:fastutil:8.5.6")

    lombok()
}

application {
    mainClass.set("io.ruin.update.Server")
}