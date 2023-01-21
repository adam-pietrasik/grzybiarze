package pl.grzybiarze.gatherer.adapters

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import pl.grzybiarze.gatherer.R
import pl.grzybiarze.gatherer.helper_classes.Post

class PostAdapter (
    var posts: List<Post>
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.users_posts, parent, false);
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.itemView.findViewById<ConstraintLayout>(R.id.postHeader).apply {
            findViewById<TextView>(R.id.userName).apply {
                text = posts[position].postOwner
            }
            findViewById<TextView>(R.id.numberOfPostComments).apply {
                text = posts[position].numberOfComments.toString()
            }
            findViewById<TextView>(R.id.numberOfPostLikes).apply {
                text = posts[position].numberOfLikes.toString()
            }
        }
        holder.itemView.findViewById<ConstraintLayout>(R.id.postFooter).apply {
            findViewById<TextView>(R.id.postTimestamp).apply {
                text = posts[position].date
            }
            findViewById<TextView>(R.id.postLocalization).apply {
                text = posts[position].localization
            }
            findViewById<TextView>(R.id.postMushroomType).apply {
                text = posts[position].mushroomType
            }
        }
        holder.itemView.findViewById<TextView>(R.id.postText).apply {
            text = posts[position].postText
        }
        holder.itemView.findViewById<ImageView>(R.id.postImage).apply {
            if (posts[position].postPictureUri != null) {
                Log.w("TAG", "onBindViewHolder ${posts[position].postPictureUri}")
                visibility = View.VISIBLE
                // Image uri zamienić na bitmap
            }
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }

}