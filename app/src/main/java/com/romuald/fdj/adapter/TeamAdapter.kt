package com.romuald.fdj.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.romuald.fdj.databinding.TeamLayoutBinding
import com.romuald.fdj.model.Team
import com.romuald.fdj.view.MainActivity


class TeamAdapter : RecyclerView.Adapter<TeamAdapter.ViewHolder>(), View.OnClickListener {
    private var teamList = ArrayList<Team>()

    private var listener: MainActivity? = null

    fun setTeamList(teamList: ArrayList<Team>, listener: MainActivity) {
        this.teamList = teamList
        this.listener = listener
        notifyDataSetChanged()
    }

    interface TeamClickListener {
        fun onTeamClicked(teamName: String)
    }

    class ViewHolder(private val binding: TeamLayoutBinding, private val listener: MainActivity?) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private lateinit var team: Team

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(position: Int, teamList: ArrayList<Team>) {
            team = teamList[position]
            Glide.with(this.itemView)
                .load(teamList[position].strTeamBadge)
                .into(binding.movieImage)
        }

        override fun onClick(v: View?) {
            listener?.onTeamClicked(team.strTeam)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TeamLayoutBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            ),
            listener = listener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        (holder).bind(position, teamList)

    }

    override fun getItemCount(): Int {
        return teamList.size
    }

    override fun onClick(v: View?) {
//        Do nothing
    }
}
