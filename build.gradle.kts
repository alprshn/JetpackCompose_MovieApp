import com.android.build.gradle.internal.dsl.decorator.SupportedPropertyType.Collection.List.type


// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("com.google.dagger.hilt.android") version "2.49" apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
    id("androidx.room") version "2.6.1" apply false
}

