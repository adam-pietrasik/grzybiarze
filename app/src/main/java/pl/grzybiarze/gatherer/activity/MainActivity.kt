package pl.grzybiarze.gatherer.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import pl.grzybiarze.gatherer.R

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val signInButton = findViewById<Button>(R.id.sign_in_button)
            .setOnClickListener {
                Intent(this, MenuActivity::class.java).also {
                    startActivity(it)
                }
            }

        val signUpButton = findViewById<Button>(R.id.sign_up_button)
            .setOnClickListener {
                Intent(this, RegisterActivity::class.java).also {
                    startActivity(it)
                }
            }
    }
}