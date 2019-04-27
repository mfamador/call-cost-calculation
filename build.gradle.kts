plugins {
    application
    kotlin("jvm") version "1.3.30"
}

application {
    mainClassName = "com.github.mfamador.callcost.MainKt"
}

group = "com.github.mfamador"
version = "1.0"

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {
    compile(kotlin("stdlib"))

    testImplementation("org.assertj:assertj-core:3.4.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.4.2")
}

repositories {
    jcenter()
}
