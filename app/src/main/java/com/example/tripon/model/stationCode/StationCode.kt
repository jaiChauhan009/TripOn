package com.example.tripon.model.stationCode

data class StationCode(
    val `data`: List<Data>,
    val message: String,
    val status: Boolean,
    val timestamp: Long
)