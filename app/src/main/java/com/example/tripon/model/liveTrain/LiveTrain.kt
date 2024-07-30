package com.example.tripon.model.liveTrain

data class LiveTrain(
    val `data`: Data,
    val message: String,
    val status: Boolean,
    val timestamp: Long
)