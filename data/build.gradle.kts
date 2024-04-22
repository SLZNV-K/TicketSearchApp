plugins {
    id("org.jetbrains.kotlin.jvm")
}
dependencies {
    val retrofitGsonVersion = "2.9.0"
    val gsonVersion = "2.10.1"

    implementation(project(":domain"))

    implementation("javax.inject:javax.inject:1")
    api("com.google.code.gson:gson:$gsonVersion")
    api("com.squareup.retrofit2:converter-gson:$retrofitGsonVersion")
}
