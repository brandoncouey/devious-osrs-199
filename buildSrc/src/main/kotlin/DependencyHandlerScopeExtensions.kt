import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.lombok() {
    val dependencyNotation = "org.projectlombok:lombok:${Versions.LOMBOK}"
    add("compileOnly", dependencyNotation)
    add("annotationProcessor", dependencyNotation)
}