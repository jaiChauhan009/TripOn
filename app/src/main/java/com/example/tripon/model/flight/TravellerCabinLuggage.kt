package com.example.tripon.model.flight

data class TravellerCabinLuggage(
    val luggageAllowance: LuggageAllowance,
    val personalItem: Boolean,
    val travellerReference: String
)