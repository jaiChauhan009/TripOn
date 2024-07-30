package com.example.tripon.model.flight

data class TravellerPrice(
    val travellerPriceBreakdown: TravellerPriceBreakdown,
    val travellerReference: String,
    val travellerType: String
)