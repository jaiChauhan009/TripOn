package com.example.tripon.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tripon.DateSelectionScreen
import com.example.tripon.MyApplication
import com.example.tripon.StationRepository
import com.example.tripon.StationViewModel
import com.example.tripon.StationViewModelFactory
import com.example.tripon.database.Note
import com.example.tripon.database.NoteDatabase
import com.example.tripon.model.TrainBwStaion.TrainBwStation
import com.example.tripon.model.liveTrain.Data
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@OptIn(DelicateCoroutinesApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyScreen(navController: NavController) {
    val repository = remember { StationRepository() }
    val viewModelFactory = remember { StationViewModelFactory(repository) }
    val stationViewModel: StationViewModel = viewModel(factory = viewModelFactory)
    var fromStation by remember { mutableStateOf("") }
    var toStation by remember { mutableStateOf("") }
    var trainList by remember { mutableStateOf<List<com.example.tripon.model.TrainBwStaion.Data>>(emptyList()) }
    var trainLiveData by remember { mutableStateOf<Data?>(null) }
    var selectedTrain by remember { mutableStateOf<com.example.tripon.model.TrainBwStaion.Data?>(null) }
    var selectedDate by remember { mutableStateOf("Select Date and Month") }
    val database: NoteDatabase = (LocalContext.current.applicationContext as MyApplication).database
    val noteDao = database.noteDao()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        DateSelectionScreen { formattedDate ->
            selectedDate = formattedDate
        }
        OutlinedTextField(
            value = fromStation,
            onValueChange = { fromStation = it },
            label = { Text("From Station") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = toStation,
            onValueChange = { toStation = it },
            label = { Text("To Station") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (fromStation.isNotEmpty() && toStation.isNotEmpty()) {
                    try {
                        val fromStationCode = stationViewModel.searchStation(fromStation)
                        fromStationCode.let {
                            val toStationCode = stationViewModel.searchStation(toStation)
                            toStationCode.let {
                                CoroutineScope(Dispatchers.IO).launch {
                                    trainList = repository.trainBwStation(fromStation,toStation,selectedDate)
                                }
                            }
                        }
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Search")
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (trainList.isNotEmpty()) {
            Text("Trains between $fromStation and $toStation:")
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                items(trainList.size) { index ->
                    val train = trainList[index]
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            stationViewModel.liveTrain(train.train_number)
                            selectedTrain = train
                            val note = Note(
                                title = "Train",
                                content = train.train_number
                            )
                            GlobalScope.launch(Dispatchers.IO) {
                                noteDao.insert(note)
                            }
                        }
                        .padding(5.dp)) {
                        Column {
                            Text(text = "Train Name - ${train.train_name}")
                            Text(text = "Train no - ${train.train_number}")
                        }
                    }
                }
            }
        }
        trainLiveData?.let { it1 -> LiveTrainData(train = it1) }
    }

    LaunchedEffect(key1 = stationViewModel.trainLiveData) {
        stationViewModel.trainLiveData.observeForever {
            trainLiveData = it
        }
    }
}

@Composable
fun LiveTrainData(train: Data) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 4.dp,
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Train Name - ${train.train_name}")
            Text(text = "Train no - ${train.train_number}")
            Text(text = "Running Days - ${train.run_days}")
            Text(text = "Alert message - ${train.new_alert_msg}")
            train.upcoming_stations.forEach { 
                Text(text = "at ${it.eta} train at ${it.station_name} PF no ${it.platform_number}")
            }
            Text(text = "current Station - ${train.current_station_name}")
            Text(text = "Current location info ----")
            Text(text = "current message - ${train.current_location_info[0].message}")
            Text(text = "late - ${train.current_location_info[0].hint}")
            train.previous_stations.forEach{
                Text(text = "at ${it.eta} train at ${it.station_name} PF no ${it.platform_number}")
            }
        }
    }
}
