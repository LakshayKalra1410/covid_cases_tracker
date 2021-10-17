package com.example.covid_cases_tracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.meracovidtracker.R

class StateRvAdapter (private val stateList:List<StateModel>):RecyclerView.Adapter<StateRvAdapter.StatRVViewHolder>(){

    class StatRVViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val stateNameTV:TextView=itemView.findViewById(R.id.idRVStates)
        val casesTv:TextView=itemView.findViewById(R.id.idTVCases)
        val deathTV:TextView=itemView.findViewById(R.id.idTVDeath)
        val recoveredTV:TextView=itemView.findViewById(R.id.idTVRecovered)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatRVViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.state_rv_item,parent,false)
        return  StatRVViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StatRVViewHolder, position: Int) {
        val stateData=stateList[position]
        holder.casesTv.text=stateData.state
        holder.stateNameTV.text=stateData.state
        holder.recoveredTV.text=stateData.cases.toString()
        holder.deathTV.text=stateData.death.toString()


    }

    override fun getItemCount(): Int {
        return  stateList.size
    }
}