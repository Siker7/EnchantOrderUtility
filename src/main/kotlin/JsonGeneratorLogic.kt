package com.sikerspot.enchantOrderUtility

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

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

class EditableUserInputState(name: String, maxLevel: String, cost: String) {
    var nameFieldValue by mutableStateOf(name)
    var maxLevelFieldValue by mutableStateOf(maxLevel)
    var costFieldValue by mutableStateOf(cost)
}

@Composable
fun rememberEditableUserInputState(name: String, maxLevel: String, cost: String): EditableUserInputState =
    remember(name, maxLevel, cost) {
        EditableUserInputState(name, maxLevel, cost)
    }