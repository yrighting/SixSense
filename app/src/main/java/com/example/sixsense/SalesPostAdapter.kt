package com.example.sixsense

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import data.entity.salespost.SalesPost
import java.text.SimpleDateFormat
import java.util.*

class SalesPostAdapter(
    private val items: MutableList<SalesPost>,  // 좋아요 수 업데이트 위해 List → MutableList
    private val onLikeClicked: (SalesPost) -> Unit  // 좋아요 눌렀을 때 DB 반영용 콜백
) : RecyclerView.Adapter<SalesPostAdapter.SalesPostViewHolder>() {

    class SalesPostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textRestaurant: TextView = itemView.findViewById(R.id.text_item_restaurant)
        val textWriter: TextView = itemView.findViewById(R.id.text_item_writer)
        val textContent: TextView = itemView.findViewById(R.id.text_item_content)
        val textTime: TextView = itemView.findViewById(R.id.text_item_time)
        val textLikeCount: TextView = itemView.findViewById(R.id.text_like_count)
        val imageLike: ImageView = itemView.findViewById(R.id.image_like)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalesPostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sales_post, parent, false)
        return SalesPostViewHolder(view)
    }

    override fun onBindViewHolder(holder: SalesPostViewHolder, position: Int) {
        val post = items[position]

        holder.textWriter.text = post.aliasId
        holder.textRestaurant.text = getRestaurantName(post.restaurantId)
        holder.textContent.text = post.content
        holder.textTime.text = formatTimestamp(post.timestamp)

        holder.textLikeCount.text = post.likeCount.toString()  // 좋아요 수 표시

        holder.imageLike.setOnClickListener {
            post.likeCount += 1
            holder.textLikeCount.text = post.likeCount.toString()
        }

    }

    override fun getItemCount(): Int = items.size

    private fun formatTimestamp(timestamp: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

    private fun getRestaurantName(restaurantId: Int): String {
        return "맛집 #$restaurantId"
    }
}
