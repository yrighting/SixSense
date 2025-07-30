package com.sixsense.app

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.sixsense.app.R
import com.sixsense.app.data.entity.SixsenseDatabase
import com.sixsense.app.data.entity.TagDao
import data.entity.salespost.SalesPost
import data.entity.salespost.SalesPostTagCrossRef
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class SaleWriteActivity : AppCompatActivity() {
    private lateinit var chipGroup: ChipGroup
    private lateinit var tagDao: TagDao
    private val selectedTagIds = mutableListOf<Int>()

    private lateinit var editRestaurant: EditText
    private lateinit var editTitle: EditText
    private lateinit var editContent: EditText
    private lateinit var editAliasId: EditText
    private lateinit var imagePreview: ImageView
    private lateinit var btnSelectImage: Button
    private lateinit var btnSubmit: Button

    private lateinit var btnSelectLocation: Button
    private lateinit var textSelectedLocation: TextView
    private var selectedLatitude: Double? = null
    private var selectedLongitude: Double? = null

    private var selectedImageUri: Uri? = null

    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
            uri?.let {
                val safeUri = copyToInternalStorage(this, it)
                imagePreview.setImageURI(safeUri)
                selectedImageUri = safeUri
            }
        }

    private fun copyToInternalStorage(context: Context, uri: Uri): Uri {
        val inputStream = context.contentResolver.openInputStream(uri)!!
        val file = File(context.filesDir, "img_${System.currentTimeMillis()}.jpg")
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)
        inputStream.close()
        outputStream.close()
        return Uri.fromFile(file)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sale_write)

        // 뷰 초기화
        editRestaurant = findViewById(R.id.edit_restaurant_name)
        editTitle = findViewById(R.id.edit_title)
        editContent = findViewById(R.id.edit_content)
        editAliasId = findViewById(R.id.edit_alias_id)
        imagePreview = findViewById(R.id.image_preview)
        btnSelectImage = findViewById(R.id.btn_select_image)
        btnSubmit = findViewById(R.id.btn_submit_post)
        chipGroup = findViewById(R.id.tagChipGroup)
        btnSelectLocation = findViewById(R.id.btn_select_location)
        textSelectedLocation = findViewById(R.id.text_selected_location)

        tagDao = SixsenseDatabase.getDatabase(applicationContext).tagDao()

        // 태그 chip 생성
        lifecycleScope.launch {
            val tagList = tagDao.getAllTags()
            for (tag in tagList) {
                val chip = Chip(this@SaleWriteActivity).apply {
                    text = tag.tagName
                    isCheckable = true
                    this.tag = tag.tagId
                }
                chipGroup.addView(chip)
            }
        }

        btnSelectImage.setOnClickListener {
            imagePickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }

        val locationSelectLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val lat = result.data?.getDoubleExtra("latitude", 0.0)
                    val lng = result.data?.getDoubleExtra("longitude", 0.0)

                    if (lat != null && lng != null) {
                        selectedLatitude = lat
                        selectedLongitude = lng
                        textSelectedLocation.text = "선택된 위치: $lat, $lng"
                    }
                }
            }

        btnSelectLocation.setOnClickListener {
            val intent = Intent(this, MapSelectActivity::class.java)
            intent.putExtra("restaurantName", editRestaurant.text.toString())
            locationSelectLauncher.launch(intent)
        }

        btnSubmit.setOnClickListener {
            savePost()
        }
    }

    private fun savePost() {
        val restaurant = editRestaurant.text.toString()
        val title = editTitle.text.toString()
        val content = editContent.text.toString()
        val alias = editAliasId.text.toString()

        if (restaurant.isBlank() || title.isBlank() || content.isBlank()) {
            Toast.makeText(this, "모든 항목을 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        val post = SalesPost(
            restaurantId = restaurant,
            aliasId = alias,
            title = title,
            content = content,
            timestamp = System.currentTimeMillis(),
            imageUri = selectedImageUri?.toString(),
            latitude = selectedLatitude,
            longitude = selectedLongitude
        )

        lifecycleScope.launch {
            val db = SixsenseDatabase.getDatabase(applicationContext)
            val salesPostDao = db.salesPostDao()
            val postId = salesPostDao.insert(post).toInt()

            selectedTagIds.clear()
            for (i in 0 until chipGroup.childCount) {
                val chip = chipGroup.getChildAt(i) as Chip
                if (chip.isChecked) {
                    selectedTagIds.add(chip.tag as Int)
                }
            }

            selectedTagIds.forEach { tagId ->
                salesPostDao.insertSalesPostTagCrossRef(
                    SalesPostTagCrossRef(postId, tagId)
                )
            }

            Toast.makeText(this@SaleWriteActivity, "게시글이 등록되었습니다", Toast.LENGTH_SHORT).show()

            val resultIntent = Intent().apply {
                putExtra("latitude", selectedLatitude ?: 0.0)
                putExtra("longitude", selectedLongitude ?: 0.0)
                setResult(RESULT_OK)
            }

            finish()
        }
    }
}
