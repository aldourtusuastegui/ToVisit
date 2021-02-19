package com.acsoft.tovisit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.acsoft.tovisit.data.remote.RemoteInterviewDataSource
import com.acsoft.tovisit.data.remote.RetrofitClient
import com.acsoft.tovisit.presentation.InterviewModelFactory
import com.acsoft.tovisit.presentation.InterviewViewModel
import com.acsoft.tovisit.repository.InterviewRepositoryImpl

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<InterviewViewModel> {
        InterviewModelFactory(InterviewRepositoryImpl(
            RemoteInterviewDataSource(RetrofitClient.webService))
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.getInterviews().observe(this,{ result ->
            Log.d("NEW",result.toString())
        })

    }
}