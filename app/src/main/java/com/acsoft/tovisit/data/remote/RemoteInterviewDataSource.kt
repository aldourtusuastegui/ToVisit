package com.acsoft.tovisit.data.remote

import com.acsoft.tovisit.data.model.InterviewItemData
import retrofit2.Response

class RemoteInterviewDataSource(private val webService: WebService) {

    suspend fun getInterviews() : Response<List<InterviewItemData>> {
        return webService.getInterviews()
    }

}