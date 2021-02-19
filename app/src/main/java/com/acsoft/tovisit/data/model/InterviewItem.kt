package com.acsoft.tovisit.data.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InterviewItemData(
        val streetName: String,
        val suburb: String,
        val visited: Boolean,
        val location: Location,
) : Parcelable

@Entity
data class InterviewItemEntity(
    @PrimaryKey
    val streetName: String,
    val suburb: String,
    val visited: Boolean,
    @Embedded
    val location: Location,
)

fun List<InterviewItemEntity>.toInterviewList(): MutableList<InterviewItemData> {
    val resultList = mutableListOf<InterviewItemData>()
    this.forEach { interviewEntity ->
        resultList.add(interviewEntity.toInterviewItemData())
    }
    return resultList
}

fun InterviewItemEntity.toInterviewItemData(): InterviewItemData = InterviewItemData(
        this.streetName,
        this.suburb,
        this.visited,
        this.location
)

fun InterviewItemData.toInterviewItemEntity(): InterviewItemEntity = InterviewItemEntity(
        this.streetName,
        this.suburb,
        this.visited,
        this.location
)




