package com.antigravity.prathamchikitse.models

import java.io.Serializable

data class EmergencyTopic(
    val id: String,
    val title: String,
    val iconResId: Int,
    val steps: List<GuideStep>
) : Serializable
