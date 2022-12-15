package pl.grzybiarze.gatherer.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import pl.grzybiarze.gatherer.R
import pl.grzybiarze.gatherer.activity.AtlasActivity
import pl.grzybiarze.gatherer.data.MushroomElementModal


class MushroomDetailsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val data = arguments?.getSerializable("mushroom") as MushroomElementModal
        val view = inflater.inflate(R.layout.mushroom_details_fragment, container, false)

        val imageView: ImageView = view.findViewById(R.id.mushroomImage)
        val mushroomName: TextView = view.findViewById(R.id.mushroomName)
        val backButton: ImageButton = view.findViewById(R.id.backButton)
        val mushroomDescription: TextView = view.findViewById(R.id.mushroomDescription)

        mushroomName.text = data.name
        mushroomDescription.text = data.description

        Glide.with(imageView.context).load(data.image).into(imageView)

        backButton.setOnClickListener {
            //  val intent = Intent(imageView.context, AtlasActivity::class.java)
            //  startActivity(intent)
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
        }

        return view;
    }
}