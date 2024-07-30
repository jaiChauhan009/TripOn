package com.example.tripon.model.flight

data class LuggageAllowanceX(
    val luggageType: String,
    val massUnit: String,
    val maxPiece: Int,
    val maxTotalWeight: Double,
    val maxWeightPerPiece: Double,
    val ruleType: String
)