package pl.grzybiarze.gatherer.activity

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pl.grzybiarze.gatherer.R
import pl.grzybiarze.gatherer.adapters.StatisticItemGridView
import pl.grzybiarze.gatherer.data.StatisticItemModal

class UserActivity : AppCompatActivity(){

    private val TAG = "UserActivity"
    private lateinit var auth: FirebaseAuth

    private lateinit var statisticGridView: GridView
    private lateinit var statisticItemModal: List<StatisticItemModal>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        statisticGridView = findViewById(R.id.stat_view)
        statisticItemModal = ArrayList<StatisticItemModal>()

        statisticItemModal = statisticItemModal + StatisticItemModal(1,"Wróbel")

        val submit = findViewById<Button>(R.id.submit)
        //auth = Firebase.auth

        val courseAdapter = StatisticItemGridView(statisticList = statisticItemModal, this@UserActivity)


        statisticGridView.adapter = courseAdapter

        // on below line we are setting adapter to our grid view.
        //statisticGridView.adapter = courseAdapter

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