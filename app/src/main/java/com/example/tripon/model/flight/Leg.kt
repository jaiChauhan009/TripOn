package com.example.tripon.model.flight

data class Leg(
    val arrivalAirport: ArrivalAirport,
    val arrivalTerminal: String,
    val arrivalTime: String,
    val cabinClass: String,
    val carriers: List<String>,
    val carriersData: List<CarriersData>,
    val departureAirport: DepartureAirport,
    val departureTerminal: String,
    val departureTime: String,
    val flightInfo: FlightInfo,
    val flightStops: List<Any>,
    val totalTime: Int
)