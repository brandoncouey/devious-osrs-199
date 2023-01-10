plugins {
    `java-library`
}

apply<org.jire.gradle.kilim.KilimPlugin>()

dependencies {
    implementation("com.zaxxer:HikariCP:5.0.0")
    implementation("mysql:mysql-connector-java:8.0.26")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("commons-io:commons-io:2.11.0")
    implementation("org.apache.httpcomponents:httpclient:4.5.13")
    implementation("com.google.http-client:google-http-client-jackson2:1.40.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    implementation("io.github.classgraph:classgraph:4.8.116")

    lombok()
}

tasks {
    jar {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }
}