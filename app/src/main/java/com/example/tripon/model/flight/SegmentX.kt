package com.example.tripon.model.flight

data class SegmentX(
    val arrivalAirport: ArrivalAirport,
    val arrivalTime: String,
    val departureAirport: DepartureAirport,
    val departureTime: String,
    val isAtolProtected: Boolean,
    val legs: List<Leg>,
    val showWarningDestinationAirport: Boolean,
    val showWarningOriginAirport: Boolean,
    val totalTime: Int,
    val travellerCabinLuggage: List<TravellerCabinLuggage>,
    val travellerCheckedLuggage: List<TravellerCheckedLuggage>
)