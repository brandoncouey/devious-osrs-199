package org.jire.gradle.kilim

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.*
import java.io.File

/**
 * @author Jire
 * @author Polish Civil
 */
class KilimPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.configure<JavaPluginExtension> {
            ConfigureScope(target, this).configure()
        }
    }

    private inner class ConfigureScope(
        project: Project,
        javaPluginExtension: JavaPluginExtension
    ) : Project by project, JavaPluginExtension by javaPluginExtension

    private fun ConfigureScope.configure() {
        val inputClasses = File(buildDir, "classes/java/main")
        val outputClasses = File(buildDir, "classes/java/kilim")
        if (!outputClasses.exists() && !outputClasses.mkdirs()) {
            throw IllegalStateException("Couldn't create outputClasses directory $outputClasses")
        }

        configurations.create("kilimWeave")

        val weave by tasks.registering(WeaveTask::class) {
            group = "build"
            classpath = configurations["kilimWeave"]
            mainClass.set("kilim.tools.Weaver")
            inputs.files(fileTree(File(inputClasses, "**/*.class")))
            outputs.dir(outputClasses)
            jvmArgs("--enable-preview")
            args("-d", outputClasses, inputClasses)

            val projectDependencies =
                configurations["implementation"].dependencies.withType(ProjectDependency::class.java)
            projectDependencies.forEach {
                val file = File(it.dependencyProject.buildDir, "classes/java/main")
                classpath += files(file.absolutePath)
            }
        }

        dependencies {
            val kilim = "org.db4j:kilim:2.0.2"
            add("kilimWeave", kilim)
            add("implementation", kilim)
        }

        sourceSets {
            named<SourceSet>("main") {
                runtimeClasspath += files(outputClasses)
            }
        }

        tasks {
            named<JavaCompile>("compileJava") {
                // make sure after java compilation weave is performed, no matter if java compile is up-to-date.
                finalizedBy(weave)
            }
            named<Jar>("jar") {
                duplicatesStrategy = DuplicatesStrategy.INCLUDE

                dependsOn(weave)

                // make sure the jar is re-generated after weave pass
                from(weave)
            }
            existing(JavaExec::class) {
                named<JavaExec>("run") {
                    dependsOn(weave)
                }
            }
        }
    }

}