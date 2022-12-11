package com.romuald.fdj.repository

import androidx.lifecycle.MutableLiveData
import com.romuald.fdj.model.responses.TeamResponse
import com.romuald.fdj.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

object TeamActivityRepository {

    val team_data = MutableLiveData<TeamResponse>()

    fun getTeamApiCall(team: String?): MutableLiveData<TeamResponse> {
        val call = RetrofitClient.apiInterface.getOneTeam(team)

        call.enqueue(object : Callback<TeamResponse> {
            override fun onFailure(call: Call<TeamResponse>, t: Throwable) {
                Timber.v(t.message.toString())
            }

            override fun onResponse(
                call: Call<TeamResponse>,
                response: Response<TeamResponse>
            ) {
                val data = response.body()
                team_data.value = data?.let { TeamResponse(it.teams) }

            }
        })

        return team_data
    }

}

