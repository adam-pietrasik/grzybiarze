package pl.grzybiarze.gatherer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import pl.grzybiarze.gatherer.R

class RegisterActivity : AppCompatActivity() {

    private val TAG = "RegisterActivity"
    private lateinit var auth: FirebaseAuth

    private lateinit var username: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var submit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth

        username = findViewById(R.id.username)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        submit = findViewById(R.id.submit)

        submit.setOnClickListener {
            // TODO: after the database of Users will be set, username will be used
            val usernameText = username.text.toString()
            val emailText = email.text.toString()
            val passwordText = password.text.toString()
            createAccount(emailText, passwordText)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
        // TODO: check if user is signed in if not null then go to user page or login (which one is better?)
        // val currentUser = auth.currentUser
    }

    private fun createAccount(email: String, password: String) {
        if (!validateData(email, password)) {
            Log.d(TAG, "createAccount() empty data passed")
            Toast.makeText(this, R.string.sign_in_empty_password_or_email, Toast.LENGTH_SHORT).show()
            return
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createAccount() user successfully registered")
                    Toast.makeText(this, R.string.user_created, Toast.LENGTH_SHORT).show()
                    // TODO: go to main page
                }
                else {
                    Log.w(TAG, "createAccount() user registration error: ", task.exception)
                    Toast.makeText(this, R.string.authentication_failed, Toast.LENGTH_SHORT).show()

                }
            }
    }

    private fun validateData(email: String, password: String) : Boolean{
        return !(email.isEmpty() && password.isEmpty())
    }

}