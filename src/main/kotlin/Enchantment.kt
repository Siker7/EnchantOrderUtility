package com.sikerspot.enchantOrderUtility

//data class defining an Enchantment by the most relevant metrics to xp cost in-game
data class Enchant(val enchantName:String, val enchantMaxLevel:Int?, val enchantCost:Int?, val conflictList:List<Enchant>)

data class Tool(val toolName:String, val enchantList:List<Enchant>)
