package com.example.tripon.model.liveTrain

data class CurrentLocationInfo(
    val deeplink: String,
    val hint: String,
    val img_url: String,
    val label: String,
    val message: String,
    val readable_message: String,
    val type: Int
)