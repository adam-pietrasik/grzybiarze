package pl.grzybiarze.gatherer.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import pl.grzybiarze.gatherer.R

class MenuActivity : AppCompatActivity() {

    private val TAG = "MenuActivity"
    lateinit var toggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        var drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        var navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)

        drawerLayout.addDrawerListener(toggle)

        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){

                R.id.nav_profile -> Toast.makeText(applicationContext,"Profil",Toast.LENGTH_SHORT).show()
                R.id.nav_friends -> Toast.makeText(applicationContext,"Znajomi",Toast.LENGTH_SHORT).show()
                R.id.nav_mapa -> Toast.makeText(applicationContext,"Mapa",Toast.LENGTH_SHORT).show()
                R.id.nav_pogoda -> Toast.makeText(applicationContext,"Pogoda",Toast.LENGTH_SHORT).show()
                R.id.nav_aparat -> Toast.makeText(applicationContext,"Aparat",Toast.LENGTH_SHORT).show()
                R.id.nav_ustawienia -> Toast.makeText(applicationContext,"Ustawienia",Toast.LENGTH_SHORT).show()
                R.id.nav_about -> Toast.makeText(applicationContext,"O Aplikacji",Toast.LENGTH_SHORT).show()

            }
            true
        }

    }

}