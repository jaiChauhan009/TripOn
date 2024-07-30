package com.example.tripon.model.flight

data class Aggregation(
    val airlines: List<Airline>,
    val budget: Budget,
    val departureIntervals: List<DepartureInterval>,
    val duration: List<Duration>,
    val durationMax: Int,
    val durationMin: Int,
    val filteredTotalCount: Int,
    val flightTimes: List<FlightTime>,
    val minPrice: MinPriceX,
    val minPriceFiltered: MinPriceFiltered,
    val minRoundPrice: MinRoundPrice,
    val stops: List<Stop>,
    val totalCount: Int
)