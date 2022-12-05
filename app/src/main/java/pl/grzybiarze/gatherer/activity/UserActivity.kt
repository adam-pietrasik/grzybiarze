package pl.grzybiarze.gatherer.activity

import android.os.Bundle
import android.util.Log
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pl.grzybiarze.gatherer.R
import pl.grzybiarze.gatherer.adapters.StatisticItemGridView
import pl.grzybiarze.gatherer.data.User

class UserActivity : AppCompatActivity(){

    private val TAG = "UserActivity"
    private lateinit var auth: FirebaseAuth

    private lateinit var statisticGridView: GridView
    private lateinit var user: List<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        statisticGridView = findViewById(R.id.stat_view)
        user = ArrayList()

        val user = getUserInfo()
        //deleteUser()

        this.user = this.user + User(user.username, user.firstName,user.secondName, user.email)

        //val submit = findViewById<Button>(R.id.submit)
        auth = Firebase.auth

        val courseAdapter = StatisticItemGridView(statisticList = this.user, this@UserActivity)


        statisticGridView.adapter = courseAdapter

        // on below line we are setting adapter to our grid view.
        //statisticGridView.adapter = courseAdapter

//        submit.setOnClickListener {
//            getUsers()
//        }
    }

    private fun getUserInfo() : User {
        val db = Firebase.firestore
        val user = Firebase.auth.currentUser

        var username = ""
        var firstName = ""
        var secondName = ""
        var email = ""

        if(user != null) {
            // Name, email address, and profile photo Url
            val name = user.displayName
            email = user.email.toString()
            val photoUrl = user.photoUrl

            // Check if user's email is verified
            val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            val uid = user.uid

            db.collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener {
//                    Log.d(TAG, "Email: $email")
//                    Log.d(TAG, "Name: ${name.toString()}")
//                    Log.d(TAG, "Uid: $uid")
                    username = it.get("username").toString()
                    firstName = it.get("firstName").toString()
                    secondName = it.get("firstName").toString()
                    email = it.get("email").toString()
//                    Log.d(TAG, "Username : $username")
//                    Log.d(TAG, "Firstname : $firstName")
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting user info.", exception)
                }
        } else {
            Log.e(TAG, "User not found")
        }
        return User(username, firstName, secondName, email)
    }

    private fun deleteUser() {
        // Delete users from authentication group
        val user = Firebase.auth.currentUser

        // Connect to database
        val db = Firebase.firestore

        if (user != null) {

            user.delete()
                .addOnSuccessListener { Log.d(TAG, "User successfully deleted from authentication group!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error deleting user", e) }

            db.collection("users").document(user.uid)
                .delete()
                .addOnSuccessListener { Log.d(TAG, "User successfully deleted!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
        }
    }
}