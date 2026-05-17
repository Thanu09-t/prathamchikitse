package com.antigravity.prathamchikitse.models

data class Hospital(
    val name: String,
    val lat: Double,
    val lon: Double,
    val address: String = "",
    val phone: String = "",
    var distanceMeters: Float = 0f
)
