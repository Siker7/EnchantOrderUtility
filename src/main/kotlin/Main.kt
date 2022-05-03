package com.sikerspot.enchantOrderUtility
import androidx.compose.material.Text
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application{
    Window(onCloseRequest = ::exitApplication) {
        Text("Hello, World!")
    }
}