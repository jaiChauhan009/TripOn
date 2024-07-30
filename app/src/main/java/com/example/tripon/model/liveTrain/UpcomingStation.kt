package com.example.tripon.model.liveTrain

data class UpcomingStation(
    val a_day: Int,
    val arrival_delay: Int,
    val day: Int,
    val distance_from_current_station: Int,
    val distance_from_current_station_txt: String,
    val distance_from_source: Int,
    val eta: String,
    val eta_a_min: Int,
    val etd: String,
    val food_available: Boolean,
    val halt: Int,
    val is_diverted_station: Boolean,
    val non_stops: List<NonStop>,
    val on_time_rating: Int,
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