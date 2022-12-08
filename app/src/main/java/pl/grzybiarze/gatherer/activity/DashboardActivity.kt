package pl.grzybiarze.gatherer.activity

import android.app.Activity
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pl.grzybiarze.gatherer.R
import pl.grzybiarze.gatherer.adapters.FollowersAdapter
import pl.grzybiarze.gatherer.adapters.PostAdapter
import pl.grzybiarze.gatherer.helper_classes.Followers
import pl.grzybiarze.gatherer.helper_classes.Post
import java.text.DateFormat
import java.util.*

class DashboardActivity : AppCompatActivity() {

    val TAG = "DashboardActivity"

    lateinit var setLocalizationButton: ImageButton
    lateinit var addImageButton: ImageButton
    lateinit var posts: MutableList<Post>
    lateinit var postAdapter: PostAdapter

    var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        setLocalizationButton = findViewById(R.id.localizationPinButton)
        addImageButton = findViewById(R.id.addImageButton)

        var followers = mutableListOf(
            Followers("KW"),
            Followers("KS"),
            Followers("AP"),
            Followers("MK"),
            Followers("FB"),
            Followers("JK"),
            Followers("MC"),
            Followers("VM"),
            Followers("JP"),
            Followers("PD"),
            Followers("TJ"),
        )

        val followersAdapter = FollowersAdapter(followers)
        val recyclerViewFollowers = findViewById<RecyclerView>(R.id.userFollowers)
        recyclerViewFollowers.adapter = followersAdapter
        recyclerViewFollowers.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        posts = mutableListOf(
            Post(5, 10,
                DateFormat.getDateInstance(DateFormat.MEDIUM).format(Date(2022, 11, 17)),
                "Skierniewice",
                "Kamil",
                "KW",
                "Grzyb",
                "",
                null
            ),
            Post(5, 10,
                DateFormat.getDateInstance(DateFormat.MEDIUM).format(Date(2022, 11, 17)),
                "Skierniewice",
                "Kamil",
                "KW",
                "Grzyb",
                "",
                null
            ),
            Post(5, 10,
                DateFormat.getDateInstance(DateFormat.MEDIUM).format(Date(2022, 11, 17)),
                "Skierniewice",
                "Kamil",
                "KW",
                "Grzyb",
                "",
                null
            )
        )

        addImageButton.setOnClickListener {
            openGalleryFolder()
        }

        postAdapter = PostAdapter(posts)
        val recyclerViewPosts = findViewById<RecyclerView>(R.id.usersPosts)
        recyclerViewPosts.adapter = postAdapter
        recyclerViewPosts.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun openGalleryFolder() {
        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = "image/*"
        try {
            galleryResult.launch(galleryIntent)
        } catch (e: Exception) {
            Log.e(TAG, "openGalleryFolder: $e")
        }
    }

    var galleryResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            posts[0].postPicture = data?.data
            posts[0].localization = "Warszawa"
            postAdapter.notifyItemChanged(0)
        }
    }

}