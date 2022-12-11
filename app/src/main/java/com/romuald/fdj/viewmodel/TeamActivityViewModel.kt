package com.romuald.fdj.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.romuald.fdj.model.responses.TeamResponse
import com.romuald.fdj.repository.TeamActivityRepository

class TeamActivityViewModel : ViewModel() {

    var teamLiveData: MutableLiveData<TeamResponse>? = null

    fun getTeam(team: String?): LiveData<TeamResponse>? {
        teamLiveData = TeamActivityRepository.getTeamApiCall(team)
        return teamLiveData
    }
}