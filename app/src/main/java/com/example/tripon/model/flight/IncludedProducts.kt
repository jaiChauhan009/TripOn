package com.example.tripon.model.flight

data class IncludedProducts(
    val areAllSegmentsIdentical: Boolean,
    val segments: List<List<Segment>>
)