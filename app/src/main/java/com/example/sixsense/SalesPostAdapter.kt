package com.sixsense.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import data.entity.salespost.SalesPost
import java.text.SimpleDateFormat
import java.util.*

class SalesPostAdapter(
    val items: MutableList<SalesPost>,  // 좋아요 수 업데이트 위해 List → MutableList 사용
) : RecyclerView.Adapter<SalesPostAdapter.SalesPostViewHolder>() {

    // ViewHolder: 아이템 레이아웃 내 뷰 요소들을 바인딩해줌
    class SalesPostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textRestaurant: TextView = itemView.findViewById(R.id.text_item_restaurant)
        val textWriter: TextView = itemView.findViewById(R.id.text_item_writer)
        val title: TextView = itemView.findViewById(R.id.text_item_title)
        val textTime: TextView = itemView.findViewById(R.id.text_item_time)
        val textLikeCount: TextView = itemView.findViewById(R.id.text_like_count)
    }

    // ViewHolder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalesPostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sales_post, parent, false)
        return SalesPostViewHolder(view)
    }

    // ViewHolder에 데이터 바인딩
    override fun onBindViewHolder(holder: SalesPostViewHolder, position: Int) {
        val post = items[position]

        // 텍스트 데이터 설정
        holder.textWriter.text = post.aliasId
        holder.textRestaurant.text = getRestaurantName(post.restaurantId)
        holder.title.text = post.title
        holder.textTime.text = formatTimestamp(post.timestamp)
        holder.textLikeCount.text = post.likeCount.toString()


        // 아이템 클릭 시 상세화면(SaleInfo)으로 이동 + 데이터 전달
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = android.content.Intent(context, com.sixsense.app.SaleInfo::class.java).apply {
                putExtra("postId", post.postId)
                putExtra("title", post.title)
                putExtra("restaurantName", getRestaurantName(post.restaurantId))
                putExtra("writerId", post.aliasId)
                putExtra("time", formatTimestamp(post.timestamp))
                putExtra("content", post.content)
                putExtra("likeCount", post.likeCount)
                putExtra("imageResId", post.imageResId)
                if (post.imageUri != null) {
                    putExtra("imageUri", post.imageUri)
                }
            }
            context.startActivity(intent)
        }
    }

    // 아이템 개수 반환
    override fun getItemCount(): Int = items.size

    // 타임스탬프 → "yyyy-MM-dd HH:mm" 형식으로 변환
    private fun formatTimestamp(timestamp: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

    // restaurantId 그대로 반환
    private fun getRestaurantName(restaurantId: String): String {
        return restaurantId
    }
}
