package com.antigravity.prathamchikitse.models

import java.io.Serializable

data class GuideStep(
    val stepNumber: Int,
    val titleEn: String,
    val descriptionEn: String,
    val titleKa: String,
    val descriptionKa: String
) : Serializable
