package com.example.sixsense.party

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sixsense.R

// 댓글 목록을 RecyclerView에 표시하기 위한 Adapter 클래스
class CommentAdapter(private val commentList: List<Comment>) :
    RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    // 뷰 홀더: 댓글 한 개 아이템 뷰를 관리
    class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val authorText: TextView = itemView.findViewById(R.id.commentAuthor)
        val contentText: TextView = itemView.findViewById(R.id.commentContent)
        val timestampText: TextView = itemView.findViewById(R.id.commentTimestamp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_comment, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = commentList[position]
        holder.authorText.text = comment.author
        holder.contentText.text = comment.content
        holder.timestampText.text = comment.timestamp
    }

    override fun getItemCount(): Int = commentList.size
}
