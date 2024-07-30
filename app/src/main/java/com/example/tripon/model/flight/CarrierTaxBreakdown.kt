package com.example.tripon.model.flight

data class CarrierTaxBreakdown(
    val avgPerAdult: AvgPerAdult,
    val avgPerChild: AvgPerChild,
    val avgPerInfant: AvgPerInfant,
    val carrier: Carrier
)