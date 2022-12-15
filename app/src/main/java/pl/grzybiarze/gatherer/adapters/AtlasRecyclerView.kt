package pl.grzybiarze.gatherer.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pl.grzybiarze.gatherer.R
import pl.grzybiarze.gatherer.data.MushroomElementModal
import pl.grzybiarze.gatherer.enum.MushroomStatus
import pl.grzybiarze.gatherer.repo.ClickListener
import com.squareup.picasso.Picasso;

class AtlasRecyclerView(
    private val mushroomModalList: List<MushroomElementModal>,
    clickListener: ClickListener
) :
    RecyclerView.Adapter<AtlasRecyclerView.ViewHolder>() {

    private val clickListener: ClickListener = clickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.mushroom_element, parent, false),
            clickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = mushroomModalList[position].name
        Glide.with(holder.imageButton.context).load(mushroomModalList[position].image).into(holder.imageButton);
        when (mushroomModalList[position].status) {
            MushroomStatus.EDIBLE -> {
                holder.imageView.setBackgroundResource(R.drawable.food_drumstick)
            }
            MushroomStatus.TOXIC -> {
                holder.imageView.setBackgroundResource(R.drawable.bottle_tonic_skull)
            }
            else -> {
                holder.imageView.setBackgroundResource(R.drawable.food_drumstick_off)
            }
        }
    }

    override fun getItemCount(): Int {
        return mushroomModalList.size
    }

    inner class ViewHolder(ItemView: View, clickListener: ClickListener) :
        RecyclerView.ViewHolder(ItemView) {
        val imageButton: ImageButton = itemView.findViewById(R.id.mushroomImage)
        val imageView: ImageView = itemView.findViewById(R.id.edible)
        val textView: TextView = itemView.findViewById(R.id.mushroomName)

        init {
            imageButton.setOnClickListener {
                clickListener.onClickItem(
                    adapterPosition
                )
            }
        }
    }


}