package pl.grzybiarze.gatherer.activity

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import pl.grzybiarze.gatherer.R

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val submit = findViewById<Button>(R.id.submit)
        auth = Firebase.auth

        submit.setOnClickListener {
            val usernameText = username.text.toString()
            val passwordText = password.text.toString()
            signIn(usernameText, passwordText)
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
                        baseContext, R.string.sign_in_failed,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun validateData(email: String, password: String) : Boolean{
        return !(email.isEmpty() || password.isEmpty())
    }
}