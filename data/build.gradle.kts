plugins {
    id("org.jetbrains.kotlin.jvm")
}
dependencies {
    val retrofitGsonVersion = "2.9.0"
    val gsonVersion = "2.10.1"

    implementation(project(mapOf("path" to ":domain")))

    implementation("com.google.code.gson:gson:$gsonVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitGsonVersion")
}
