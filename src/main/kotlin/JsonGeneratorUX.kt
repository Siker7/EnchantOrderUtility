package com.sikerspot.enchantOrderUtility

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.rememberDialogState
import java.awt.event.KeyEvent

// The part of the UI containing the first page of the list creation feature.
// Eventually, this will be used to generate JSON files with lists of Enchant objects for the main part of the program to use.
@Composable
fun jsonGeneratorUI(state: JsonGeneratorState = rememberJsonGeneratorState("", "", "")){
    duplicateDialog(state)
    //Column containing the entirety of this UI
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        //Navigation tabs for the Json Generator UI
        Row(modifier = Modifier.fillMaxWidth(663.toFloat()/810).fillMaxHeight(45.toFloat()/500), horizontalArrangement = Arrangement.Center){
            //Navigates to the Enchantment Creation screen
            Button(modifier = Modifier.fillMaxWidth(1.toFloat()/3f).weight(1f).fillMaxHeight(), onClick = {}){
                Text("Enchantments")
            }
            //Navigates to the Tool Creation screen
            Button(modifier = Modifier.fillMaxWidth(1.toFloat()/3f).weight(1f).fillMaxHeight(), onClick = {}){
                Text("Tools")
            }
            //Navigates to the Conflict Creation screen
            Button(modifier = Modifier.fillMaxWidth(1.toFloat()/3f).weight(1f).fillMaxHeight(), onClick = {}){
                Text("Conflicts")
            }
        }
        Spacer(Modifier.fillMaxHeight(28.toFloat()/500))
        //Row containing the Enchantment Creation screen
        Row(modifier = Modifier.fillMaxWidth(663.toFloat()/810).fillMaxHeight().weight(1f)){
            //Column containing the input fields
            Column(modifier = Modifier.fillMaxHeight().fillMaxWidth(309.toFloat()/810).weight(1f).padding(bottom = 7.5.dp), verticalArrangement = Arrangement.SpaceBetween){
                val focusManager = LocalFocusManager.current
                val firstField = remember { FocusRequester() }
                //Name text field
                OutlinedTextField(
                    singleLine = true,
                    value = state.nameFieldValue,
                    onValueChange = { value ->
                        state.nameFieldValue = value.filter {it.isLetter() || it.isWhitespace()}
                    },
                    textStyle = TextStyle(fontSize = 20.sp),
                    label = { Text("Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(309.toFloat()/73)
                        .focusRequester(firstField)
                        .onKeyEvent {
                            if (it.key == Key(KeyEvent.VK_ENTER) && it.type == KeyEventType.KeyDown) {
                                focusManager.moveFocus(FocusDirection.Next)
                                true
                            } else {
                                false
                            }
                        }
                )
                //Max Level text field
                OutlinedTextField(
                    singleLine = true,
                    value = state.maxLevelFieldValue,
                    onValueChange = { value ->
                        state.maxLevelFieldValue = value.filter {it.isDigit()}.trimStart('0')
                    },
                    textStyle = TextStyle(fontSize = 20.sp),
                    label = { Text("Max Level") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(309.toFloat()/73)
                        .onKeyEvent {
                            if (it.key == Key(KeyEvent.VK_ENTER) && it.type == KeyEventType.KeyDown) {
                                focusManager.moveFocus(FocusDirection.Next)
                                true
                            } else {
                                false
                            }
                        }
                )
                //Cost text field
                OutlinedTextField(
                    singleLine = true,
                    value = state.costFieldValue,
                    onValueChange = { value ->
                        state.costFieldValue = value.filter {it.isDigit()}.trimStart('0')
                    },
                    keyboardActions = KeyboardActions(
                        onDone = {}
                    ),
                    textStyle = TextStyle(fontSize = 20.sp),
                    label = { Text("Cost") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(309.toFloat()/73)
                        .onKeyEvent {
                            if (it.key == Key(KeyEvent.VK_ENTER) && it.type == KeyEventType.KeyDown) {
                                addEnchantToList(state)
                                firstField.requestFocus()
                                true
                            } else {
                                false
                            }
                        }
                )
                //Row containing the Clear and Add buttons
                Row(modifier = Modifier.fillMaxWidth().aspectRatio(309.toFloat()/45).width(IntrinsicSize.Min), horizontalArrangement = Arrangement.SpaceEvenly){
                    //Clear button
                    Button(modifier = Modifier.fillMaxHeight().aspectRatio(73.toFloat()/45), onClick = {clearInputFields(state);focusManager.clearFocus()}){
                        Text("X")
                    }
                    //Add button
                    Button(modifier = Modifier.fillMaxHeight().aspectRatio(73.toFloat()/45), onClick = { addEnchantToList(state) }){
                        Text(">")
                    }
                }
            }
            Spacer(Modifier.fillMaxWidth(46.toFloat()/810).weight(46.toFloat()/309))
            LazyColumn(modifier = Modifier.fillMaxHeight().fillMaxWidth(309.toFloat()/810).weight(1f).padding(vertical = 7.5.dp).border(1.dp, Color.Gray, RoundedCornerShape(5.dp))) {
                items(items = state.jsonEnchantList) { list ->
                    enchantCard(state, list.enchantName, list.enchantMaxLevel, list.enchantCost)
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth(546.toFloat()/810).fillMaxHeight(90.toFloat()/500), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
            Button(onClick = {}, modifier = Modifier.fillMaxWidth(191.toFloat()/810).weight(191.toFloat()/546)){
                Text("")
            }
            Spacer(Modifier.fillMaxWidth(164.toFloat()/546))
            Button(onClick = {}, modifier = Modifier.fillMaxWidth(191.toFloat()/810).weight(191.toFloat()/546)) {
                Text("")
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
            .padding(1.dp)
            .fillMaxWidth()
            .aspectRatio(309.toFloat()/27)
            .border(2.dp, Color.LightGray, RoundedCornerShape(5.dp))
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium,
        elevation = 5.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Button(onClick = {editEnchant(name, state)}, modifier = Modifier.fillMaxHeight().aspectRatio(1.toFloat()), contentPadding = PaddingValues(0.dp)){
                Text("<", fontSize = 20.sp)
            }
            Text(text = name,
                fontSize = 20.sp,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                softWrap = false,
                modifier = Modifier.weight(1.toFloat()).padding(start = 5.dp))
            Spacer(modifier = Modifier.width(1.dp).fillMaxHeight().background(Color.LightGray))
            Text(text = maxLevel.toString(),
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                maxLines = 1,
                modifier = Modifier.weight(0.3.toFloat()))
            Spacer(modifier = Modifier.width(1.dp).fillMaxHeight().background(Color.LightGray))
            Text(text = cost.toString(),
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                maxLines = 1,
                modifier = Modifier.weight(0.3.toFloat()))
            Button(onClick = {deleteEnchant(name, state)}, modifier = Modifier.fillMaxHeight().aspectRatio(1.toFloat()), contentPadding = PaddingValues(0.dp)){
                Text("X", fontSize = 20.sp)
            }
        }
    }
}

// Dialog box that shows when someone attempts to add a duplicate enchantment.
// It gives the option to either cancel, or overwrite the existing item.
@Composable
fun duplicateDialog(state: JsonGeneratorState) {
    if(state.duplicateDialogOpen) {
        Dialog(onCloseRequest = {state.duplicateDialogOpen = false}, state = rememberDialogState(width = Dp.Unspecified, height = Dp.Unspecified), title = "Duplicate Enchantment"){
            val buttonFocusRequester = FocusRequester()
            Column(Modifier.padding(11.dp).width(250.dp)){
                Text("That enchantment is already on the list. Would you like to replace it?", maxLines = 2, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                Row(Modifier.padding(top = 15.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly){
                    Button(onClick = {state.duplicateDialogOpen = false}){
                        Text("Cancel")
                    }
                    Button(onClick = {state.replaceWithDuplicate = true; addEnchantToList(state); state.duplicateDialogOpen = false}, modifier = Modifier.focusRequester(buttonFocusRequester)){
                        Text("Replace")
                    }
                }
            }
            LaunchedEffect(Unit) {
                buttonFocusRequester.requestFocus()
            }
        }
    }
}
