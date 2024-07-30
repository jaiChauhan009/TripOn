package com.example.tripon.model.flight

data class Segment(
    val luggageType: String,
    val massUnit: String,
    val maxPiece: Int,
    val maxTotalWeight: Double,
    val maxWeightPerPiece: Double,
    val piecePerPax: Int,
    val ruleType: String,
    val sizeRestrictions: SizeRestrictions
)