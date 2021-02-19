package com.acsoft.tovisit.data.model

import com.google.gson.annotations.SerializedName

//data class InterviewList(val results: List<InterviewItem> = listOf())

data class InterviewItem(
    @SerializedName("streetName")
    val streetName: String,
    @SerializedName("suburb")
    val suburb: String,
    @SerializedName("visited")
    val visited: Boolean,
    @SerializedName("location")
    val location: Location,
)