package com.sixsense.app

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sixsense.CommentAdapter
import com.sixsense.app.R
import com.sixsense.app.data.entity.SixsenseDatabase
import data.entity.party.Participation
import data.entity.party.PartyComment
import data.entity.party.PartyPost
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PartyDetailActivity : AppCompatActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var tvContent: TextView
    private lateinit var tvLocation: TextView
    private lateinit var tvDatetime: TextView
    private lateinit var tvPeople: TextView
    private lateinit var btnJoin: Button
    private lateinit var commentInput: EditText
    private lateinit var btnAddComment: Button
    private lateinit var commentRecycler: RecyclerView

    private lateinit var db: SixsenseDatabase
    private var postId: Int = -1
    private lateinit var post: PartyPost

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_party_detail)

        db = SixsenseDatabase.getDatabase(this)

        postId = intent.getIntExtra("postId", -1)
        val userId = intent.getStringExtra("userId") ?: "익명"

        // UI 요소들 초기화
        tvTitle = findViewById(R.id.tv_title)
        tvContent = findViewById(R.id.tv_content)
        tvLocation = findViewById(R.id.tv_location)
        tvDatetime = findViewById(R.id.tv_datetime)
        tvPeople = findViewById(R.id.tv_people)
        btnJoin = findViewById(R.id.btn_join)
        commentInput = findViewById(R.id.input_comment)
        btnAddComment = findViewById(R.id.btn_add_comment)
        commentRecycler = findViewById(R.id.recycler_comment)
        commentRecycler.layoutManager = LinearLayoutManager(this)

        // DB에서 post 정보 불러오기
        CoroutineScope(Dispatchers.IO).launch {
            post = db.partyDao().getPartyPostById(postId)

            withContext(Dispatchers.Main) {
                tvTitle.text = post.title
                tvContent.text = post.content
                tvLocation.text = "장소: ${post.location}"
                tvDatetime.text = "시간: ${post.date} ${post.time}"
                tvPeople.text = "인원: ${post.currentPeople} / ${post.maxPeople}"

                if (post.currentPeople >= post.maxPeople) {
                    btnJoin.isEnabled = false
                    btnJoin.text = "마감됨"
                }

                loadComments()
            }
        }

        btnJoin.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val alreadyJoined = db.partyDao().isUserAlreadyJoined(postId, userId)
                if (alreadyJoined) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@PartyDetailActivity, "이미 참여한 모임입니다.", Toast.LENGTH_SHORT).show()
                    }
                    return@launch
                }

                if (post.currentPeople < post.maxPeople) {
                    val updated = post.copy(currentPeople = post.currentPeople + 1)
                    db.partyDao().updatePartyPost(updated)
                    val participation = Participation(postId = postId, userId = userId)
                    db.partyDao().insertParticipation(participation)
                    post = updated

                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@PartyDetailActivity, "참여 완료!", Toast.LENGTH_SHORT).show()
                        tvPeople.text = "인원: ${updated.currentPeople} / ${updated.maxPeople}"

                        if (post.currentPeople >= post.maxPeople) {
                            btnJoin.isEnabled = false
                            btnJoin.text = "마감됨"
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@PartyDetailActivity, "정원 초과입니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        btnAddComment.setOnClickListener {
            val content = commentInput.text.toString()
            if (content.isNotBlank()) {
                val currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())
                CoroutineScope(Dispatchers.IO).launch {
                    val comment = PartyComment(postId = postId, author = userId, content = content, timestamp = currentTime)
                    db.partyDao().insertComment(comment)

                    withContext(Dispatchers.Main) {
                        commentInput.text.clear()
                        loadComments()
                    }
                }
            }
        }
    }

    private fun loadComments() {
        CoroutineScope(Dispatchers.IO).launch {
            val comments = db.partyDao().getCommentsForPost(postId).sortedByDescending { it.timestamp }
            withContext(Dispatchers.Main) {
                commentRecycler.adapter = CommentAdapter(comments)
            }
        }
    }
}
