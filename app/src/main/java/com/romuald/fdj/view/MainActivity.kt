package com.romuald.fdj.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.romuald.fdj.R
import com.romuald.fdj.adapter.TeamAdapter
import com.romuald.fdj.databinding.ActivityMainBinding
import com.romuald.fdj.model.Team
import com.romuald.fdj.viewmodel.MainActivityViewModel


class MainActivity : AppCompatActivity(), TeamAdapter.TeamClickListener {

    lateinit var context: Context

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var teamAdapter: TeamAdapter
    private lateinit var listAdapter: ArrayAdapter<String>
    var leaguesList = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        context = this@MainActivity
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        binding.progressBar.visibility = View.VISIBLE
        displayLeaguesList()
        setupRecyclerView()
    }

    private fun displayLeaguesList() {
        mainActivityViewModel.getLeagues()?.observe(this, Observer { response ->
            binding.progressBar.visibility = View.GONE
            val leagues = response.leagues
            for (league in leagues) {
                leaguesList.add(league.strLeague)
            }

            listAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, leaguesList)
            binding.userList.adapter = listAdapter

            binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String?): Boolean {
                    binding.searchBar.clearFocus()
                    if (leaguesList.contains(query)) {

                        listAdapter.filter.filter(query)
                    } else {
                        Toast.makeText(this@MainActivity, R.string.toast_message, Toast.LENGTH_LONG)
                            .show()
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    binding.userList.visibility = View.VISIBLE
                    listAdapter.filter.filter(newText)
                    return false
                }
            })

        })
        binding.userList.onItemClickListener = OnItemClickListener { _, _, position, _ ->
            binding.searchBar.setQuery(leaguesList[position], false)
            binding.userList.visibility = View.GONE
            initTeams(leaguesList[position])

        }

    }

    private fun initTeams(league: String) {
        binding.progressBar.visibility = View.GONE
        mainActivityViewModel.getTeams(league)?.observe(this, Observer { response ->
            val teams = response.teams
            teamAdapter.setTeamList(teams as ArrayList<Team>, this)

        })
    }

    private fun setupRecyclerView() {
        teamAdapter = TeamAdapter()
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(applicationContext, 2)
            adapter = teamAdapter
        }
    }

    override fun onTeamClicked(teamName: String) {
        val intent = Intent(this, TeamActivity::class.java)
        intent.putExtra("teamName", teamName)
        startActivity(intent)
    }

}

