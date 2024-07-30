package com.example.tripon.model.flight

data class Fee(
    val currencyCode: String,
    val nanos: Int,
    val units: Int
)