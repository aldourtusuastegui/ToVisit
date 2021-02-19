package com.acsoft.tovisit.data.model

data class InterviewList(val results: List<InterviewItem> = listOf())

data class InterviewItem(
    val streetName: String,
    val suburb: String,
    val visited: Boolean,
    val location: Location,
)