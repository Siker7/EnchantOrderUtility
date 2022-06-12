package com.sikerspot.enchantOrderUtility

import androidx.compose.runtime.*

//this function never worked properly for my purposes, now when editButton is called in controlButtons, that does what this was meant to do.
//keeping this here until I'm absolutely certain I won't need it anymore.
/*
fun addEnchantToList(listToChange: List<RawEnchant>, name: String, maxLevel: Int?, cost: Int?){
    val changedList = listToChange + listOf(RawEnchant(name, maxLevel, cost))
    println("addEnchantToList was run")
    println(name)
    println(changedList.toString())
}
*/

class JsonGeneratorState(name: String, maxLevel: String, cost: String) {
    var jsonEnchantList by mutableStateOf(mutableStateListOf<RawEnchant>())
    var nameFieldValue by mutableStateOf(name)
    var maxLevelFieldValue by mutableStateOf(maxLevel)
    var costFieldValue by mutableStateOf(cost)
}

@Composable
fun rememberJsonGeneratorState(name: String, maxLevel: String, cost: String): JsonGeneratorState =
    remember(name, maxLevel, cost) {
        JsonGeneratorState(name, maxLevel, cost)
    }