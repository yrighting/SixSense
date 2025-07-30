package com.sixsense.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sixsense.R

class ReviewAdapter(private val reviewList: List<Review>) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textUserName: TextView = itemView.findViewById(R.id.textUserName)
        val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
        val textDate: TextView = itemView.findViewById(R.id.textDate)
        val textReview: TextView = itemView.findViewById(R.id.textReview)
        val textTags: TextView = itemView.findViewById(R.id.textTags)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.review_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = reviewList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val review = reviewList[position]
        holder.textUserName.text = review.userName
        holder.ratingBar.rating = review.rating
        holder.textDate.text = review.date
        holder.textReview.text = review.text
        holder.textTags.text = review.tags.joinToString(" ") { "#$it" }
    }
}
