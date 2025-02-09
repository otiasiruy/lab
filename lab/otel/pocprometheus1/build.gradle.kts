import de.undercouch.gradle.tasks.download.Download

plugins {
    id("java")
    id("war")
    id("io.spring.dependency-management") version "1.1.0"
    id("de.undercouch.download") version "5.4.0"
}

group = "com.example"
version = "1.0-SNAPSHOT"

// Use Java toolchain to target Java 17
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenCentral()
}

// Use Spring BOM
dependencyManagement {
    imports {
        mavenBom("org.springframework:spring-framework-bom:6.1.2")
    }
}

dependencies {
    // Spring WebMVC
    implementation("org.springframework:spring-webmvc:6.1.2")

    // Micrometer
    implementation("io.micrometer:micrometer-core:1.10.5")
    implementation("io.micrometer:micrometer-registry-prometheus:1.10.5")

    compileOnly("jakarta.servlet:jakarta.servlet-api:6.0.0")

    // JUnit
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
}

tasks.test {
    useJUnitPlatform()
}

tasks.war {
    archiveFileName.set("my-spring6-app.war")
}

// -------------------------------------------------------------
// Task to download the OpenTelemetry Java Agent
// -------------------------------------------------------------
val downloadOtelAgent by tasks.registering(Download::class) {
    src("https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v1.20.0/opentelemetry-javaagent.jar")
    dest(file("$buildDir/otel/opentelemetry-javaagent.jar"))
    overwrite(false)
}

// -------------------------------------------------------------
// (Optional) Copy the downloaded agent into a distribution folder
// -------------------------------------------------------------
val copyOtelAgent by tasks.registering(Copy::class) {
    dependsOn(downloadOtelAgent)
    from(file("$buildDir/otel/opentelemetry-javaagent.jar"))
    into("$buildDir/distribution/otel")
}

// -------------------------------------------------------------
// (Optional) Create a zip distribution
// -------------------------------------------------------------
tasks.register<Zip>("distZip") {
    dependsOn(tasks.war, copyOtelAgent)
    from(tasks.war.get().archiveFile) {
        into("lib")
    }
    from("$buildDir/distribution/otel") {
        into("otel")
    }
    archiveFileName.set("my-spring6-app-distribution.zip")
}