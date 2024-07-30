package com.example.tripon.model.flight

data class FlightInfo(
    val carrierInfo: CarrierInfo,
    val facilities: List<Any>,
    val flightNumber: Int,
    val planeType: String
)