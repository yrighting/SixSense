package com.sixsense.app.data.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sixsense.app.data.entity.*
import com.sixsense.app.data.dao.*
import kotlinx.coroutines.*
import data.entity.salespost.SalesPost
import data.entity.salespost.SalesPostDao


@Database(
    entities = [Restaurant::class, Review::class, Tag::class, RestaurantTagCrossRef::class, SalesPost::class],
    version = 1
)
abstract class SixsenseDatabase : RoomDatabase() {

    abstract fun restaurantDao(): RestaurantDao
    abstract fun reviewDao(): ReviewDao
    abstract fun salesPostDao(): SalesPostDao


    companion object {
        @Volatile private var INSTANCE: SixsenseDatabase? = null

        fun getDatabase(context: Context): SixsenseDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SixsenseDatabase::class.java,
                    "sixsense_database"
                )
                    .addCallback(DatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabaseCallback : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            CoroutineScope(Dispatchers.IO).launch {
                INSTANCE?.let { db ->
                    val restaurantDao = db.restaurantDao()
                    val reviewDao = db.reviewDao()

                    val rest1 = Restaurant(
                        name = "공릉분식",
                        latitude = 37.6261,
                        longitude = 127.0951,
                        category = "가성비",
                        rating = 4.6f,
                        imageUrl = null,
                        description = "떡볶이, 김밥, 라면 세트가 6천 원! 서울여대생 단골집",
                        isSaleAvailable = true
                    )
                    val rest2 = Restaurant(
                        name = "혼밥국수",
                        latitude = 37.6257,
                        longitude = 127.0962,
                        category = "혼밥",
                        rating = 4.3f,
                        imageUrl = null,
                        description = "1인 테이블 + 셀프국물! 조용히 먹고 갈 수 있는 숨은 국수 맛집",
                        isSaleAvailable = false
                    )

                    val id1 = restaurantDao.insertRestaurant(rest1).toInt()
                    val id2 = restaurantDao.insertRestaurant(rest2).toInt()

                    val tag1 = Tag(tagName = "가성비 갑!")
                    val tag2 = Tag(tagName = "혼밥하기 좋은 곳")
                    val tag3 = Tag(tagName = "개강/종강 축하 맛집")
                    val tag4 = Tag(tagName = "늦게까지 영업하는 맛집")
                    val tag5 = Tag(tagName = "캠퍼스 근처 숨은 맛집")

                    restaurantDao.insertTag(tag1)
                    restaurantDao.insertTag(tag2)
                    restaurantDao.insertTag(tag3)
                    restaurantDao.insertTag(tag4)
                    restaurantDao.insertTag(tag5)

                    restaurantDao.insertCrossRef(RestaurantTagCrossRef(id1, 1))
                    restaurantDao.insertCrossRef(RestaurantTagCrossRef(id1, 3))
                    restaurantDao.insertCrossRef(RestaurantTagCrossRef(id1, 5))
                    restaurantDao.insertCrossRef(RestaurantTagCrossRef(id2, 2))
                    restaurantDao.insertCrossRef(RestaurantTagCrossRef(id2, 4))
                    restaurantDao.insertCrossRef(RestaurantTagCrossRef(id2, 5))

                    val review1 = Review(
                        restaurantId = id1,
                        userName = "지은",
                        rating = 5.0f,
                        comment = "시험 끝나고 떡라세트 먹으면 행복함... 진짜 싸고 맛있어요!",
                        timestamp = System.currentTimeMillis()
                    )
                    val review2 = Review(
                        restaurantId = id2,
                        userName = "수빈",
                        rating = 4.2f,
                        comment = "조용하고 혼밥하기 좋아요. 국물도 진하고 맛있어요.",
                        timestamp = System.currentTimeMillis()
                    )

                    reviewDao.insertReview(review1)
                    reviewDao.insertReview(review2)
                }
            }
        }
    }
} // ✅ 이 한 개만 필요!

// ✅ 이 부분은 클래스 밖 (별도 파일로 해도 좋지만 지금은 여기 유지)
data class RestaurantWithTags(
    @Embedded val restaurant: Restaurant,
    @Relation(
        parentColumn = "id",
        entityColumn = "tagId",
        associateBy = Junction(RestaurantTagCrossRef::class)
    )
    val tags: List<Tag>
)

data class TagWithRestaurants(
    @Embedded val tag: Tag,
    @Relation(
        parentColumn = "tagId",
        entityColumn = "id",
        associateBy = Junction(RestaurantTagCrossRef::class)
    )
    val restaurants: List<Restaurant>
)

