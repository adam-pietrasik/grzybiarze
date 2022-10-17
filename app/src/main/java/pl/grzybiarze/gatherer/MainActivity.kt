package pl.grzybiarze.gatherer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import pl.grzybiarze.gatherer.activity.LoginActivity

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val signInButton = findViewById<Button>(R.id.sign_in_button)
            .setOnClickListener {
                Intent(this, LoginActivity::class.java).also {
                    startActivity(it)
                }
            }

        // TODO: Create registration activity and pinup it to this button
        val signUpButton = findViewById<Button>(R.id.sign_up_button)
    }
}