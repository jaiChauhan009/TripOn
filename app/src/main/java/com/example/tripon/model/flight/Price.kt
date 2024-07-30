package com.example.tripon.model.flight

data class Price(
    val currencyCode: String,
    val nanos: Int,
    val units: Int
)