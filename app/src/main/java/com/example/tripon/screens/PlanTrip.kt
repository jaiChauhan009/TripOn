package com.example.tripon.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tripon.ChatScreen
import com.example.tripon.MyApplication
import com.example.tripon.R
import com.example.tripon.Screen
import com.example.tripon.SettingsScreen
import com.example.tripon.database.Note
import com.example.tripon.database.NoteDatabase
import kotlinx.coroutines.flow.first




data class Service(
    var title: String = "",
    var description: String = "",
    var image: Int = 0
)

val serviceList = listOf<Service>(
    Service("ai", "Description 1", R.drawable.ai),
    Service("map", "Description 2", R.drawable.map),
    Service("hotel", "Description 3", R.drawable.room),
    Service("taxi", "Description 4", R.drawable.taxi),
    Service("flight", "Description 5", R.drawable.plane),
    Service("train", "Description 6", R.drawable.train),
    Service("more", "Description 7", R.drawable.more),
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PlanTrip(navController: NavController){
    val database: NoteDatabase = LocalContext.current.applicationContext.let {
        context -> (context as MyApplication).database
    }
    val noteDao = database.noteDao()
    val noteList = remember { mutableStateOf<List<Note>>(emptyList()) }
    LaunchedEffect(Unit) {
        try {
            val notes = noteDao.getAllNotes().first()
            noteList.value = notes
        } catch (e: Exception) {
            // Handle error fetching notes from database
            Log.e("PlanTrip", "Error fetching notes: ${e.message}")
        }
    }
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "Book your service",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(vertical = 8.dp))
        LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.padding(5.dp) ) {
            items(serviceList){
                ServiceCard(service = it, navController = navController)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Recently Searched")
        Row  {
            var search by remember { mutableStateOf("") }
            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                label = { Text("Search") },
                modifier = Modifier
                    .width(200.dp)
                    .padding(horizontal = 8.dp)
            )
            Icon(imageVector = Icons.Default.Search, modifier = Modifier.clickable {

            }, contentDescription = "Search")

            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn{
                items(noteList.value) { note ->
                    NoteItem(note = note)
                }
            }
        }
    }
}

@Composable
fun ServiceCard(service: Service, navController: NavController) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                navController.navigate(service.title)
            }
    ) {
        Card(
            elevation = CardDefaults.cardElevation(5.dp),
            modifier = Modifier.size(150.dp)
        ) {
            Image(
                painter = painterResource(id = service.image),
                contentDescription = service.title,
                modifier = Modifier.fillMaxSize()
            )
        }
        Text(
            text = service.title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp),
            maxLines = 1
        )
    }
}

@Composable
fun NoteItem(note: Note) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = note.title, style = MaterialTheme.typography.bodyLarge)
        Text(text = note.content, style = MaterialTheme.typography.bodySmall)
    }
}