package com.example.tripon.model.flight

data class Duration(
    val durationType: String,
    val enabled: Boolean,
    val max: Int,
    val min: Int,
    val paramName: String
)