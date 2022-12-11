package com.romuald.fdj.retrofit

import com.romuald.fdj.common.Constant.URL_ALL_LEAGUE
import com.romuald.fdj.common.Constant.URL_ALL_TEAM
import com.romuald.fdj.common.Constant.URL_ONE_TEAM
import com.romuald.fdj.model.responses.LeaguesResponse
import com.romuald.fdj.model.responses.TeamResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {

    @GET(URL_ALL_LEAGUE)
    fun getAllLeague(): Call<LeaguesResponse>

    @GET(URL_ALL_TEAM)
    fun getAllTeam(@Query("l") league: String): Call<TeamResponse>

    @GET(URL_ONE_TEAM)
    fun getOneTeam(@Query("t") team: String?): Call<TeamResponse>

}