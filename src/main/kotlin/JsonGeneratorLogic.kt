package com.sikerspot.enchantOrderUtility

import androidx.compose.runtime.*

class JsonGeneratorState(name: String, maxLevel: String, cost: String) {
    var jsonEnchantList by mutableStateOf(mutableStateListOf<Enchant>())
    var nameFieldValue by mutableStateOf(name)
    var maxLevelFieldValue by mutableStateOf(maxLevel)
    var costFieldValue by mutableStateOf(cost)
}

@Composable
fun rememberJsonGeneratorState(name: String, maxLevel: String, cost: String): JsonGeneratorState =
    remember(name, maxLevel, cost) {
        JsonGeneratorState(name, maxLevel, cost)
    }

fun addEnchantToList(state: JsonGeneratorState) {
    state.jsonEnchantList.add(Enchant(state.nameFieldValue, state.maxLevelFieldValue.toIntOrNull(), state.costFieldValue.toIntOrNull()))
}