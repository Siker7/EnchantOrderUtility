package com.sikerspot.enchantOrderUtility

import androidx.compose.runtime.*

class JsonGeneratorState(name: String, maxLevel: String, cost: String) {
    var jsonEnchantList by mutableStateOf(mutableStateListOf<Enchant>())
    var nameFieldValue by mutableStateOf(name)
    var maxLevelFieldValue by mutableStateOf(maxLevel)
    var costFieldValue by mutableStateOf(cost)
    var duplicateDialogOpen by mutableStateOf(false)
    var replaceWithDuplicate by mutableStateOf(false)
}

@Composable
fun rememberJsonGeneratorState(name: String, maxLevel: String, cost: String): JsonGeneratorState =
    remember(name, maxLevel, cost) {
        JsonGeneratorState(name, maxLevel, cost)
    }

fun clearInputFields(state: JsonGeneratorState) {
    state.nameFieldValue = ""
    state.maxLevelFieldValue = ""
    state.costFieldValue = ""
}

fun addEnchantToList(state: JsonGeneratorState) {
    if (state.nameFieldValue.isAlphabetical() && state.maxLevelFieldValue.isNumerical() && state.costFieldValue.isNumerical()) {
        if (state.jsonEnchantList.any {it.enchantName.equals(state.nameFieldValue, true)}) {
            if (state.replaceWithDuplicate) {
                val replacedIndex = state.jsonEnchantList.indexOf(state.jsonEnchantList.find {it.enchantName.equals(state.nameFieldValue, true)})
                state.jsonEnchantList[replacedIndex] = (
                    Enchant(
                        state.nameFieldValue,
                        state.maxLevelFieldValue.toIntOrNull(),
                        state.costFieldValue.toIntOrNull()
                    )
                )
                state.replaceWithDuplicate = false
                clearInputFields(state)
            } else {
                state.duplicateDialogOpen = true
            }
        } else {
            state.jsonEnchantList.add(
                Enchant(
                    state.nameFieldValue,
                    state.maxLevelFieldValue.toIntOrNull(),
                    state.costFieldValue.toIntOrNull()
                )
            )
            clearInputFields(state)
        }

    }
}

fun deleteEnchant(enchantName: String, state: JsonGeneratorState) {
    val enchantToDelete: Enchant? = state.jsonEnchantList.find { it.enchantName == enchantName }
    state.jsonEnchantList.remove(enchantToDelete)
}

fun editEnchant(enchantName: String, state: JsonGeneratorState) {
    val enchantToEdit: Enchant? = state.jsonEnchantList.find { it.enchantName == enchantName }
    state.nameFieldValue = enchantToEdit?.enchantName.toString().trimEnd()
    state.maxLevelFieldValue = enchantToEdit?.enchantMaxLevel.toString()
    state.costFieldValue = enchantToEdit?.enchantCost.toString()
    deleteEnchant(enchantName, state)
}

/*
fun checkForDuplicateEnchants(state: JsonGeneratorState) {
    val enchantIterator = state.jsonEnchantList.listIterator()
    while (enchantIterator.hasNext()) {
        val iteratorIndex = enchantIterator.nextIndex()
        val iteratorName = enchantIterator.next().enchantName
        println(iteratorIndex)
        if (state.jsonEnchantList.any{it.enchantName == iteratorName && state.jsonEnchantList.indexOf(it) != iteratorIndex}){
            enchantIterator.set(state.jsonEnchantList[iteratorIndex].copy(isDuplicate = true))
            println("duplicate found")
            println(enchantIterator.nextIndex())
        } else {
            enchantIterator.set(state.jsonEnchantList[iteratorIndex].copy(isDuplicate = false))
            println("duplicate not found")
            println(enchantIterator.nextIndex())
        }
    }
}
*/

//These serve a duel purpose, both as redundancy for the input filter,
//as well as only allowing an item to be added if the fields aren't empty.
fun String?.isNumerical() = !this.isNullOrEmpty() && this.all {Character.isDigit(it)}
fun String?.isAlphabetical() = !this.isNullOrEmpty() && this.all {Character.isLetter(it) || Character.isWhitespace(it)}