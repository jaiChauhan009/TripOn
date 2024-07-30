package com.example.tripon.model.flight

data class LuggageAllowance(
    val luggageType: String,
    val massUnit: String,
    val maxPiece: Int,
    val maxWeightPerPiece: Double,
    val sizeRestrictions: SizeRestrictions
)