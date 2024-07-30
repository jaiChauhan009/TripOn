package com.example.tripon.model.flight

data class FlexibleTicketX(
    val airProductReference: String,
    val priceBreakdown: PriceBreakdownXX,
    val recommendation: Recommendation,
    val travellers: List<String>
)