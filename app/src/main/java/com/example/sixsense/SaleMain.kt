package com.example.sixsense

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import data.entity.salespost.SalesPost
import data.entity.salespost.SalesPostDaoHelper

class SaleMain : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SalesPostAdapter
    private lateinit var daoHelper: SalesPostDaoHelper
    private lateinit var allPosts: List<SalesPost>

    private val samplePosts = listOf(
        SalesPost(
            restaurantId = "고씨네 카레 서울여대점",
            aliasId = "익명1",
            title = "고씨네 카레 신메뉴 20% 할인 이벤트",
            content = "고씨네에서 매운 돈가스 카레 신메뉴 나왔는데, 지금 출시 기념으로 20% 할인 중이에요! 이번 달까지 하는데 매콤한거나 카레 좋아하시는 분들 한번 드셔보세요~! 계산할 때 자동으로 할인 적용되더라구요!",
            timestamp = System.currentTimeMillis(),
            imageResId = R.drawable.sample1

        ),
        SalesPost(
            restaurantId = "화랑대곱창",
            aliasId = "익명2",
            title = "혼밥 세트 10% 할인 이벤트",
            content = "혼밥하는 분들께 꿀팁! 화랑대곱창에서 곱창 1인 세트 10% 할인 중이에요. 혼자 가도 부담 없고 조용하게 한 끼 먹기 딱 좋아요. 따로 쿠폰 같은 거 없이 그냥 주문하면 할인해줘요. 점심보다 저녁에 사람이 덜 붐비는 듯!",
            timestamp = System.currentTimeMillis(),
            imageResId = R.drawable.sample2

        ),
        SalesPost(
            restaurantId = "육회란 연어다 본점",
            aliasId = "익명3",
            title = "육회·연어 세트 이벤트",
            content = "육회란 연어다 이번 주에 육연세트 시키면 육회 30g 더 줌! 양 꽤 돼서 둘이 나눠 먹기 딱이고, 연어랑 같이 먹으니까 조합도 괜찮은듯! 가성비 좋아서 추천~~",
            timestamp = System.currentTimeMillis(),
            imageResId = R.drawable.sample3

        ),
        SalesPost(
            restaurantId = "카페인중독 노원점",
            aliasId = "익명4",
            title = "생크림 와플 1+1 이벤트",
            content = "아침 수업 전에 카페인중독 들렀는데 생크림 와플 1+1 행사 중이에요! 오전 11시 전까지라 아직 시간도 넉넉하니 와플 좋아하시는 분들 한번 가보세요~~ ",
            timestamp = 1753433800000,
            imageResId = R.drawable.sample4

        ),
        SalesPost(
            restaurantId = "스쿨푸드 딜리버리 태릉입구점",
            aliasId = "익명5",
            title = "떡볶이+마리 세트 20% 할인 이벤트",
            content = "스쿨푸드 떡볶이+마리 세트 지금 20% 할인 중이에요. 배달로 시켜도 할인 적용돼서 오늘 저녁 부담 없이 해결했어요. 세트 좋아하는 분들 한 번 이용해보셔도 좋을 듯!",
            timestamp = System.currentTimeMillis(),
            imageResId = R.drawable.sample5

        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales_main)

        recyclerView = findViewById(R.id.recycler_sales_posts)
        recyclerView.layoutManager = LinearLayoutManager(this)

        daoHelper = SalesPostDaoHelper(this)

        // 중복 방지: DB 비어 있을 때만 샘플 삽입
        if (daoHelper.getAllSalesPosts().isEmpty()) {
            samplePosts.forEach {
                daoHelper.insert(it)
            }
        }

        // DB에서 전체 글 불러오기
        val postList = daoHelper.getAllSalesPosts()

        adapter = SalesPostAdapter(postList.toMutableList())
        recyclerView.adapter = adapter

        val editSearch = findViewById<EditText>(R.id.edit_search)
        val textEmptyResult = findViewById<TextView>(R.id.text_empty_result)

        allPosts = daoHelper.getAllSalesPosts()  // 전역 리스트 저장
        adapter = SalesPostAdapter(allPosts.toMutableList())
        recyclerView.adapter = adapter

        editSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val keyword = s.toString().replace(" ", "")
                val filtered = allPosts.filter {
                    it.restaurantId.replace(" ", "").contains(keyword, ignoreCase = true) ||
                            it.title.replace(" ", "").contains(keyword, ignoreCase = true) ||
                            it.content.replace(" ", "").contains(keyword, ignoreCase = true)
                }


                adapter.items.clear()
                adapter.items.addAll(filtered)
                adapter.notifyDataSetChanged()

                textEmptyResult.visibility = if (filtered.isEmpty()) View.VISIBLE else View.GONE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        })

    }
}



