package com.example.tripon.model.flight

data class IncludedProductsBySegment(
    val travellerProducts: List<TravellerProduct>,
    val travellerReference: String
)