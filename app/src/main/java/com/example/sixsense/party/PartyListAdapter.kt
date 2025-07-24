package com.example.sixsense.party

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sixsense.R

// 파티 목록을 보여주는 RecyclerView 어댑터
class PartyListAdapter(
    private val partyDataList: List<PartyData>,
    private val onItemClick: (PartyData) -> Unit
) : RecyclerView.Adapter<PartyListAdapter.PartyViewHolder>() {

    // 뷰홀더: 파티 아이템 1개 표시 담당
    inner class PartyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.tvPartyTitle)

        init {
            // 아이템 클릭 시 콜백 호출
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(partyDataList[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_party, parent, false)
        return PartyViewHolder(view)
    }

    override fun onBindViewHolder(holder: PartyViewHolder, position: Int) {
        holder.titleTextView.text = partyDataList[position].title
    }

    override fun getItemCount(): Int = partyDataList.size
}
