import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sixsense.PartyListAdapter
import com.sixsense.app.PartyDetailActivity
import com.sixsense.app.R
import com.sixsense.app.data.entity.SixsenseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyPartiesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var db: SixsenseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_parties)

        db = SixsenseDatabase.getDatabase(this)
        val userId = intent.getStringExtra("userId") ?: "익명"

        recyclerView = findViewById(R.id.recycler_my_parties)
        recyclerView.layoutManager = LinearLayoutManager(this)

        CoroutineScope(Dispatchers.IO).launch {
            val myPosts = db.partyDao().getMyParticipatedPosts(userId)
            withContext(Dispatchers.Main) {
                recyclerView.adapter = PartyListAdapter(myPosts) { party ->
                    val intent = Intent(this@MyPartiesActivity, PartyDetailActivity::class.java)
                    intent.putExtra("postId", party.id)
                    intent.putExtra("userId", userId)
                    startActivity(intent)
                }
            }
        }
    }
}
