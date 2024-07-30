package com.example.tripon.model.flight

data class FlightDeal(
    val key: String,
    val offerToken: String,
    val price: Price,
    val travellerPrices: List<TravellerPrice>
)