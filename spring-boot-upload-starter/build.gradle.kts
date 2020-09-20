import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    maven
    kotlin("jvm") version "1.3.61"
}

dependencies {
    implementation("com.qcloud:cos_api:5.6.8")
    implementation("org.springframework.boot:spring-boot-autoconfigure:2.2.1.RELEASE")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
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

