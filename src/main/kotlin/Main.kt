package com.sikerspot.enchantOrderUtility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application{
    Window(onCloseRequest = ::exitApplication, title = "Enchant Order Utility", state = rememberWindowState(width = 800.dp, height = 600.dp)) {
        MaterialTheme {
            Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Column(Modifier.align(Alignment.CenterVertically), Arrangement.spacedBy(5.dp)) {
                    enchantName()
                    enchantMaxLevel()
                    enchantCost()
                    val count = remember{mutableStateOf(0)}
                    Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClick = {
                            count.value = 0
                        }) {
                        Text("Reset")
                    }
                }
                Column(Modifier.fillMaxSize(), Arrangement.spacedBy(5.dp)){
                    val count1 = remember{mutableStateOf(0)}
                    Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClick = {
                            count1.value = 0
                        }) {
                        Text("Reset")
                    }
                }
            }
        }

    }
}

@Composable
fun enchantName(){
    val name = remember{mutableStateOf(TextFieldValue())}
    TextField(
        //modifier = Modifier.align(Alignment.CenterHorizontally),
        value = name.value,
        onValueChange = {name.value = it},
        label = {Text("Name") }
    )
}

@Composable
fun enchantMaxLevel(){
    val maxLevel = remember{mutableStateOf(TextFieldValue())}
    TextField(
        value = maxLevel.value,
        onValueChange = {maxLevel.value = it},
        label = {Text("Max Level")}
    )
}

@Composable
fun enchantCost(){
    val cost = remember{mutableStateOf(TextFieldValue())}
    TextField(
        value = cost.value,
        onValueChange = {cost.value = it},
        label = {Text("Cost Multiplier")}
    )
}