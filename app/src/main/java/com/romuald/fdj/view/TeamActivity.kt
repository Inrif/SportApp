package com.romuald.fdj.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.romuald.fdj.databinding.ActivityTeamBinding
import com.romuald.fdj.model.Team
import com.romuald.fdj.viewmodel.TeamActivityViewModel

class TeamActivity : AppCompatActivity() {

    private lateinit var context: Context
    private lateinit var binding: ActivityTeamBinding
    private lateinit var teamActivityViewModel: TeamActivityViewModel
    private lateinit var team: Team

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        context = this@TeamActivity
        teamActivityViewModel = ViewModelProvider(this).get(TeamActivityViewModel::class.java)
        binding.progressBar.visibility = View.VISIBLE

        val bundle: Bundle? = intent.extras

        bundle?.let {
            bundle.apply {
                val teamName: String? = getString("teamName")
                initTeamData(teamName)
            }
        }

    }

    private fun initTeamData(teamName: String?) {
        binding.progressBar.visibility = View.GONE
        teamActivityViewModel.getTeam(teamName)?.observe(this, Observer { response ->
            val teams = response.teams
            team = teams.first()
            bindingTeamData(team)
        })
    }

    private fun bindingTeamData(team: Team?) {
        binding.progressBar.visibility = View.GONE
        Glide.with(binding.teamBanner)
            .load(team?.strTeamBanner)
            .into(binding.teamBanner)

        binding.teamCountry.text = team?.strCountry
        binding.teamLeague.text = team?.strLeague
        binding.teamDescription.text = team?.strDescriptionFR
        binding.teamName.text = team?.strTeam
    }

    fun back(view: View) {
        finish()
    }

}