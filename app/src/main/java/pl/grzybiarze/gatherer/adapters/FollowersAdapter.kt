package pl.grzybiarze.gatherer.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import pl.grzybiarze.gatherer.R
import pl.grzybiarze.gatherer.helper_classes.Followers
import kotlin.math.roundToInt

class FollowersAdapter(
    var followers: List<Followers>
) : RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder>() {

    private var mContext: Context? = null

    inner class FollowersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersViewHolder {
        if (mContext == null)
            mContext = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_followers, parent, false)
        return FollowersViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollowersViewHolder, position: Int) {
        holder.itemView.resources.getDrawable(R.drawable.user_profile_light_blue, null)

        holder.itemView.findViewById<TextView>(R.id.userProfile).apply {
            text = followers[position].initials
            background = getNameInitialBackground()
        }
    }

    override fun getItemCount(): Int {
        return followers.size
    }


    // TODO: Later we need to get rid of this code in favor of pulling initials data from users profiles
    private fun getNameInitialBackground(): Drawable? {
        val drawable = arrayOf(
            R.drawable.user_profile_brown, R.drawable.user_profile_dark_blue,
            R.drawable.user_profile_dark_red, R.drawable.user_profile_light_blue,
            R.drawable.user_profile_violet
        )

        return ContextCompat.getDrawable(mContext!!, drawable[(Math.random() * (drawable.size - 1)).roundToInt()])
    }
}