import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.`maven-publish`

plugins {
    `maven-publish`
    signing
}

publishing {
    // Configure all publications
    publications.withType<MavenPublication> {
        // Stub javadoc.jar artifact
        artifact(tasks.register("${name}JavadocJar", Jar::class) {
            archiveClassifier.set("javadoc")
            archiveAppendix.set(this@withType.name)
        })

        // Provide artifacts information required by Maven Central
        pom {
            name.set("SweetMeSoft Kotlin Multiplatform common library")
            description.set("Common library with Composables, functions and tools to use accross different SweetMeSoft projects")
            url.set("https://github.com/erickvelasco11/Kmp_Library")

            licenses {
                license {
                    name.set("MIT")
                    url.set("https://opensource.org/licenses/MIT")
                }
            }
            developers {
                developer {
                    id.set("SweetMeSoft")
                    name.set("SweetMeSoft Team")
                    organization.set("SweetMeSoft")
                    organizationUrl.set("https://www.sweetmesoft.com")
                }
            }
            scm {
                url.set("https://github.com/erickvelasco11/Kmp_Library")
            }
        }
    }
}

signing {
    if (project.hasProperty("signing.gnupg.keyName")) {
        useGpgCmd()
        sign(publishing.publications)
    }
}
