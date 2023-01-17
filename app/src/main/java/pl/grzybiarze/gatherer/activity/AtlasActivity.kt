package pl.grzybiarze.gatherer.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
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
    lateinit var toggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_atlas_recycler_view)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewsMushrooms)

        var drawerLayout : DrawerLayout = findViewById(R.id.atlasLayout)
        var navView : NavigationView = findViewById(R.id.nav_view)
        var bottomMenu : BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomMenu.selectedItemId = R.id.atlas

        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)

        drawerLayout.addDrawerListener(toggle)

        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){

                R.id.nav_profile -> Toast.makeText(applicationContext,"Profil", Toast.LENGTH_SHORT).show()
                R.id.nav_friends -> Toast.makeText(applicationContext,"Znajomi", Toast.LENGTH_SHORT).show()
                R.id.nav_mapa -> Toast.makeText(applicationContext,"Mapa", Toast.LENGTH_SHORT).show()
                R.id.nav_pogoda -> Toast.makeText(applicationContext,"Pogoda", Toast.LENGTH_SHORT).show()
                R.id.nav_aparat -> Toast.makeText(applicationContext,"Aparat", Toast.LENGTH_SHORT).show()
                R.id.nav_ustawienia -> Toast.makeText(applicationContext,"Ustawienia", Toast.LENGTH_SHORT).show()
                R.id.nav_about -> Toast.makeText(applicationContext,"O Aplikacji", Toast.LENGTH_SHORT).show()

            }
            true
        }
        bottomMenu.setOnItemSelectedListener {
            when(it.itemId){

            R.id.tablica -> Intent(this, WallActivity::class.java).also { startActivity(it) }
            R.id.atlas -> Intent(this, AtlasActivity::class.java).also { startActivity(it) }
            R.id.lokalizacja -> Intent(this, MapActivity::class.java).also { startActivity(it) }

        }
            true
        }
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item))
        {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}