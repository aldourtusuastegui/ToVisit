package com.acsoft.tovisit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.acsoft.tovisit.data.local.AppDatabase
import com.acsoft.tovisit.data.local.LocalInterviewDataSource
import com.acsoft.tovisit.data.remote.RemoteInterviewDataSource
import com.acsoft.tovisit.data.remote.RetrofitClient
import com.acsoft.tovisit.presentation.InterviewModelFactory
import com.acsoft.tovisit.presentation.InterviewViewModel
import com.acsoft.tovisit.repository.InterviewRepositoryImpl
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<InterviewViewModel> {
        InterviewModelFactory(InterviewRepositoryImpl(
            applicationContext,
            RemoteInterviewDataSource(RetrofitClient.webService),
            LocalInterviewDataSource(AppDatabase.getDatabase(applicationContext).interviewDao())
        ))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            viewModel.getlist().observe(this@MainActivity,{ list->
                list.let {
                    if(list.isNotEmpty()) {
                        val xd = list[0].streetName
                        Log.d("TAG",xd)
                    } else {
                        Log.d("TAG","vacio")
                    }

                }
            })
        }

    }
}