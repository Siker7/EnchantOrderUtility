package com.sikerspot.enchantOrderUtility

import androidx.compose.runtime.*

class JsonGeneratorState(name: String, maxLevel: String, cost: String) {
    var jsonEnchantList by mutableStateOf(mutableStateListOf<Enchant>())
    var nameFieldValue by mutableStateOf(name)
    var maxLevelFieldValue by mutableStateOf(maxLevel)
    var costFieldValue by mutableStateOf(cost)
    var selectedEnchant by mutableStateOf("")
}

@Composable
fun rememberJsonGeneratorState(name: String, maxLevel: String, cost: String): JsonGeneratorState =
    remember(name, maxLevel, cost) {
        JsonGeneratorState(name, maxLevel, cost)
    }

fun addEnchantToList(state: JsonGeneratorState) {
    if (state.nameFieldValue.isAlphabetical() && state.maxLevelFieldValue.isNumerical() && state.costFieldValue.isNumerical()) {
        state.jsonEnchantList.add(
            Enchant(
                state.nameFieldValue,
                state.maxLevelFieldValue.toIntOrNull(),
                state.costFieldValue.toIntOrNull()
            )
        )
        state.selectedEnchant = state.nameFieldValue
        state.nameFieldValue = ""
        state.maxLevelFieldValue = ""
        state.costFieldValue = ""
    }
}

fun deleteEnchant(state: JsonGeneratorState) {
    if (state.selectedEnchant != "") {
        val enchantToDelete: Enchant? = state.jsonEnchantList.find { it.enchantName == state.selectedEnchant }
        state.jsonEnchantList.remove(enchantToDelete)
        state.selectedEnchant = ""
    }
}

fun editEnchant(state: JsonGeneratorState) {
    if (state.selectedEnchant != "") {
        val enchantToEdit: Enchant? = state.jsonEnchantList.find { it.enchantName == state.selectedEnchant }
        state.nameFieldValue = enchantToEdit?.enchantName.toString().trimEnd()
        state.maxLevelFieldValue = enchantToEdit?.enchantMaxLevel.toString()
        state.costFieldValue = enchantToEdit?.enchantCost.toString()
        state.jsonEnchantList.remove(enchantToEdit)
        state.selectedEnchant = ""
    }
}


//these may not be necessary, as the textFields filter this stuff out on their own
fun String?.isNumerical() = !this.isNullOrEmpty() && this.all {Character.isDigit(it)}
fun String?.isAlphabetical() = !this.isNullOrEmpty() && this.all {Character.isLetter(it) || Character.isWhitespace(it)}