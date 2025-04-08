package com.kmj.presentation.util

import androidx.compose.runtime.staticCompositionLocalOf

val LocalCustomImageLoader = staticCompositionLocalOf<CustomImageLoader> {
    error("No CustomImageLoader provided")
}