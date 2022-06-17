package com.sikerspot.enchantOrderUtility

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.awt.event.KeyEvent

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

// Haven't made this yet. Might include things like tabs for each step of the process.
fun topSection(){

}

// The part of the UI containing everything in the center row of the application.
// This includes the input fields, buttons for adding/removing/editing enchants in the list, and the list.
@Composable
fun centerSection(state: JsonGeneratorState = rememberJsonGeneratorState("", "", "")){
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        inputFields(state)
        Spacer(modifier = Modifier.width(40.dp))
        controlButtons(state)
        Spacer(modifier = Modifier.width(40.dp))
        enchantList(state)
    }
}

//This is where the input fields live.
@Composable
fun inputFields(state: JsonGeneratorState){
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
                val focusManager = LocalFocusManager.current
                val firstField = remember { FocusRequester() }
                TextField(
                    singleLine = true,
                    value = state.nameFieldValue,
                    onValueChange = { value ->
                        state.nameFieldValue = value.filter {it.isLetter() || it.isWhitespace()}
                    },
                    label = {Text("Name")},
                    modifier = Modifier
                        .focusRequester(firstField)
                        .onKeyEvent {
                            if (it.key == Key(KeyEvent.VK_ENTER) && it.type == KeyEventType.KeyDown && state.nameFieldValue.isNotEmpty()) {
                                focusManager.moveFocus(FocusDirection.Next)
                                true
                            } else {
                                false
                            }
                        }
                )
                TextField(
                    singleLine = true,
                    value = state.maxLevelFieldValue,
                    onValueChange = { value ->
                        state.maxLevelFieldValue = value.filter {it.isDigit()}.trimStart('0')
                    },
                    label = {Text("Max Level")},
                    modifier = Modifier
                        .onKeyEvent {
                            if (it.key == Key(KeyEvent.VK_ENTER) && it.type == KeyEventType.KeyDown && state.maxLevelFieldValue.isNotEmpty()) {
                                focusManager.moveFocus(FocusDirection.Next)
                                true
                            } else {
                                false
                            }
                        }
                )
                TextField(
                    singleLine = true,
                    value = state.costFieldValue,
                    onValueChange = { value ->
                        state.costFieldValue = value.filter {it.isDigit()}.trimStart('0')
                    },
                    label = {Text("Cost Multiplier")},
                    keyboardActions = KeyboardActions(
                        onDone = {}
                    ),
                    modifier = Modifier
                        .onKeyEvent {
                            if (it.key == Key(KeyEvent.VK_ENTER) && it.type == KeyEventType.KeyDown && state.costFieldValue.isNotEmpty()) {
                                addEnchantToList(state)
                                firstField.requestFocus()
                                true
                            } else {
                                false
                            }
                        }
                )
            }
        }
    }
}

//The buttons in the center column.
@Composable
fun controlButtons(state: JsonGeneratorState){
    Column(Modifier.width(40.dp).height(400.dp)) {
        Spacer(modifier = Modifier.height(172.dp))
        //Converts the data from the input fields into an "enchant" object and adds that object to jsonEnchantList
        Button(onClick = {addEnchantToList(state)}, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(">")
        }
        Button(onClick = {deleteEnchant(state)}, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("X")
        }
        //(not implemented) will remove the selected object from list and fill the input fields with the data that was in the selected object
        Button(onClick = {editEnchant(state)}, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("<")
        }
    }
}

//the column on the right containing the enchantment list
@Composable
fun enchantList(state: JsonGeneratorState){
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
            LazyColumn(modifier = Modifier) {
                items(items = state.jsonEnchantList) { list ->
                    enchantCard(state, list.enchantName, list.enchantMaxLevel, list.enchantCost)
                }
            }
        }
    }
}

// Card template meant to display a single item in the list of enchantments.
// This will be called multiple times, once for each enchantment in the list.
@Composable
fun enchantCard(state:JsonGeneratorState, name: String, maxLevel: Int?, cost: Int?){
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .background(
                if (name == state.selectedEnchant)
                    Color.Red else Color.Yellow
            )
            .wrapContentHeight()
            .selectable
                (
                selected = name == state.selectedEnchant,
                onClick = { if (state.selectedEnchant != name)
                    state.selectedEnchant = name
                }
            ),
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
