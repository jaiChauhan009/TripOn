package com.example.tripon.screens

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.LocalImageLoader
import com.example.tripon.StationRepository
import com.example.tripon.StationViewModel
import com.example.tripon.StationViewModelFactory
import com.example.tripon.model.hotel.Result
import com.google.android.gms.location.LocationServices
import com.google.android.material.datepicker.MaterialDatePicker
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HotelScreen(navController: NavController) {
    val repository = remember { StationRepository() }
    val viewModelFactory = remember { StationViewModelFactory(repository) }
    val stationViewModel: StationViewModel = viewModel(factory = viewModelFactory)
    var checkinDate by remember { mutableStateOf("") }
    var checkoutDate by remember { mutableStateOf("") }
    var latitude by remember { mutableStateOf("") }
    var longitude by remember { mutableStateOf("") }
    var hotelList by remember { mutableStateOf(emptyList<Result>()) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text("Enter Search Criteria:")
        Spacer(modifier = Modifier.height(8.dp))

        CalendarComposeDate(
            selectedDate = checkinDate,
            onDateSelected = {
                checkinDate = it.format(DateTimeFormatter.ISO_LOCAL_DATE)
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        CalendarComposeDate(
            selectedDate = checkoutDate,
            onDateSelected = {
                checkoutDate = it.format(DateTimeFormatter.ISO_LOCAL_DATE)
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Get current location button
        Button(
            onClick = {
                val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return@Button
                }
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location : Location? ->
                        latitude = location?.latitude.toString()
                        longitude = location?.longitude.toString()
                    }
                    .addOnFailureListener {
                        Log.e("HotelScreen", "Error getting location: ")
                    }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Get Current Location")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Search button to trigger hotel search
        Button(
            onClick = {
                if (checkinDate.isNotEmpty() && checkoutDate.isNotEmpty() &&
                    latitude.isNotEmpty() && longitude.isNotEmpty()
                ) {
                    stationViewModel.hotelList(checkinDate, checkoutDate, latitude, longitude)
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Search Hotels")
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(hotelList.size) {
                val hotel = hotelList[it]
                Box(modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()){
                    Text(text = hotel.name)
                    Text(text = "hotel address - ${hotel.latitude} , ${hotel.longitude}")
                    Text(text = "Hotel payment - ${hotel.currency}")
                    LazyRow {
                        items(hotel.photoUrls.size){

                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = stationViewModel.hotelData) {
        stationViewModel.hotelData.observeForever {
            hotelList = it
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarComposeDate(
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
            CalendarCompose(picker = datePickerDialog, onDateSelected = onDateSelected)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarCompose(
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
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
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
            picker.showNow((context as FragmentActivity).supportFragmentManager, "DATE_PICKER")
        }

        onDispose {
            picker.clearOnPositiveButtonClickListeners()
        }
    }
}
