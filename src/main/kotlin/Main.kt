package com.sikerspot.enchantOrderUtility

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Enchant Order Utility",
        resizable = true,
        state = rememberWindowState(width = 810.dp, height = 500.dp)
    ) {
        jsonGeneratorUI()
    }
}