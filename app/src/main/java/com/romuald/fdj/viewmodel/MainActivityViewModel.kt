package com.romuald.fdj.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.romuald.fdj.model.responses.LeaguesResponse
import com.romuald.fdj.model.responses.TeamResponse
import com.romuald.fdj.repository.MainActivityRepository

class MainActivityViewModel : ViewModel() {

    var leaguesLiveData: MutableLiveData<LeaguesResponse>? = null
    var teamLiveData: MutableLiveData<TeamResponse>? = null

    fun getLeagues(): LiveData<LeaguesResponse>? {
        leaguesLiveData = MainActivityRepository.getLeagueApiCall()
        return leaguesLiveData
    }

    fun getTeams(league: String): LiveData<TeamResponse>? {
        teamLiveData = MainActivityRepository.getTeamsApiCall(league)
        return teamLiveData
    }
}