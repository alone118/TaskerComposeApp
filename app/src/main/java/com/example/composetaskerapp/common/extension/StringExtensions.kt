package com.example.composetaskerapp.common.extension

import androidx.compose.ui.graphics.Color

fun String.convertToColor():Color {
    return Color(android.graphics.Color.parseColor(this))
}