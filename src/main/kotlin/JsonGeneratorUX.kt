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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

// The part of the UI containing the first page of the list creation feature.
// Eventually, this will be used to generate JSON files with lists of Enchant objects for the main part of the program to use.
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

// Haven't made this yet. Might include things like a sort button for the column of enchants on the right in the future.
fun topSection(){

}

// The part of the UI containing everything in the center row of the application.
// This includes the input fields, buttons for adding/removing/editing enchants in the list, and the list.
@Preview
@Composable
fun centerSection(state: EditableUserInputState = rememberEditableUserInputState("", "", "")){

    //this is here because it works best for state-hauling (I think)
    val jsonEnchantList = mutableStateListOf<RawEnchant>()

    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        inputFields(state)
        Spacer(modifier = Modifier.width(40.dp))
        controlButtons({jsonEnchantList.add(RawEnchant(state.nameFieldValue, state.maxLevelFieldValue.toIntOrNull(), state.costFieldValue.toIntOrNull()))}, {println("editButton pressed")})
        Spacer(modifier = Modifier.width(40.dp))
        enchantListDisplay(jsonEnchantList)
    }
}

//This is where the input fields live.
@Composable
fun inputFields(state: EditableUserInputState){
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
                enchantName(state.nameFieldValue) {state.nameFieldValue = it}
                enchantMaxLevel(state.maxLevelFieldValue) {state.maxLevelFieldValue = it}
                enchantCost(state.costFieldValue) {state.costFieldValue = it}
            }
        }
    }
}

//The input field for Enchantment Name
@Composable
fun enchantName(nameValue: String, nameChanged: (String) -> Unit){
    //val fieldValue = remember { mutableStateOf(TextFieldValue()) }
    TextField(
        singleLine = true,
        value = nameValue,
        onValueChange = nameChanged,
        label = {Text("Name")}
    )
}

//The input field for Maximum Enchantment Power Level
@Composable
fun enchantMaxLevel(maxLevelValue: String, maxLevelChanged: (String) -> Unit){
    //val fieldValue = remember { mutableStateOf(TextFieldValue()) }
    TextField(
        singleLine = true,
        value = maxLevelValue,
        onValueChange = maxLevelChanged,
        label = {Text("Max Level")}
    )

}

//The input field for Enchantment XP Cost
@Composable
fun enchantCost(costValue: String, costChanged: (String) -> Unit){
    //val fieldValue = remember { mutableStateOf(TextFieldValue()) }
    TextField(
        singleLine = true,
        value = costValue,
        onValueChange = costChanged,
        label = {Text("Cost Multiplier")}
    )
}

//The buttons in the center column.
@Composable
fun controlButtons(addButton: () -> Unit, editButton: () -> Unit){
    Column(Modifier.width(40.dp).height(400.dp)) {
        Spacer(modifier = Modifier.height(195.dp))
        //Converts the data from the input fields into a RawEnchant object and adds that object to jsonEnchantList
        Button(onClick = addButton, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(">")
        }
        //(not implemented) will remove the selected object from list and fill the input fields with the data that was in the selected object
        Button(onClick = editButton, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("<")
        }
    }
}

//the column on the right containing the enchantment list
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

//haven't made this yet, will include buttons to progress to the next step, to save, and to exit list creation.
fun bottomSection(){

}
