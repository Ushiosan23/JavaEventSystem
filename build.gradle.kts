plugins {
    signing
    `java-library`
    `maven-publish`
}

// Library configuration
group = "com.github.ushiosan23"
version = "0.0.1"

// Configure repositories
repositories {
    mavenCentral()
    jcenter()
}

// Configure java plugin
java {
    withJavadocJar()
    withSourcesJar()
}

// Configure dependencies
dependencies {
    /* basic dependencies */
    implementation("org.jetbrains:annotations:19.0.0")
    /* test */
    implementation("junit", "junit", "4.13")
}

/* ---------------------------------------------------------
 *
 * Publishing configuration
 *
 * --------------------------------------------------------- */

publishing {
    val stringVersion = rootProject.version as String
    val versionName = stringVersion.replace(".", "_")

    /* Configure publications */
    publications {

        /* Create maven publication */
        create<MavenPublication>("mavenJava") {
            groupId = rootProject.group as String
            artifactId = rootProject.name.toLowerCase()
            version = rootProject.version as String

            /* Set java components */
            from(components["java"])

            /* POM document */
            pom {
                name.set("${rootProject.name}_$versionName")
                description.set("A little event system based on java.util base.")
                url.set("https://github.com/Ushiosan23/JavaEventSystem")

                // License
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://raw.githubusercontent.com/Ushiosan23/JavaEventSystem/master/LICENSE")
                    }
                }
                developers {
                    developer {
                        id.set("Ushiosan23")
                        name.set("Ushiosan23")
                        email.set("haloleyendee@outlook.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/Ushiosan23/JavaEventSystem.git")
                    developerConnection.set("scm:git:ssh://github.com/Ushiosan23/JavaEventSystem.git")
                    url.set("https://github.com/Ushiosan23/JavaEventSystem")
                }
            }

            /* Dependencies */
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
        }

    }

    /* Define repositories */
    repositories {
        /* maven repositories */
        maven {
            val targetRepoURL = if (stringVersion.endsWith("SNAPSHOT"))
                "https://oss.sonatype.org/content/repositories/snapshots"
            else
                "https://oss.sonatype.org/service/local/staging/deploy/maven2"

            name = "MavenCentralRepository"
            url = uri(targetRepoURL)

            /* credentials */
            credentials {
                username = "Ushiosan23"
                password = rootProject.properties["mavenPass"] as String? ?: ""
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}

/* ---------------------------------------------------------
 *
 * Configure tasks
 *
 * --------------------------------------------------------- */

tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}
