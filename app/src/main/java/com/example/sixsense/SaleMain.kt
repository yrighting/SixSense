package com.example.sixsense

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import data.entity.salespost.SalesPost
import data.entity.salespost.SalesPostDaoHelper

class SaleMain : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SalesPostAdapter
    private lateinit var daoHelper: SalesPostDaoHelper

    private val samplePosts = listOf(
        SalesPost(
            postId = 0,
            restaurantId = "고씨네 카레 서울여대점",
            aliasId = "익명1",
            title = "고씨네 카레 신메뉴 이벤트",
            content = "'버터 치킨 카레’ 출시 기념 1주일간 20% 할인",
            timestamp = System.currentTimeMillis()
        ),
        SalesPost(
            postId = 0,
            restaurantId = "화랑대곱창",
            aliasId = "익명2",
            title = "혼밥 세트 이벤트",
            content = "곱창 1인 세트 주문 시 10% 할인!",
            timestamp = System.currentTimeMillis()
        ),
        SalesPost(
            postId = 0,
            restaurantId = "육회란 연어다 본점",
            aliasId = "익명3",
            title = "육회·연어 세트 이벤트",
            content = "육회·연어 세트 주문 시 육회 30g 추가 무료!",
            timestamp = System.currentTimeMillis()
        ),
        SalesPost(
            postId = 0,
            restaurantId = "카페인중독 노원점",
            aliasId = "익명4",
            title = "아메리카노 1+1 이벤트",
            content = "오전 10시까지 아메리카노 주문 시 1잔 무료 추가",
            timestamp = System.currentTimeMillis()
        ),
        SalesPost(
            postId = 0,
            restaurantId = "스쿨푸드 딜리버리 태릉입구점",
            aliasId = "익명5",
            title = "떡볶이+마리 세트 이벤트",
            content = "떡볶이+마리 세트 주문 시 20% 할인!",
            timestamp = System.currentTimeMillis()
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
    }
}



