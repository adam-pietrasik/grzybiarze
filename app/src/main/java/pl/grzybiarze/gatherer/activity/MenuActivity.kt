package pl.grzybiarze.gatherer.activity

import android.os.Bundle
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

    }

}