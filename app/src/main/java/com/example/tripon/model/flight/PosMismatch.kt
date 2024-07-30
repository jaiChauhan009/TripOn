package com.example.tripon.model.flight

data class PosMismatch(
    val detectedPointOfSale: String,
    val isPOSMismatch: Boolean,
    val offerSalesCountry: String
)