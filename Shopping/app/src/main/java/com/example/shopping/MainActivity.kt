package com.example.shopping

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.shopping.ui.theme.ShoppingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingTheme {
                ShoppingCartApp()
                }
            }
        }
    }


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ShoppingCartApp() {
    var data = remember { mutableStateListOf<String>() }
    var isShowDialog = remember {
        mutableStateOf(false)
    }
    var newItemName by remember {
        mutableStateOf("")
    }
    if (isShowDialog.value) {
        InputDialog(
            itemName = newItemName,
            onCancel = {
                isShowDialog.value = false
            },
            onAddButtonClick = { newItem ->
                data.add(newItem)
                isShowDialog.value = false
            }
        )
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Shopping Cart")},
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.LightGray,
                    titleContentColor = Color.Magenta
                ))
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { isShowDialog.value = true },
                containerColor = Color.Magenta) {
                Icon(Icons.Filled.Add,
                    "Add new Items",
                    tint = Color.White)
            }
        }
    ){
        LazyColumn(modifier = Modifier.padding(it)) {
            items(data) {item ->
                CartItem(item)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InputDialog(
    itemName: String,
    onCancel: () -> Unit,
    onAddButtonClick: (String) -> Unit
) {
    Dialog(
        onDismissRequest = onCancel,
    ) {
        var textValue by remember {
            mutableStateOf(itemName)
        }
        Card(
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                TextField(
                    value = textValue,
                    onValueChange = { textValue = it },
                    label = { Text("Itemname") }
                )
                TextButton(onClick = { onAddButtonClick(textValue) }) {
                    Text("Add")
                }
            }
        }
    }
}
@Composable
private fun CartItem(itemname:String) {
    var amount : Int by remember { mutableStateOf(0) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
    ){
        Text(
            "$itemname",
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp)
        )
        IconButton(onClick = { amount-- }) {
            Icon(Icons.Filled.ArrowBack,
                "Decrease")
        }
        Text(
            "$amount",
        )
        IconButton(onClick = { amount++ }) {
            Icon(Icons.Filled.ArrowForward,
                "Increase")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview(){
    ShoppingCartApp()
}
