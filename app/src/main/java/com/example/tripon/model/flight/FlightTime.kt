package com.example.tripon.model.flight

data class FlightTime(
    val arrival: List<Arrival>,
    val departure: List<Departure>
)