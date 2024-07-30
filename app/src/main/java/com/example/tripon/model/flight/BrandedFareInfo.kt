package com.example.tripon.model.flight

data class BrandedFareInfo(
    val cabinClass: String,
    val fareAttributes: List<Any>,
    val fareName: String,
    val features: List<Any>,
    val nonIncludedFeatures: List<Any>,
    val nonIncludedFeaturesRequired: Boolean,
    val sellableFeatures: List<Any>
)