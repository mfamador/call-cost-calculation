plugins {
    base
    kotlin("jvm") version "1.3.20" apply false
}

allprojects {

    group = "com.github.mfamador"

    version = "1.0"

    repositories {
        jcenter()
    }
}

dependencies {
    subprojects.forEach {
        archives(it)
    }
}