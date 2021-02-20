package com.acsoft.tovisit.repository

import android.content.Context
import android.util.Log
import com.acsoft.tovisit.core.Resource
import com.acsoft.tovisit.data.local.LocalInterviewDataSource
import com.acsoft.tovisit.data.model.InterviewItemEntity
import com.acsoft.tovisit.data.model.toInterviewItemEntity
import com.acsoft.tovisit.data.remote.RemoteInterviewDataSource
import com.acsoft.tovisit.utils.isNetworkAvailable
import kotlinx.coroutines.flow.Flow

class InterviewRepositoryImpl(private val context: Context,
                              private val dataSourceRemote : RemoteInterviewDataSource,
                              private val dataSourceLocal : LocalInterviewDataSource) : InterviewRepository {

    override suspend fun getInterviews(): Flow<List<InterviewItemEntity>> {

        if (isNetworkAvailable(context)) {
            val call = dataSourceRemote.getInterviews()
            val interviewsList = call.body()
            if (call.isSuccessful) {

                interviewsList?.forEach { interviews ->
                    Log.d("TAG",interviews.toInterviewItemEntity().streetName)
                    dataSourceLocal.saveInterviews(interviews.toInterviewItemEntity())
                }

            } else {
                return dataSourceLocal.getInterviews()
            }
        }



        return dataSourceLocal.getInterviews()

    }

    override suspend fun getVisitsToDo(): Flow<Int> {
        return dataSourceLocal.getVisitsToDo()
    }
}