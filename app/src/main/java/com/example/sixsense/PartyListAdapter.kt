package com.example.sixsense

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import data.entity.party.PartyPost
import com.sixsense.app.R

class PartyListAdapter(
    private val partyList: List<PartyPost>,
    private val onItemClick: (PartyPost) -> Unit
) : RecyclerView.Adapter<PartyListAdapter.PartyViewHolder>() {

    inner class PartyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.tv_title)
        val locationText: TextView = itemView.findViewById(R.id.tv_location)
        val datetimeText: TextView = itemView.findViewById(R.id.tv_datetime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_party_post, parent, false)
        return PartyViewHolder(view)
    }

    override fun onBindViewHolder(holder: PartyViewHolder, position: Int) {
        val party = partyList[position]
        holder.titleText.text = party.title
        holder.locationText.text = party.location
        holder.datetimeText.text = "${party.date} ${party.time}"

        holder.itemView.setOnClickListener {
            onItemClick(party)
        }
    }

    override fun getItemCount(): Int = partyList.size
}
