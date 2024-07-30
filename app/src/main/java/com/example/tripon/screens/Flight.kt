package com.example.tripon.screens

import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tripon.MyApplication
import com.example.tripon.StationRepository
import com.example.tripon.StationViewModel
import com.example.tripon.StationViewModelFactory
import com.example.tripon.database.Note
import com.example.tripon.database.NoteDatabase
import com.example.tripon.model.flight.Aggregation
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(DelicateCoroutinesApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Flight(navController: NavController){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val repository = remember { StationRepository() }
        val viewModelFactory = remember { StationViewModelFactory(repository) }
        val stationViewModel: StationViewModel = viewModel(factory = viewModelFactory)
        var flightList by remember { mutableStateOf<Aggregation?>(null) }
        var checkinDate by remember { mutableStateOf("") }
        var checkoutDate by remember { mutableStateOf("") }
        val database: NoteDatabase = (LocalContext.current.applicationContext as MyApplication).database
        val noteDao = database.noteDao()

        Text("Enter Search Criteria:")
        Spacer(modifier = Modifier.height(8.dp))

        CalendarDate(
            selectedDate = checkinDate,
            onDateSelected = {
                checkinDate = it.format(DateTimeFormatter.ISO_LOCAL_DATE)
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        CalendarDate(
            selectedDate = checkoutDate,
            onDateSelected = {
                checkoutDate = it.format(DateTimeFormatter.ISO_LOCAL_DATE)
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (checkinDate.isNotEmpty() && checkoutDate.isNotEmpty()
                ) {
                    stationViewModel.flights(checkinDate, checkoutDate)
                    val note = Note(
                        title = "Flight",
                        content = "Bombay to Delhi on $checkinDate to $checkoutDate"
                    )
                    GlobalScope.launch {
                        noteDao.insert(note)
                    }
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Search Flight")
        }
        Spacer(modifier = Modifier.height(16.dp))

        flightList?.airlines?.forEach {
            Column {
                Text(text = " -- FLIGHTS -- ")
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)){
                    Text(it.name)
                }
            }
        }

        flightList?.departureIntervals?.forEach {
            Column {
                Text(text = "Departure Interval")
                Text(text = "slotTiming == ${it.start} - ${it.end}")
            }
        }

        LaunchedEffect(key1 = stationViewModel.flightData) {
            stationViewModel.flightData.observeForever {
                flightList = it
            }
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarDate(
    selectedDate: String,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val datePickerDialog = remember { MaterialDatePicker.Builder.datePicker().build() }

    Box(modifier = modifier) {
        OutlinedTextField(
            value = selectedDate,
            onValueChange = { onDateSelected(LocalDate.parse(it)) },
            label = { Text(if (selectedDate.isEmpty()) "Select Date" else selectedDate) },
            modifier = Modifier
                .clickable { expanded = true }
                .padding(vertical = 8.dp)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color.White)
        ) {
            CalendarCompos(picker = datePickerDialog, onDateSelected = onDateSelected)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarCompos(
    picker: MaterialDatePicker<Long>,
    onDateSelected: (LocalDate) -> Unit
) {
    val context = LocalContext.current

    TextButton(
        onClick = {
            picker.showNow((context as androidx.fragment.app.FragmentActivity).supportFragmentManager, "DATE_PICKER")
        }
    ) {
        Text("Select Date")
    }

    val activityResultLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == androidx.appcompat.app.AppCompatActivity.RESULT_OK) {
            val selectedDate = picker.selection ?: return@rememberLauncherForActivityResult
            val localDate = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(selectedDate),
                ZoneId.systemDefault()
            ).toLocalDate()
            onDateSelected(localDate)
        }
    }

    DisposableEffect(Unit) {
        picker.addOnPositiveButtonClickListener {
            picker.showNow((context as androidx.fragment.app.FragmentActivity).supportFragmentManager, "DATE_PICKER")
        }

        onDispose {
            picker.clearOnPositiveButtonClickListeners()
        }
    }
}
