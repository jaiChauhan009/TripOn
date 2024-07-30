package com.example.tripon.model.flight

data class Airline(
    val count: Int,
    val iataCode: String,
    val logoUrl: String,
    val minPrice: MinPriceX,
    val name: String
)