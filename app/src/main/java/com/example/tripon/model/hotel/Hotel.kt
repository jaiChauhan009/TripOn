package com.example.tripon.model.hotel

data class Hotel(
    val count: Int,
    val mapPageFields: MapPageFields,
    val results: List<Result>
)