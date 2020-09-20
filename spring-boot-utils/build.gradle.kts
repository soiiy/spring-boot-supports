import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    maven
    kotlin("jvm") version "1.3.61"
}

dependencies {
    implementation("org.hibernate.validator:hibernate-validator:6.1.5.Final")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.10.1")
}

java.sourceCompatibility = JavaVersion.VERSION_11

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.named<Upload>("uploadArchives") {
    val codingArtifactsRepoUrl: String by project
    val codingArtifactsGradleUsername: String by project
    val codingArtifactsGradlePassword: String by project
    repositories.withGroovyBuilder {
        "mavenDeployer" {
            "repository"("url" to codingArtifactsRepoUrl) {
                "authentication"("userName" to codingArtifactsGradleUsername, "password" to codingArtifactsGradlePassword)
            }
            "pom" {
                setProperty("version", project.version)
                setProperty("groupId", project.group)
                setProperty("artifactId", project.name)
            }
        }
    }
}
