package pl.grzybiarze.gatherer.activity

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pl.grzybiarze.gatherer.R
import pl.grzybiarze.gatherer.dialogs.ForgotPasswordDialog

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val submit = findViewById<Button>(R.id.submit)
        val forgotPassword = findViewById<Button>(R.id.forgotPassword)
        auth = Firebase.auth

        submit.setOnClickListener {
            val usernameText = username.text.toString()
            val passwordText = password.text.toString()
            signIn(usernameText, passwordText)
            getUsers()
            getUserInfo()
            //deleteUser()
        }

        forgotPassword.setOnClickListener {
            val forgotPasswordDialog = ForgotPasswordDialog(this)
            forgotPasswordDialog.show()
        }
    }

    private fun signIn(email: String, password: String) {
        if (!validateData(email, password)) {
            Log.d(TAG, "signInWithEmail:emptyData")
            Toast.makeText(
                baseContext, R.string.sign_in_empty_password_or_email,
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success " + auth.currentUser?.email)
                    Toast.makeText(
                        baseContext, R.string.sign_in_success,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Log.w(TAG, "signInWithEmail:failure " + auth.currentUser?.email, task.exception)
                    Toast.makeText(
                        baseContext, R.string.authentication_failed,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun validateData(email: String, password: String) : Boolean{
        return !(email.isEmpty() || password.isEmpty())
    }

    private fun getUsers() {
        val db = Firebase.firestore

        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    private fun getUserInfo() {
        val user = Firebase.auth.currentUser

        if(user != null) {
            // Name, email address, and profile photo Url
            val name = user.displayName
            val email = user.email
            val photoUrl = user.photoUrl

            // Check if user's email is verified
            val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            val uid = user.uid

            Log.d(TAG, "Email: ${email.toString()}")
            Log.d(TAG, "Name: ${name.toString()}")
            Log.d(TAG, "Uid: $uid")
        }
    }

    private fun deleteUser() {
        // Delete users from authentication group
        val user = Firebase.auth.currentUser
        user?.delete()

        // Connect to database
        val db = Firebase.firestore

        db.collection("users").document(user?.uid.toString())
            .delete()
            .addOnSuccessListener { Log.d(TAG, "User successfully deleted!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
    }
}