package pl.grzybiarze.gatherer.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
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
    var userNameAndSurname = "test"
    val auth: FirebaseAuth = Firebase.auth

    var posts: MutableList<Post> = mutableListOf()
    var followers: MutableList<Followers>  = mutableListOf()
    var imageUri: String? = null
    var contentUri: Uri? = null
    lateinit var imgRef: StorageReference

    val storage = Firebase.storage
    val currentUser = auth.currentUser

    val IMG_PATH = "${this.currentUser!!.uid}/images/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        getUserData(currentUser!!.uid)

        setLocalizationButton = findViewById(R.id.localizationPinButton)
        addImageButton = findViewById(R.id.addImageButton)
        writePostEditText = findViewById(R.id.writePostEditText)

        val followersAdapter = FollowersAdapter(followers)
        val recyclerViewFollowers = findViewById<RecyclerView>(R.id.userFollowers)
        recyclerViewFollowers.adapter = followersAdapter
        recyclerViewFollowers.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

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

        /*val str: Task<Uri> = */
//        storage.reference.child("${IMG_PATH}1770991056").downloadUrl.addOnSuccessListener {
//            Log.d(TAG, "onCreate ${it.path}")
//        }.addOnFailureListener {
//            Log.d(TAG, "onCreate ${it.message}")
//        }
        getPostsFromServer()
    }

    private fun openGalleryFolder() {
        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = "image/*"
        galleryResult.launch(galleryIntent)
    }

    var galleryResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            this.contentUri = data?.data
            val storageRef = storage.reference
            this.imgRef =
                storageRef.child("$IMG_PATH${this.contentUri?.lastPathSegment}")
            println("${this.imgRef.path} ${this.imgRef.root}" )
            this.imageUri = "${this.imgRef.root}${this.imgRef.path}"
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
                posts.add(post)
                if (this.imageUri != null) run {
                    val uploadTask = imgRef.putFile(this.contentUri!!)
                    uploadTask.addOnSuccessListener {
                        Log.d(TAG, "sendPostDataToServer() image added")
                    }
                }
                postAdapter.notifyItemChanged(0)
            }
            .addOnFailureListener {
                ex ->
                Log.e(TAG, "$ex")
            }
    }

    private fun getPostsFromServer() {
        val db = Firebase.firestore

        db.collection("posts")
            .get()
            .addOnSuccessListener { posts ->
                for(post in posts) {
                    this.posts.add(post.toObject())
                }
                postAdapter.notifyDataSetChanged()
            }
    }

}