package com.sikerspot.enchantOrderUtility

fun addEnchantToList(listToChange: List<RawEnchant>, name: String, maxLevel: Int?, cost: Int?){
    val changedList = listToChange + listOf(RawEnchant(name, maxLevel, cost))
    println("addEnchantToList was run")
    println(name)
    println(changedList.toString())
}