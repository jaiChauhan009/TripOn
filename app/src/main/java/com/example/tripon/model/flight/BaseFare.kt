package com.example.tripon.model.flight

data class BaseFare(
    val currencyCode: String,
    val nanos: Int,
    val units: Int
)