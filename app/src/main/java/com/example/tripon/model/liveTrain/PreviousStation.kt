package com.example.tripon.model.liveTrain

data class PreviousStation(
    val a_day: Int,
    val arrival_delay: Int,
    val distance_from_source: Int,
    val eta: String,
    val etd: String,
    val halt: Int,
    val is_diverted_station: Boolean,
    val non_stops: List<NonStop>,
    val platform_number: Int,
    val si_no: Int,
    val sta: String,
    val station_code: String,
    val station_lat: Double,
    val station_lng: Double,
    val station_name: String,
    val std: String,
    val stoppage_number: Int
)