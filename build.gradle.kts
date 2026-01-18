plugins {
    val kotlinVersion = "2.0.21"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    // JPA 플러그인과 allOpen은 일단 제거했습니다.
    id("org.springframework.boot") version "3.4.1"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.current"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // web 라이브러리만 남기고 jpa는 제거했습니다.
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // RSS 파싱을 위한 라이브러리 (최신 버전)
    implementation("com.prof18.rssparser:rssparser:6.0.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // 코루틴 핵심 라이브러리 (suspend 함수 실행용)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")

    // 코루틴 리액티브 라이브러리 (Spring suspend 컨트롤러 에러 해결용)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}
