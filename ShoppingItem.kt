package com.example.shoppinglistapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

data class ShoppingItem(
    val id:Int,
    var name:String,
    var qty:Int,
    var isEditing:Boolean=false
)

@Composable
fun ShowShoppingItem(
    item: ShoppingItem,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
){
    Row(
        modifier= Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .border(
                border = BorderStroke(2.dp, Color(0XFF018786)),
                shape = RoundedCornerShape(20)
            )
            .height(48.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(text = item.name, modifier = Modifier.padding(8.dp))
        Text(text = item.qty.toString(), modifier = Modifier.padding(8.dp))

        //Edit Button
        IconButton(onClick = {onEditClick()}) {
            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Button")
        }

        //Delete Button
        IconButton(onClick = {onDeleteClick()}) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Button")
        }
    }
}

@Composable
fun ShoppingItemEditor(
    item:ShoppingItem,
    onCompleteClick:(String,Int)->Unit
){
    var editedName by remember{ mutableStateOf(item.name) }
    var editedQty by remember { mutableStateOf(item.qty.toString()) }
    var isEditing by remember { mutableStateOf(item.isEditing) }

    Row(modifier = Modifier
        .background(Color.White)
        .fillMaxWidth()
        .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly){
        Column {
            BasicTextField(value = editedName,
                onValueChange = {editedName=it},
                singleLine = true,
                modifier = Modifier.padding(8.dp)
            )
            BasicTextField(value = editedQty,
                onValueChange = {editedQty=it},
                singleLine = true,
                modifier = Modifier.padding(8.dp)
            )
        }

        Button(onClick = {
            onCompleteClick(editedName,editedQty.toIntOrNull()?:1)
            isEditing=false
        }) {
            Text(text = "Save")
        }
    }
}