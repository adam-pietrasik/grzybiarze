package pl.grzybiarze.gatherer.activity

import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import pl.grzybiarze.gatherer.R
import pl.grzybiarze.gatherer.adapters.AtlasRecyclerView
import pl.grzybiarze.gatherer.data.MushroomElementModal
import pl.grzybiarze.gatherer.enum.MushroomStatus
import pl.grzybiarze.gatherer.enum.MushroomStatus.*
import pl.grzybiarze.gatherer.fragment.MushroomDetailsFragment
import pl.grzybiarze.gatherer.repo.ClickListener
import java.lang.ref.Reference

class AtlasActivity : ClickListener, AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_atlas_recycler_view)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewsMushrooms)

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        val data = ArrayList<MushroomElementModal>()

        val db = Firebase.firestore

        db.collection("mushroom")
            .get()
            .addOnSuccessListener { result ->
                for (documents in result) {
                    val name: String = documents.data["name"] as String
                    val description: String = documents.data["description"] as String
                    val isEdible: String = documents.data["isedible"] as String
                    val photo: String = documents.data["photo"] as String
                   // Firebase.storage.getReferenceFromUrl(photo.path).getBytes(1024*1024)
                    when (isEdible) {
                        EDIBLE.toString() -> {
                            data.add(MushroomElementModal(photo, name, description, EDIBLE))
                        }
                        TOXIC.toString() -> {
                            data.add(MushroomElementModal(photo, name, description, TOXIC))
                        }
                        else -> {
                            data.add(MushroomElementModal(photo, name, description, INEDIBLE))
                        }
                    }
                }
                val adapter = AtlasRecyclerView(data, this)

                recyclerView.adapter = adapter
            }
    }

    private fun replaceFragment(bundle: Bundle?) {
        val fragment: Fragment = MushroomDetailsFragment()
        fragment.arguments = bundle
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.atlasLayout, fragment)
            .setReorderingAllowed(true)
            .commit()
    }

    override fun onClickItem(position: Int) {
        replaceFragment(null)
    }
}