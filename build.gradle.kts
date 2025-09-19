plugins {
    alias(libs.plugins.android.application) version libs.versions.androidGradlePlugin.get() apply false
    alias(libs.plugins.android.library) version libs.versions.androidGradlePlugin.get() apply false
    alias(libs.plugins.kotlin.compose) version libs.versions.kotlinCompose apply false
    alias(libs.plugins.google.hilt) version libs.versions.hiltPlugin apply false
}