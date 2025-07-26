package com.example.sixsense

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import data.entity.salespost.SalesPost
import data.entity.salespost.SalesPostDaoHelper
import java.io.File
import java.io.FileOutputStream

class SaleWriteActivity : AppCompatActivity() {

    private lateinit var editRestaurant: EditText
    private lateinit var editTitle: EditText
    private lateinit var editContent: EditText
    private lateinit var imagePreview: ImageView
    private lateinit var btnSelectImage: Button
    private lateinit var btnSubmit: Button

    private var selectedImageUri: Uri? = null

    private lateinit var daoHelper: SalesPostDaoHelper

    // 이미지 선택 런처
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

        // 뷰 연결
        editRestaurant = findViewById(R.id.edit_restaurant_name)
        editTitle = findViewById(R.id.edit_title)
        editContent = findViewById(R.id.edit_content)
        imagePreview = findViewById(R.id.image_preview)
        btnSelectImage = findViewById(R.id.btn_select_image)
        btnSubmit = findViewById(R.id.btn_submit_post)

        daoHelper = SalesPostDaoHelper(this)

        // 이미지 선택 버튼 클릭 시 실행
        btnSelectImage.setOnClickListener {
            imagePickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }

        // 글 등록 버튼 클릭
        btnSubmit.setOnClickListener {
            savePost()
        }
    }

    private fun savePost() {
        val restaurant = editRestaurant.text.toString()
        val title = editTitle.text.toString()
        val content = editContent.text.toString()
        val alias = findViewById<EditText>(R.id.edit_alias_id).text.toString()

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
            imageUri = selectedImageUri?.toString()  // 이미지 경로 저장
        )

        daoHelper.insert(post)
        Toast.makeText(this, "게시글이 등록되었습니다", Toast.LENGTH_SHORT).show()
        setResult(RESULT_OK)
        finish()
    }
}
