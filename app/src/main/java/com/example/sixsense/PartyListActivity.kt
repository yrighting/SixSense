package com.sixsense.app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sixsense.PartyAdapter
import com.sixsense.app.R
import com.sixsense.app.data.entity.SixsenseDatabase
import data.entity.party.PartyPost
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PartyListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PartyAdapter
    private lateinit var db: SixsenseDatabase
    private lateinit var allPosts: List<PartyPost>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_party_list)

        db = SixsenseDatabase.getDatabase(this)
        recyclerView = findViewById(R.id.recycler_party_list)

        val btnCreate = findViewById<Button>(R.id.btnCreateParty)
        btnCreate.setOnClickListener {
            val intent = Intent(this, CreateParty::class.java)
            startActivity(intent)
        }

        val etSearch = findViewById<EditText>(R.id.etSearch)
        val spinnerSort = findViewById<Spinner>(R.id.spinnerSort)

        // onItemClick 콜백 설정
        adapter = PartyAdapter { post ->
            val intent = Intent(this, PartyDetailActivity::class.java)
            intent.putExtra("postId", post.id)
            intent.putExtra("userId", "익명") // 유저 ID 전달 필요 시 수정
            startActivity(intent)
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        allPosts = emptyList()

        etSearch.addTextChangedListener {
            val filtered = allPosts.filter {
                it.title.contains(etSearch.text, ignoreCase = true)
            }
            adapter.submitList(filtered)
        }

        spinnerSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val sorted = when (position) {
                    1 -> allPosts.sortedBy { it.title }
                    2 -> allPosts.sortedByDescending { it.capacity }
                    else -> allPosts.sortedByDescending { it.id }
                }
                adapter.submitList(sorted)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        CoroutineScope(Dispatchers.IO).launch {
            val posts = db.partyDao().getAllPartyPosts()
            withContext(Dispatchers.Main) {
                allPosts = posts
                adapter.submitList(allPosts)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        CoroutineScope(Dispatchers.IO).launch {
            val posts = db.partyDao().getAllPartyPosts()
            Log.i("PartyListActivity", "DB에서 ${posts.size}개의 파티 글을 불러왔습니다.")
            withContext(Dispatchers.Main) {
                allPosts = posts
                adapter.submitList(allPosts)
            }
        }
    }
}
