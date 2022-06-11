package com.sikerspot.enchantOrderUtility
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState


//implement state-hauling for these variables after you get a working commit
var enchantNameFieldValue: String = " "
var enchantCostFieldValue: Int? = 0
var enchantMaxLevelFieldValue: Int? = 0

fun jsonGeneratorUI() = application{
    Window(onCloseRequest = ::exitApplication, title = "Enchant Order Utility", resizable = false, state = rememberWindowState(width = 800.dp, height = 600.dp)) {
        MaterialTheme {
            Column(modifier = Modifier.fillMaxSize(), Arrangement.spacedBy(0.dp)) {
                topSection()
                Spacer(modifier = Modifier.height(40.dp))
                centerSection()
                Spacer(modifier = Modifier.height(5.dp))
                bottomSection()
            }
        }

    }
}

fun topSection(){
    //Haven't made this yet
}

//The part of the UI containing the enchantment input fields, buttons for adding/removing/editing enchants in the list, and the list.
@Preview
@Composable
fun centerSection(){

    //this is here because it works best for state-hauling
    val jsonEnchantList = mutableStateListOf<RawEnchant>()

    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        inputFields()
        Spacer(modifier = Modifier.width(40.dp))
        controlButtons({jsonEnchantList.add(RawEnchant(enchantNameFieldValue, enchantMaxLevelFieldValue, enchantCostFieldValue))}, {println("editButton pressed")})
        Spacer(modifier = Modifier.width(40.dp))
        enchantListDisplay(jsonEnchantList)
    }
}

//The part of the lists function holding the input fields.
@Composable
fun inputFields(){
    Column(verticalArrangement = Arrangement.spacedBy(5.dp), modifier = Modifier) {
        Text(
            text = "Create Enchantment",
            Modifier.width(280.dp).height(30.dp).border(width = 2.dp, color = Color.DarkGray)
                .padding(6.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(5.dp))
        Card(
            modifier = Modifier.width(280.dp).height(400.dp)
                .border(width = 2.dp, color = Color.DarkGray), backgroundColor = Color.LightGray
        ) {
            Column(Modifier, verticalArrangement = Arrangement.Center) {
                enchantName()
                enchantMaxLevel()
                enchantCost()
            }
        }
    }
}

@Composable
fun enchantName(){
    val fieldValue = remember { mutableStateOf(TextFieldValue()) }
    TextField(
        singleLine = true,
        value = fieldValue.value,
        onValueChange = {fieldValue.value = it},
        label = {Text("Name") }
    )
    enchantNameFieldValue = fieldValue.value.text
}


@Composable
fun enchantMaxLevel(){
    val fieldValue = remember { mutableStateOf(TextFieldValue()) }
    TextField(
        singleLine = true,
        value = fieldValue.value,
        onValueChange = {fieldValue.value = it},
        label = {Text("Max Level")}
    )
    enchantMaxLevelFieldValue = fieldValue.value.text.toIntOrNull()

}

@Composable
fun enchantCost(){
    val fieldValue = remember { mutableStateOf(TextFieldValue()) }
    TextField(
        singleLine = true,
        value = fieldValue.value,
        onValueChange = {fieldValue.value = it},
        label = {Text("Cost Multiplier")}
    )
    enchantCostFieldValue = fieldValue.value.text.toIntOrNull()
}

@Composable
fun controlButtons(addButton: () -> Unit, editButton: () -> Unit){
    Column(Modifier.width(40.dp).height(400.dp)) {
        Spacer(modifier = Modifier.height(195.dp))
        Button(onClick = addButton, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(">")
        }
        Button(onClick = editButton, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("<")
        }
    }
}

@Composable
fun enchantListDisplay(inputList: List<RawEnchant>){
    Column(verticalArrangement = Arrangement.spacedBy(5.dp), modifier = Modifier) {
        Text(
            text = "Enchantment List",
            Modifier.width(280.dp).height(30.dp).border(width = 2.dp, color = Color.DarkGray)
                .padding(6.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(5.dp))
        Card(
            modifier = Modifier.width(280.dp).height(400.dp)
                .border(width = 2.dp, color = Color.DarkGray), backgroundColor = Color.LightGray
        ) {
            rawEnchantList(inputList)
        }
    }
}

// LazyColumn meant to display the list of enchantments that have been submitted to the list so far.
@Composable
fun rawEnchantList(inputList: List<RawEnchant>){
    LazyColumn(modifier = Modifier) {
        items(items = inputList) { inputList ->
            rawEnchantCard(inputList.rawEnchantName, inputList.rawEnchantMaxLevel, inputList.rawEnchantCost)
        }
    }
}

// Card template meant to display a single item in the list of enchantments.
// This will be called multiple times, once for each enchantment in the list.
@Composable
fun rawEnchantCard(name: String, maxLevel: Int?, cost: Int?){
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium,
        elevation = 5.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = name)
            Text(text = maxLevel.toString())
            Text(text = cost.toString())
        }
    }
}

fun bottomSection(){
    //haven't made this yet
}
