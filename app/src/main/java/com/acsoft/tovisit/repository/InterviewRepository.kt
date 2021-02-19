package com.acsoft.tovisit.repository

import com.acsoft.tovisit.data.model.InterviewList

interface InterviewRepository {

    suspend fun getInterviews() : InterviewList

}