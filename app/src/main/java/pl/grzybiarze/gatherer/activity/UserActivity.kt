package pl.grzybiarze.gatherer.activity

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pl.grzybiarze.gatherer.R
import pl.grzybiarze.gatherer.adapters.StatisticItemGridView
import pl.grzybiarze.gatherer.data.StatisticItemModal
import pl.grzybiarze.gatherer.dialogs.ForgotPasswordDialog

class UserActivity : AppCompatActivity(){

    private val TAG = "UserActivity"
    private lateinit var auth: FirebaseAuth
    lateinit var statisticGridView: StatisticItemGridView
    lateinit var statisticItemModal: List<StatisticItemModal>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView()

        val submit = findViewById<Button>(R.id.submit)
        //auth = Firebase.auth

        submit.setOnClickListener {
            getUsers()
        }
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
}