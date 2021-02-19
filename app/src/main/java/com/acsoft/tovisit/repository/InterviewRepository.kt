package com.acsoft.tovisit.repository

import com.acsoft.tovisit.data.model.InterviewItem

interface InterviewRepository {

    suspend fun getInterviews() : List<InterviewItem>

}