allprojects {
    group = "com.soiiy.platform"
    version = "0.0.1-SNAPSHOT"
}

subprojects {
    repositories {
        val codingArtifactsRepoUrl: String by project
        val codingArtifactsGradleUsername: String by project
        val codingArtifactsGradlePassword: String by project
        maven {
            url = uri(codingArtifactsRepoUrl)
            credentials {
                username = codingArtifactsGradleUsername
                password = codingArtifactsGradlePassword
            }
        }
        mavenLocal()
        mavenCentral()
    }
}