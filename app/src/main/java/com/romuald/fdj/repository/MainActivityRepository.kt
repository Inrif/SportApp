package com.romuald.fdj.repository

import androidx.lifecycle.MutableLiveData
import com.romuald.fdj.model.responses.LeaguesResponse
import com.romuald.fdj.model.responses.TeamResponse
import com.romuald.fdj.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

object MainActivityRepository {

    val leagues = MutableLiveData<LeaguesResponse>()
    val teams = MutableLiveData<TeamResponse>()

    fun getLeagueApiCall(): MutableLiveData<LeaguesResponse> {

        val call = RetrofitClient.apiInterface.getAllLeague()

        call.enqueue(object : Callback<LeaguesResponse> {
            override fun onFailure(call: Call<LeaguesResponse>, t: Throwable) {
                Timber.v(t.message.toString())
            }

            override fun onResponse(
                call: Call<LeaguesResponse>,
                response: Response<LeaguesResponse>
            ) {
                Timber.v(response.body().toString())

                val data = response.body()

                leagues.value = LeaguesResponse(data!!.leagues)

            }
        })

        return leagues
    }

    fun getTeamsApiCall(league: String): MutableLiveData<TeamResponse> {

        val call = RetrofitClient.apiInterface.getAllTeam(league)

        call.enqueue(object : Callback<TeamResponse> {
            override fun onFailure(call: Call<TeamResponse>, t: Throwable) {
                Timber.v(t.message.toString())
            }

            override fun onResponse(
                call: Call<TeamResponse>,
                response: Response<TeamResponse>
            ) {
                Timber.v(response.body().toString())

                val data = response.body()

                teams.value = data?.let { TeamResponse(it.teams) }

            }
        })

        return teams
    }

}

