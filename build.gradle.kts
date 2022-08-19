import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm") version "1.6.10"
    id("org.jetbrains.compose") version "1.1.1"
}

group = "com.sikerspot.enchantOrderUtility"
version = "1.0.0"

repositories {
    mavenCentral()
    google()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")


}

dependencies {
    //val navVersion = "2.5.1"
    testImplementation(kotlin("test"))
    implementation(compose.desktop.currentOs)
    /*
    I know for a fact these were needed at one point.
    Now, the program works fine without them, and doesn't even start with them active.
    I'm keeping these here for now in case that changes.

    //implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    //implementation("androidx.navigation:navigation-ui:$navVersion")
    //implementation("androidx.navigation:navigation-compose:$navVersion")
    */

    //Figure out repository | implementation("com.arkivanov.decompose:decompose:<0.6.0>")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "16"
}


compose.desktop {
    application{
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
        }
    }
}
