package pl.grzybiarze.gatherer.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pl.grzybiarze.gatherer.R
import pl.grzybiarze.gatherer.adapters.AtlasRecyclerView
import pl.grzybiarze.gatherer.data.MushroomElementModal
import pl.grzybiarze.gatherer.enum.MushroomStatus.*
import pl.grzybiarze.gatherer.fragment.MushroomDetailsFragment
import pl.grzybiarze.gatherer.repo.ClickListener

class AtlasActivity : ClickListener, AppCompatActivity() {
    private var data = ArrayList<MushroomElementModal>()
    private lateinit var filterText: EditText
    private lateinit var adapter: AtlasRecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_atlas_recycler_view)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewsMushrooms)

        val filter = findViewById<Button>(R.id.buttonWyszukaj)

        filterText = findViewById(R.id.editTextFindTask)

        filter.setOnClickListener {
            filterData();
        }

        recyclerView.layoutManager = GridLayoutManager(this, 2)

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
                adapter = AtlasRecyclerView(data, this)

                recyclerView.adapter = adapter
            }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun filterData() {
        var tempData = data;
        tempData = tempData.filter { name -> name.name.contains(filterText.text) } as ArrayList<MushroomElementModal>
        adapter.clearData(tempData)
        adapter.notifyDataSetChanged()

        return
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
        val bundle = Bundle()
        bundle.putSerializable("mushroom", data[position])
        replaceFragment(bundle)
    }
}