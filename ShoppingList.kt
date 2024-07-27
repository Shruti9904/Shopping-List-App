package com.example.shoppinglistapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ShoppingList(){
    var sItems by remember { mutableStateOf(listOf<ShoppingItem>()) }
    var showDialog by remember{ mutableStateOf(false) }
    var itemName by remember { mutableStateOf("") }
    var itemQty by remember { mutableStateOf("") }

    Column(
        modifier= Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {showDialog=true},
            modifier= Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Add item")
        }

        LazyColumn(
            modifier= Modifier
                .fillMaxSize()
                .padding(16.dp)
        ){

            items(sItems){
                item->
                if(item.isEditing){
                    ShoppingItemEditor(
                        item = item,
                        onCompleteClick = {
                            editedName, editedQty ->
                            sItems = sItems.map { it.copy(isEditing = false) }
                            val editedItem=sItems.find { it.id== item.id }
                            editedItem?.let {
                                it.name=editedName
                                it.qty=editedQty
                            }
                        }
                    )
                }else{
                    ShowShoppingItem(item=item,
                        onEditClick = {
                            //finding out which item we are editing and changing the isEditing boolean to true
                            sItems=sItems.map { it.copy(isEditing = it.id==item.id) }
                        },
                        onDeleteClick = {
                            sItems=sItems-item
                        }
                    )
                }
            }
        }
    }

    if(showDialog){
        AlertDialog(onDismissRequest = { showDialog=false },
            confirmButton = {
                Row(modifier= Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween)
                {
                    //Add Button
                    Button(onClick = {
                        if(itemName.isNotBlank() && itemQty.isNotBlank()){
                            val newItem=ShoppingItem(
                                sItems.size+1,
                                name = itemName,
                                qty = itemQty.toInt(),
                                false
                            )
                            sItems=sItems+newItem
                            showDialog=false
                            itemName=""
                            itemQty=""
                        }

                    }) {
                        Text(text = "Add")
                    }
                    Spacer(modifier = Modifier.width(64.dp))

                    //Cancel Button
                    Button(onClick = {
                        showDialog=false }
                    ) {
                        Text(text = "Cancel")
                    }
                }
            },
            title ={
                Text(text = "Add Shopping Item")
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = itemName,
                        onValueChange = {
                            itemName=it
                        },
                        placeholder = {
                            Text(text = "Name")
                        },
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = itemQty,
                        onValueChange = {
                               itemQty=it
                        },
                        placeholder = {
                            Text(text = "Quantity")
                        },
                        singleLine = true
                    )
                }
            }
        )
    }
}
