import org.gradle.internal.impldep.com.google.gson.Gson

plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.google.code.gson:gson:2.7")
//    compile group: 'org.apache.directory.shared', name: 'shared-ldap-client-all', version: '1.0.0-M10'
    implementation("org.apache.commons:commons-lang3:3.14.0")
//    implementation group: 'org.apache.commons', name: 'commons-lang3', version: '3.14.0'
}

tasks.test {
    useJUnitPlatform()
}