package com.example.tripon.model.flight

data class SizeRestrictions(
    val maxHeight: Double,
    val maxLength: Double,
    val maxWidth: Double,
    val sizeUnit: String
)