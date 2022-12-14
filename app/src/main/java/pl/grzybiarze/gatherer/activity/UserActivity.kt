package pl.grzybiarze.gatherer.activity

import android.os.Bundle
import android.util.Log
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import pl.grzybiarze.gatherer.R
import pl.grzybiarze.gatherer.adapters.StatisticItemGridView
import pl.grzybiarze.gatherer.model.User

class UserActivity : AppCompatActivity() {

    private val TAG = "UserActivity"
    private lateinit var auth: FirebaseAuth

    private lateinit var statisticGridView: GridView
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        statisticGridView = findViewById(R.id.stat_view)

        getUserInfo()

        auth = Firebase.auth

    }

    private fun getUserInfo() {
        val db = Firebase.firestore
        val user = Firebase.auth.currentUser

        if (user != null) {
            val email = user.email.toString()
            val uid = user.uid

            val doc = db.collection("users").document(uid)
            doc.get().addOnSuccessListener {
                this.user = it.toObject<User>()!!
                this.user.email = email

                Log.d(TAG, "LastName : ${this.user.lastName}")
                Log.d(TAG, "Firstname : ${this.user.firstName}")
                Log.d(TAG, "Profile picture : ${this.user.profilePicture}")
                Log.d(TAG, "Email : ${this.user.email}")

                val courseAdapter =
                    StatisticItemGridView(statisticList = this.user, this@UserActivity)

                statisticGridView.adapter = courseAdapter
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting user info.", exception)
            }
        } else {
            Log.e(TAG, "User not found")
        }
    }

    private fun deleteUser() {
        // Delete users from authentication group
        val user = Firebase.auth.currentUser

        // Connect to database
        val db = Firebase.firestore

        if (user != null) {

            user.delete()
                .addOnSuccessListener {
                    Log.d(
                        TAG,
                        "User successfully deleted from authentication group!"
                    )
                }
                .addOnFailureListener { e -> Log.w(TAG, "Error deleting user", e) }

            db.collection("users").document(user.uid)
                .delete()
                .addOnSuccessListener { Log.d(TAG, "User successfully deleted!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
        }
    }
}