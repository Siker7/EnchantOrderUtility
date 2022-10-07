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
        Row(modifier = Modifier.fillMaxWidth(663/810f).fillMaxHeight(45/500f), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.Top){
            //Navigates to the Enchantment Creation screen
            Button(modifier = Modifier.fillMaxWidth(1/3f).weight(1f).fillMaxHeight(if(state.wizardScreen == 1){1f}else{38/45f}), onClick = {state.wizardScreen = 1}){
                Text("Enchantments")
            }
            //Navigates to the Tool Creation screen
            Button(modifier = Modifier.fillMaxWidth(1/3f).weight(1f).fillMaxHeight(if(state.wizardScreen == 2){1f}else{38/45f}), onClick = {state.wizardScreen = 2}){
                Text("Tools")
            }
            //Navigates to the Conflict Creation screen
            Button(modifier = Modifier.fillMaxWidth(1/3f).weight(1f).fillMaxHeight(if(state.wizardScreen == 3){1f}else{38/45f}), onClick = {state.wizardScreen = 3}){
                Text("Conflicts")
            }
        }
        Spacer(Modifier.fillMaxHeight(28/500f))
        //Row containing the Enchantment Creation screen
        Box(modifier = Modifier.fillMaxWidth(663/810f).fillMaxHeight().weight(1f)){
            when(state.wizardScreen){
                1 -> enchantCreationScreen(state)
                2 -> toolCreationScreen(state)
                //3 -> conflictCreationScreen(state)
                //4 -> jsonCreationScreen(state)
            }

        }

        Row(modifier = Modifier.fillMaxWidth(546/810f).fillMaxHeight(90/500f), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
            Button(onClick = {if(state.wizardScreen == 1){/*closes the wizard*/}else{state.wizardScreen--}}, modifier = Modifier.fillMaxWidth(191/810f).weight(191/546f)){
                Text(if(state.wizardScreen == 1){"Cancel"}else{"Previous"})
            }
            Spacer(Modifier.fillMaxWidth(164/546f))
            Button(onClick = {if(state.wizardScreen == 3){/*continues to finisher screen*/}else{state.wizardScreen++}}, modifier = Modifier.fillMaxWidth(191/810f).weight(191/546f)) {
                Text(if(state.wizardScreen == 3){"Finish"}else{"Next"})
            }
        }
    }
}

@Composable
fun enchantCreationScreen(state:JsonGeneratorState){
    Row(modifier = Modifier.fillMaxSize()){
        //Column containing the input fields
        Column(modifier = Modifier.fillMaxHeight().fillMaxWidth(309/810f).weight(1f).padding(bottom = 7.5.dp), verticalArrangement = Arrangement.SpaceBetween){
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
                    .aspectRatio(309/73f)
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
                    .aspectRatio(309/73f)
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
                    .aspectRatio(309/73f)
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
            Row(modifier = Modifier.fillMaxWidth().aspectRatio(309/45f).width(IntrinsicSize.Min), horizontalArrangement = Arrangement.SpaceEvenly){
                //Clear button
                Button(modifier = Modifier.fillMaxHeight().aspectRatio(73/45f), onClick = {clearEnchantInputFields(state);focusManager.clearFocus()}){
                    Text("X")
                }
                //Add button
                Button(modifier = Modifier.fillMaxHeight().aspectRatio(73/45f), onClick = { addEnchantToList(state) }){
                    Text(">")
                }
            }
        }
        Spacer(Modifier.fillMaxWidth(46/810f).weight(46/309f))
        LazyColumn(modifier = Modifier.fillMaxHeight().fillMaxWidth(309/810f).weight(1f).padding(vertical = 7.5.dp).border(1.dp, Color.Gray, RoundedCornerShape(5.dp))) {
            items(items = state.jsonEnchantList) { list ->
                enchantCreationItem(state, list.enchantName, list.enchantMaxLevel, list.enchantCost)
            }
        }
    }

}

