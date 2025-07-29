package com.example.sixsense

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sixsense.app.R
import data.entity.party.PartyComment

class CommentAdapter(private val commentList: List<PartyComment>) :
    RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvAuthor: TextView = itemView.findViewById(R.id.tv_comment_author)
        val tvContent: TextView = itemView.findViewById(R.id.tv_comment_content)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_comment, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = commentList[position]
        holder.tvAuthor.text = comment.author
        holder.tvContent.text = comment.content
    }

    override fun getItemCount(): Int {
        return commentList.size
    }
}