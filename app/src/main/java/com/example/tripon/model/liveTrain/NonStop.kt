package com.example.tripon.model.liveTrain

data class NonStop(
    val distance_from_source: Int,
    val is_diverted_station: Boolean,
    val si_no: Int,
    val sta: String,
    val station_code: String,
    val station_name: String,
    val std: String
)