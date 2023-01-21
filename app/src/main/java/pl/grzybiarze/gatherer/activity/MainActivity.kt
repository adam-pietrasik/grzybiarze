package pl.grzybiarze.gatherer.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import pl.grzybiarze.gatherer.R

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth
        val currentUser = auth.currentUser

        findViewById<Button>(R.id.sign_in_button)
            .setOnClickListener {
                if (currentUser == null) {
                    Intent(this, LoginActivity::class.java).also {
                        startActivity(it)
                    }
                }
                else {
                    Intent(this, WallActivity::class.java).also {
                      startActivity(it)
                    }
                }
            }

        findViewById<Button>(R.id.sign_up_button)
            .setOnClickListener {
                Intent(this, RegisterActivity::class.java).also {
                    startActivity(it)
                }
            }
    }
}