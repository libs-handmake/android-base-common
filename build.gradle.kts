plugins {
    alias(libs.plugins.android.library)
    id(libs.plugins.hoangdv.library.get().pluginId)
    id(libs.plugins.hoangdv.core.get().pluginId)
    id(libs.plugins.hoangdv.jetpack.compose.get().pluginId)
}

android {
    namespace = "common.hoangdz.lib"
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    //retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.converter.scalars)
}