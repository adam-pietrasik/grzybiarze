package pl.grzybiarze.gatherer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.grzybiarze.gatherer.R
import pl.grzybiarze.gatherer.data.MushroomElementModal

class AtlasRecyclerView(private val mushroomModalList: List<MushroomElementModal>) : RecyclerView.Adapter<AtlasRecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.mushroom_element, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = mushroomModalList[position].name
    }

    override fun getItemCount(): Int {
        return mushroomModalList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageButton: ImageButton = itemView.findViewById(R.id.mushroomImage)
        val textView: TextView = itemView.findViewById(R.id.mushroomName)
    }
}