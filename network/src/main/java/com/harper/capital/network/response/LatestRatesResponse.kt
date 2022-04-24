package com.harper.capital.network.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class LatestRatesResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("timestamp") val timestamp: Long,
    @SerializedName("base") val base: String,
    @SerializedName("date") val date: String,
    @SerializedName("rates") val rates: Map<String, Double>
)
