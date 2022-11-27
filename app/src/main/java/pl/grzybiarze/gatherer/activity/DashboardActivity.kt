package pl.grzybiarze.gatherer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pl.grzybiarze.gatherer.R
import pl.grzybiarze.gatherer.adapters.FollowersAdapter
import pl.grzybiarze.gatherer.adapters.PostAdapter
import pl.grzybiarze.gatherer.helper_classes.Followers
import pl.grzybiarze.gatherer.helper_classes.Post
import java.text.DateFormat
import java.util.*

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

        val followersAdapter = FollowersAdapter(followers)
        val recyclerViewFollowers = findViewById<RecyclerView>(R.id.userFollowers)
        recyclerViewFollowers.adapter = followersAdapter
        recyclerViewFollowers.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        var posts = mutableListOf(
            Post(5, 10,
                DateFormat.getDateInstance(DateFormat.MEDIUM).format(Date(2022, 11, 17)),
                "Skierniewice",
                "Kamil",
                "KW",
                "Grzyb"
            ),
            Post(5, 10,
                DateFormat.getDateInstance(DateFormat.MEDIUM).format(Date(2022, 11, 17)),
                "Skierniewice",
                "Kamil",
                "KW",
                "Grzyb"
            ),
            Post(5, 10,
                DateFormat.getDateInstance(DateFormat.MEDIUM).format(Date(2022, 11, 17)),
                "Skierniewice",
                "Kamil",
                "KW",
                "Grzyb"
            )
        )

        val postAdapter = PostAdapter(posts)
        val recyclerViewPosts = findViewById<RecyclerView>(R.id.usersPosts)
        recyclerViewPosts.adapter = postAdapter
        recyclerViewPosts.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}