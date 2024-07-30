package com.example.tripon

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.Coil
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.util.CoilUtils
import com.example.tripon.model.coupoun.Coupoun
import com.example.tripon.model.emoji.Emoji
import com.example.tripon.model.emoji.EmojiItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalCoilApi::class)
@Preview(showSystemUi = true)
@Composable
fun HomeScreen() {
    val database:AppDatabase = (LocalContext.current.applicationContext as MyApplication).myDatabase
    val travellerData = database.travellerDao().getAllTravellers().collectAsState(initial = emptyList())
    val repository = remember { StationRepository() }
    val viewModelFactory = remember { StationViewModelFactory(repository) }
    val stationViewModel: StationViewModel = viewModel(factory = viewModelFactory)
    var emojiList by remember { mutableStateOf<Emoji?>(null) }
    stationViewModel.emoji.value?.let { emoji ->
        emojiList = emoji as? Emoji
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp)){
        Column {
            TopBar()
            LazyRow {
                emojiList?.let {
                    items(it.size){
                        Text(text = emojiList!![it].emoji, fontSize = 30.sp)
                    }
                }
            }
            Row(horizontalArrangement = Arrangement.SpaceBetween){
                Text(text = "Stories By Travellers")
                TextButton(onClick = {  }) {
                    Text(text = "View All")
                }
            }
            LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.padding(5.dp) ) {
                items(travellerData.value.size){
                    Box(modifier = Modifier.weight(1f)){
                        Column {
                            LazyRow {
                                items(travellerData.value[it].image.size){
                                    val painter = rememberImagePainter(data = travellerData.value[it].image[it])
                                    Image(
                                        painter = painter,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(100.dp)
                                            .clip(RoundedCornerShape(5.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }
                            Text(text = "${travellerData.value[it].name} from ${travellerData.value[it].location}")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatScreen() {
    val database:AppDatabase = (LocalContext.current.applicationContext as MyApplication).myDatabase

    Text(text = "User Chat Screen")
    var search by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }
    Column {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)){
            Text(text = "Insert User")
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier
                    .width(200.dp)
                    .padding(horizontal = 8.dp)
            )
            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Location") },
                modifier = Modifier
                    .width(200.dp)
                    .padding(horizontal = 8.dp)
            )
            OutlinedTextField(
                value = contact,
                onValueChange = { contact = it },
                label = { Text("Contact") },
                modifier = Modifier
                    .width(200.dp)
                    .padding(horizontal = 8.dp),
                keyboardOptions= KeyboardOptions(
                    keyboardType = KeyboardType.Number),
            )
            Button(onClick = {
                val guider = Guider(0,name, location, contact)
                CoroutineScope(Dispatchers.IO).launch {
                    database.guiderDao().insertGuider(guider)
                }
            }) {
                Text(text = "Add User")
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                label = { Text("Search") },
                modifier = Modifier
                    .width(200.dp)
                    .padding(horizontal = 8.dp)
            )

            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                modifier = Modifier.clickable {
                    search = ""
                }
            )
        }

        LazyColumn {
            items(12){
                Box(modifier = Modifier
                    .clickable {
                    }){
                    Column {
                        Text(text = "Name")
                        Text(text = "Lorem20")
                    }
                }
            }
        }
    }
}



@Composable
fun TopBar() {
    var search by remember { mutableStateOf("") }
    var visible by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        // Menu Icon
        Icon(
            imageVector = Icons.Filled.Menu,
            contentDescription = "Menu",
            modifier = Modifier.clickable {
                visible = !visible
            }
        )

        // Search Bar
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                label = { Text("Search") },
                modifier = Modifier
                    .width(200.dp)
                    .padding(horizontal = 10.dp)
            )

            // Clear Search Icon
            if (search.isNotEmpty()) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Clear Search",
                    modifier = Modifier.clickable {
                        search = ""
                    }
                )
            }
        }

        // Mail Icon
        Icon(
            imageVector = Icons.Default.MailOutline,
            contentDescription = "Mail",
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }

    if (visible) {
        Sidebar()
    }
}




@Composable
fun Sidebar(){
    Box(modifier = Modifier
        .fillMaxWidth(0.7f)
        .fillMaxHeight()){

    }
}



@Composable
fun SettingsScreen() {
    val database:AppDatabase = (LocalContext.current.applicationContext as MyApplication).myDatabase
    val coupouns = database.couponDao().getAllCoupons().collectAsState(initial = emptyList())
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(12.dp)){
        Column {
            Text(text = "Coupouns")
            LazyColumn {
                items(coupouns.value.size){
                    Box(modifier = Modifier
                        .clickable {

                        }
                        .fillMaxWidth()){
                        Column {
                            Text(text = "Coupoun Code - ${coupouns.value[it].code}")
                            Text(text = coupouns.value[it].description)
                            Text(text = "Percentage off on trip ${coupouns.value[it].percentage}%")
                        }
                    }
                }
            }
            Text(text = "Services")
            LazyVerticalGrid(columns = GridCells.Fixed(3),
                modifier = Modifier.padding(5.dp) ) {
                items(9){
                    Card(modifier = Modifier
                        .weight(1f)
                        .height(100.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .clickable {

                        }) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                            Column {
                                Icon(imageVector = Icons.Filled.Notifications, contentDescription = null, modifier = Modifier.size(50.dp))
                                Text(text = "Name")
                            }
                        }
                    }
                }
            }
        }
    }
}


