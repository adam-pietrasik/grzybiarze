package pl.grzybiarze.gatherer.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pl.grzybiarze.gatherer.R
import pl.grzybiarze.gatherer.adapters.AtlasRecyclerView
import pl.grzybiarze.gatherer.data.MushroomElementModal
import pl.grzybiarze.gatherer.repo.ClickListener

class AtlasActivity : ClickListener, AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_atlas_recycler_view)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewsMushrooms)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val data = ArrayList<MushroomElementModal>()

        for (i in 1..20){
            data.add(MushroomElementModal("Dupa","Mushroom $i",true))
        }

        val adapter = AtlasRecyclerView(data,this)

        recyclerView.adapter = adapter

    }

    //private fun replaceFragment(bundle: Bundle) {
    //    val fragment: Fragment = MoreDetailsAboutTask()
    //    fragment.arguments = bundle
    //    getParentFragmentManager()
    //        .beginTransaction()
    //        .replace(R.id.mainLayout, fragment)
    //        .setReorderingAllowed(true)
    //        .commit()
    //}

    override fun onClickItem(position: Int) {
        println(position)
    }
}