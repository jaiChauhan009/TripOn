package com.example.tripon.model.flight

data class FlexibleTicket(
    val airProductReference: String,
    val preSelected: Boolean,
    val priceBreakdown: PriceBreakdown,
    val recommendation: Recommendation,
    val travellers: List<String>
)