package com.example.sixsense

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sixsense.app.R
import data.entity.party.PartyPost

class PartyAdapter(
    private val onItemClick: (PartyPost) -> Unit
) : ListAdapter<PartyPost, PartyAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PartyPost>() {
            override fun areItemsTheSame(oldItem: PartyPost, newItem: PartyPost): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PartyPost, newItem: PartyPost): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tv_title)
        val tvLocation: TextView = view.findViewById(R.id.tv_location)
        val tvDatetime: TextView = view.findViewById(R.id.tv_datetime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_party_post, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = getItem(position)
        holder.tvTitle.text = post.title
        holder.tvLocation.text = post.location
        holder.tvDatetime.text = "${post.date} ${post.time}"

        holder.itemView.setOnClickListener {
            onItemClick(post)
        }
    }
}
