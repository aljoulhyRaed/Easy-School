package com.techhunters.easyschool.core.service.models

import com.google.gson.annotations.SerializedName

data class Notification(
    @SerializedName("app_id") val appId: String,
    @SerializedName("contents") val contents: Map<String, String>,
    @SerializedName("included_segments") val includedSegments: List<String>,
    @SerializedName("data") val data: Map<String, String>
)
