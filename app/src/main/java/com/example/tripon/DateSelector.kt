package com.example.tripon


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed

@Composable
fun DateSelectionScreen(onDateSelected: (String) -> Unit) {
    var selectedMonthIndex by remember { mutableStateOf(-1) }
    var selectedDateIndex by remember { mutableStateOf(-1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // LazyRow for Months
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
        ) {
            itemsIndexed(months) { index, month ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .clickable {
                            selectedMonthIndex = index
                        }
                        .background(
                            color = if (selectedMonthIndex == index) Color.LightGray else Color.Transparent,
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(8.dp)
                ) {
                    Text(text = month)
                }
            }
        }
        // LazyRow for Dates
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
        ) {
            itemsIndexed(dates) { index, date ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .clickable {
                            selectedDateIndex = index
                        }
                        .background(
                            color = if (selectedDateIndex == index) Color.LightGray else Color.Transparent,
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(8.dp)
                ) {
                    Text(text = date)
                }
            }
        }

        // Display the selected date using FormatSelectedDate composable
        Text(
            text = FormatSelectedDate(selectedMonthIndex, selectedDateIndex)
        )

        // Callback to pass back the selected date string
        onDateSelected(FormatSelectedDate(selectedMonthIndex, selectedDateIndex))
    }
}


val months = listOf(
    "January", "February", "March", "April",
    "May", "June", "July", "August",
    "September", "October", "November", "December"
)

val dates = List(31) { (it + 1).toString() }

@Composable
fun FormatSelectedDate(selectedMonthIndex: Int, selectedDateIndex: Int): String {
    return if (selectedDateIndex != -1 && selectedMonthIndex != -1) {
        val formattedMonth = String.format("%02d", selectedMonthIndex + 1)
        val formattedDate = String.format("%02d", selectedDateIndex + 1)
        "Selected Date: 2024-$formattedMonth-$formattedDate"
    } else {
        "Select Date and Month"
    }
}

