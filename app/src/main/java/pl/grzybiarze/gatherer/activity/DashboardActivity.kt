package pl.grzybiarze.gatherer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pl.grzybiarze.gatherer.R
import pl.grzybiarze.gatherer.adapters.FollowersAdapter
import pl.grzybiarze.gatherer.helper_classes.Followers

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        var followers = mutableListOf(
            Followers("KW"),
            Followers("KS"),
            Followers("AP"),
            Followers("MK"),
            Followers("FB"),
            Followers("JK"),
            Followers("MC"),
            Followers("VM"),
            Followers("JP"),
            Followers("PD"),
            Followers("TJ"),
        )

        val adapter = FollowersAdapter(followers)
        val recyclerViewFollowers = findViewById<RecyclerView>(R.id.userFollowers)
        recyclerViewFollowers.adapter = adapter
        recyclerViewFollowers.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }
}