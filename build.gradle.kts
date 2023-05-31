import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.incremental.ChangesCollector.Companion.getNonPrivateNames

plugins {
    id("org.springframework.boot") version "2.7.2"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
    kotlin("plugin.jpa") version "1.7.22"
    kotlin("kapt") version "1.7.10"
    kotlin("plugin.allopen") version "1.6.21"
    kotlin("plugin.noarg") version "1.6.21"
    jacoco
}

group = "com"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

val asciidoctorExt: Configuration by configurations.creating
dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-devtools")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // Monitoring
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.micrometer:micrometer-core")
    implementation("io.micrometer:micrometer-registry-prometheus")

    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "mockito-core")
    }
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("com.ninja-squad:springmockk:4.0.0")

    // P6SY
    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.5.6")

    // Spring Docs
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    asciidoctorExt("org.springframework.restdocs:spring-restdocs-asciidoctor")

    // Swagger
    implementation("io.springfox:springfox-boot-starter:3.0.0")
    implementation("io.springfox:springfox-swagger-ui:3.0.0")

    // Database
    runtimeOnly("com.h2database:h2")

    // Querydsl
    implementation("com.querydsl:querydsl-jpa:5.0.0")
    kapt("com.querydsl:querydsl-apt:5.0.0:jpa")

    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.20")
    annotationProcessor("org.projectlombok:lombok:1.18.20")

    // MapStruct
    val mapStructVersion by extra("1.4.2.Final")
    implementation("org.mapstruct:mapstruct:${mapStructVersion}")
    kapt("org.mapstruct:mapstruct:${mapStructVersion}")
    kapt("org.mapstruct:mapstruct-processor:${mapStructVersion}")

    // Logback
    implementation("org.springframework.boot:spring-boot-starter-batch")

    // String-Utils
    implementation("org.apache.commons:commons-lang3:3.12.0")

    // Slack
    implementation("net.gpedro.integrations.slack:slack-webhook:1.4.0")
    implementation("com.github.maricn:logback-slack-appender:1.6.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.test {
    finalizedBy("jacocoTestReport")
}

tasks.jacocoTestReport {
    reports {
        html.required.set(true)
    }
    val QDomains = ('A'..'Z')
        .toMutableList()
        .map { "**/Q$it*" }


    classDirectories.setFrom(
        files(
            classDirectories.files.map {
                fileTree(it) {
                    exclude(QDomains)
                }
            }
        )
    )
    finalizedBy("jacocoTestCoverageVerification")
}

tasks.jacocoTestCoverageVerification {
    val Qdomains = mutableListOf<String>()

    for (qPattern in 'A'..'Z') {
        Qdomains.add("*.Q${qPattern}*")
    }
    violationRules {
        rule {
            element = "CLASS"

            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "0.90".toBigDecimal()
            }
            excludes = Qdomains

        }
    }
}

tasks.jar {
    enabled = false
}

val snippetsDir by extra { file("build/generated-snippets") }
tasks {
    test {
        outputs.dir(snippetsDir)
    }

    asciidoctor {
        inputs.dir(snippetsDir)
        configurations(asciidoctorExt.name)
        dependsOn(test)
        doLast {
            copy {
                from("build/docs/asciidoc")
                into("src/main/resources/static/docs")
            }
        }
    }

    build {
        dependsOn(asciidoctor)
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

noArg {
    annotation("javax.persistence.Entity")
}