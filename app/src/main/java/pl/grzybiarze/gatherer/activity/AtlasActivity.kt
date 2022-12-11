package pl.grzybiarze.gatherer.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pl.grzybiarze.gatherer.R
import pl.grzybiarze.gatherer.adapters.AtlasRecyclerView
import pl.grzybiarze.gatherer.data.MushroomElementModal
import pl.grzybiarze.gatherer.enum.MushroomStatus
import pl.grzybiarze.gatherer.fragment.MushroomDetailsFragment
import pl.grzybiarze.gatherer.repo.ClickListener

class AtlasActivity : ClickListener, AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_atlas_recycler_view)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewsMushrooms)

        recyclerView.layoutManager = GridLayoutManager(this,2)

        val data = ArrayList<MushroomElementModal>()

        for (i in 1..20){
            data.add(MushroomElementModal("cos","MushroomE $i",MushroomStatus.EDIBLE))
            data.add(MushroomElementModal("cos","MushroomT $i",MushroomStatus.TOXIC))
            data.add(MushroomElementModal("cos","MushroomI $i",MushroomStatus.INEDIBLE))
        }

        val adapter = AtlasRecyclerView(data,this)

        recyclerView.adapter = adapter

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