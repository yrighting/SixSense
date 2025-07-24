package com.example.sixsense.party

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sixsense.R
import com.example.sixsense.party.DummyData
import com.example.sixsense.party.PartyData
import com.example.sixsense.party.Comment
import com.example.sixsense.party.CommentAdapter

// 파티 상세 정보를 보여주고, 댓글을 조회 및 작성할 수 있는 화면
class PartyDetailActivity : AppCompatActivity() {

    private lateinit var commentAdapter: CommentAdapter
    private lateinit var commentRecyclerView: RecyclerView
    private lateinit var commentInput: EditText
    private lateinit var commentSendButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_party_detail)

        // View 바인딩
        val titleTextView: TextView = findViewById(R.id.partyDetailTitle)
        val descriptionTextView: TextView = findViewById(R.id.partyDetailDescription)
        val locationTextView: TextView = findViewById(R.id.tvPartyLocation)
        val hostTextView: TextView = findViewById(R.id.tvPartyHost)
        val joinButton: Button = findViewById(R.id.btnJoinParty)

        commentInput = findViewById(R.id.commentInput)
        commentSendButton = findViewById(R.id.commentSendButton)
        commentRecyclerView = findViewById(R.id.commentRecyclerView)

        // PartyList에서 넘길 때 키를 "partyId"로 맞춰야 함 (여기와 동일하게)
        val partyId = intent.getIntExtra("partyId", -1)

        // DummyData.getParties() 반환 타입이 List<PartyData> 라면 이렇게 찾기
        val party: PartyData? = DummyData.getParties().find { it.id == partyId }

        if (party == null) {
            Toast.makeText(this, "파티 정보를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // UI에 파티 정보 표시
        titleTextView.text = party.title
        descriptionTextView.text = party.description
        locationTextView.text = "장소: ${party.location}"
        hostTextView.text = "주최자: ${party.host}"

        // TODO: 실제 참여 기능 구현 필요
        joinButton.setOnClickListener {
            Toast.makeText(this, "모임에 참여하였습니다!", Toast.LENGTH_SHORT).show()
        }

        // 댓글 리스트 초기화
        val commentList = DummyData.getCommentsForParty(partyId)

        // 댓글 어댑터 세팅
        commentAdapter = CommentAdapter(commentList)
        commentRecyclerView.layoutManager = LinearLayoutManager(this)
        commentRecyclerView.adapter = commentAdapter

        // 댓글 전송 버튼 클릭 이벤트
        commentSendButton.setOnClickListener {
            val commentText = commentInput.text.toString().trim()
            if (commentText.isNotEmpty()) {
                val newComment = Comment(
                    id = 0, // DB 사용하면 자동 증가값 넣기
                    partyId = partyId,
                    author = "사용자",  // 실제 앱에서는 로그인 사용자 이름
                    content = commentText,
                    timestamp = "방금 전"
                )
                // 댓글 리스트에 추가
                (commentList as MutableList).add(newComment)

                // 어댑터에 알림 및 스크롤
                commentAdapter.notifyItemInserted(commentList.size - 1)
                commentRecyclerView.scrollToPosition(commentList.size - 1)
                commentInput.text.clear()
            } else {
                Toast.makeText(this, "댓글을 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
