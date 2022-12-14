package pl.grzybiarze.gatherer.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pl.grzybiarze.gatherer.R
import pl.grzybiarze.gatherer.adapters.FollowersAdapter
import pl.grzybiarze.gatherer.adapters.PostAdapter
import pl.grzybiarze.gatherer.helper_classes.Followers
import pl.grzybiarze.gatherer.helper_classes.Post
import java.time.LocalDateTime

class DashboardActivity : AppCompatActivity() {

    val TAG = "DashboardActivity"

    lateinit var setLocalizationButton: ImageButton
    lateinit var addImageButton: ImageButton
    lateinit var writePostEditText: EditText
    lateinit var postAdapter: PostAdapter
    lateinit var userNameAndSurname: String
    lateinit var auth: FirebaseAuth

    var posts: MutableList<Post> = mutableListOf()
    var followers: MutableList<Followers>  = mutableListOf()
    var imageUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        auth = Firebase.auth
        val currentUser = auth.currentUser
        getUserData(currentUser!!.uid)

        setLocalizationButton = findViewById(R.id.localizationPinButton)
        addImageButton = findViewById(R.id.addImageButton)
        writePostEditText = findViewById(R.id.writePostEditText)
//        var followers = mutableListOf(
//            Followers("KW"),
//            Followers("KS"),
//            Followers("AP"),
//            Followers("MK"),
//            Followers("FB"),
//            Followers("JK"),
//            Followers("MC"),
//            Followers("VM"),
//            Followers("JP"),
//            Followers("PD"),
//            Followers("TJ"),
//        )

        val followersAdapter = FollowersAdapter(followers)
        val recyclerViewFollowers = findViewById<RecyclerView>(R.id.userFollowers)
        recyclerViewFollowers.adapter = followersAdapter
        recyclerViewFollowers.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

//        posts = mutableListOf(
//            Post(5, 10,
//                DateFormat.getDateInstance(DateFormat.MEDIUM).format(Date(2022, 11, 17)),
//                "Skierniewice",
//                "Kamil",
//                "KW",
//                "Grzyb",
//                "",
//                null
//            ),
//            Post(5, 10,
//                DateFormat.getDateInstance(DateFormat.MEDIUM).format(Date(2022, 11, 17)),
//                "Skierniewice",
//                "Kamil",
//                "KW",
//                "Grzyb",
//                "",
//                null
//            ),
//            Post(5, 10,
//                DateFormat.getDateInstance(DateFormat.MEDIUM).format(Date(2022, 11, 17)),
//                "Skierniewice",
//                "Kamil",
//                "KW",
//                "Grzyb",
//                "",
//                null
//            )
//        )

        addImageButton.setOnClickListener {
            openGalleryFolder()
        }

        postAdapter = PostAdapter(posts)
        val recyclerViewPosts = findViewById<RecyclerView>(R.id.usersPosts)
        recyclerViewPosts.adapter = postAdapter
        recyclerViewPosts.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        findViewById<Button>(R.id.addPost).setOnClickListener {
            var post = Post(0
                , 0
                , LocalDateTime.now().toString()
                , ""
                , userNameAndSurname
                , ""
                , ""
                , writePostEditText.text.toString()
                , imageUri)

            sendPostDataToServer(post)
        }
    }

    private fun openGalleryFolder() {
        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = "image/*"
        galleryResult.launch(galleryIntent)
    }

    var galleryResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            imageUri = data?.data
        }
    }

    private fun getUserData(uid: String) {
        val db = Firebase.firestore

        db.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { documents ->
                val login: String = documents.data!!["username"] as String
                val firstName: String? = documents.data?.get("firstName") as String?
                val lastName: String? = documents.data?.get("lastName") as String?

                if (firstName != null && lastName != null)
                    userNameAndSurname = "$firstName ${lastName[0]}."
                else
                    userNameAndSurname = login
            }
    }

    private fun sendPostDataToServer(post: Post) {
        val db = Firebase.firestore

        db.collection("posts")
            .add(post)
            .addOnSuccessListener {
                Toast.makeText(this, "ADDED", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                ex ->
                Log.e(TAG, "$ex")
            }
    }

}