@Composable
fun toolCreationScreen(state:JsonGeneratorState){
    Row(modifier = Modifier.fillMaxSize()){
        //Column containing the input fields
        Column(modifier = Modifier.fillMaxHeight().fillMaxWidth(309/810f).weight(1f).padding(bottom = 7.5.dp), verticalArrangement = Arrangement.SpaceBetween){
            Row(modifier = Modifier, verticalAlignment = Alignment.Bottom){
                //Tool text field
                OutlinedTextField(
                    singleLine = true,
                    value = state.toolFieldValue,
                    onValueChange = { value ->
                        state.toolFieldValue = value.filter {it.isLetter() || it.isWhitespace()}
                    },
                    textStyle = TextStyle(fontSize = 20.sp),
                    label = { Text("Name") },
                    modifier = Modifier
                        .weight(236/309f)
                        .aspectRatio(236/60f)
                        .onKeyEvent {
                            if (it.key == Key(KeyEvent.VK_ENTER) && it.type == KeyEventType.KeyDown) {
                                addToolToList(state)
                                true
                            } else {
                                false
                            }
                        }
                )
                Spacer(Modifier.weight(28/309f))
                //don't like the aspect ratio of this button, fix later
                Button(modifier = Modifier.weight(45/309f).aspectRatio(16/19f), onClick = {addToolToList(state)}){
                    Text("⮛")
                }
            }
            Spacer(Modifier.fillMaxWidth().aspectRatio(309/27f))
            //Row containing the Clear and Add buttons
            LazyColumn(modifier = Modifier.fillMaxSize().border(1.dp, Color.Gray, RoundedCornerShape(5.dp))) {
                items(items = state.jsonToolList) {list ->
                    toolCreationItem(state, list.toolName)
                }

            }
        }
        Spacer(Modifier.fillMaxWidth(46/810f).weight(46/309f))
        LazyColumn(modifier = Modifier.fillMaxHeight().fillMaxWidth(309/810f).weight(1f).padding(vertical = 7.5.dp).border(1.dp, Color.Gray, RoundedCornerShape(5.dp))) {
            items(items = state.jsonEnchantList) { list ->
                enchantCreationItem(state, list.enchantName, list.enchantMaxLevel, list.enchantCost)
            }
        }
    }

}
// Card template meant to display a single item in the list of enchantments.
// This will be called multiple times, once for each enchantment in the list.
@Composable
fun enchantCreationItem(state:JsonGeneratorState, name: String, maxLevel: Int?, cost: Int?){
    Card(
        modifier = Modifier
            .padding(1.dp)
            .fillMaxWidth()
            .aspectRatio(309/27f)
            .border(2.dp, Color.LightGray, RoundedCornerShape(5.dp))
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium,
        elevation = 5.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Button(onClick = {editEnchant(name, state)}, modifier = Modifier.fillMaxHeight().aspectRatio(1f), contentPadding = PaddingValues(0.dp)){
                Text("<", fontSize = 20.sp)
            }
            Text(text = name,
                fontSize = 20.sp,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                softWrap = false,
                modifier = Modifier.weight(1f).padding(start = 5.dp))
            Spacer(modifier = Modifier.width(1.dp).fillMaxHeight().background(Color.LightGray))
            Text(text = maxLevel.toString(),
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                maxLines = 1,
                modifier = Modifier.weight(0.3f))
            Spacer(modifier = Modifier.width(1.dp).fillMaxHeight().background(Color.LightGray))
            Text(text = cost.toString(),
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                maxLines = 1,
                modifier = Modifier.weight(0.3f))
            Button(onClick = {deleteEnchant(name, state)}, modifier = Modifier.fillMaxHeight().aspectRatio(1f), contentPadding = PaddingValues(0.dp)){
                Text("X", fontSize = 20.sp)
            }
        }
    }
}

@Composable
fun toolCreationItem(state:JsonGeneratorState, name: String){
    Card(
        modifier = Modifier
            .padding(1.dp)
            .fillMaxWidth()
            .aspectRatio(309/27f)
            .border(2.dp, Color.LightGray, RoundedCornerShape(5.dp))
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium,
        elevation = 5.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Button(onClick = {editTool(name, state)}, modifier = Modifier.fillMaxHeight().aspectRatio(1f), contentPadding = PaddingValues(0.dp)){
                Text("^", fontSize = 20.sp)
            }
            Text(text = name,
                fontSize = 20.sp,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                softWrap = false,
                modifier = Modifier.weight(1f).padding(start = 5.dp))
            Button(onClick = {deleteTool(name, state)}, modifier = Modifier.fillMaxHeight().aspectRatio(1f), contentPadding = PaddingValues(0.dp)){
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
                Text("That item is already on the list. Would you like to replace it?", maxLines = 2, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                Row(Modifier.padding(top = 15.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly){
                    Button(onClick = {state.duplicateDialogOpen = false}){
                        Text("Cancel")
                    }
                    Button(onClick = {state.replaceWithDuplicate = true; when(state.wizardScreen){ 1 -> addEnchantToList(state); 2 -> addToolToList(state)}; state.duplicateDialogOpen = false}, modifier = Modifier.focusRequester(buttonFocusRequester)){
                        Text("Replace")
                    }
                }
            }
            LaunchedEffect(Unit) {
                //Find a way to prevent this from running while enter key is pressed
                //buttonFocusRequester.requestFocus()
            }
        }
    }
}
