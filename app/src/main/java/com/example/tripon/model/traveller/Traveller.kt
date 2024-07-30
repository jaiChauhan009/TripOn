package com.example.tripon.model.traveller

data class Traveller(
    val _id: Id,
    val image: List<String>,
    val location: String,
    val name: String
)