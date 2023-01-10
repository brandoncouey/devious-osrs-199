package org.jire.gradle

import org.gradle.api.Task
import org.gradle.kotlin.dsl.TaskContainerScope
import org.gradle.kotlin.dsl.configure

internal inline fun <reified T : Task> TaskContainerScope.whenNamed(
    name: String,
    noinline configuration: T.() -> Unit
) = (findByName(name) as? T)?.configure(configuration)