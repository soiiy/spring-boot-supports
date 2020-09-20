import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    maven
    kotlin("jvm") version "1.3.61"
}

dependencies {
    implementation(project(":spring-boot-utils"))

    implementation("javax.servlet:javax.servlet-api:4.0.1")
    implementation("org.springframework:spring-web:5.2.1.RELEASE")
    implementation("org.springframework:spring-webmvc:5.2.1.RELEASE")
    implementation("org.springframework.boot:spring-boot-autoconfigure:2.2.1.RELEASE")
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