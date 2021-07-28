package com.baraka.foodmate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {

    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        toggle.isDrawerIndicatorEnabled= true
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        navView.setNavigationItemSelectedListener(this)
        //aDD FRAME LAYOUT
        setToolbarTitle("Chase the flavours")
        //automatic fragment
        val fragment = supportFragmentManager.beginTransaction()
        fragment.replace(R.id.fragment_container, HomeFragment()).commit()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        //close drawer on click
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)

        // handle clicks
        when (item.itemId) {
            R.id.nav_home -> {
                setToolbarTitle("Chase the flavours")
                changeFragment(HomeFragment())
            }
            R.id.nav_orders-> {
                setToolbarTitle("Customer Orders")
                changeFragment(OrdersFragment())
            }

            R.id.nav_settings -> {
                setToolbarTitle("Food Mate Settings")
                changeFragment(SettingsFragment())
            }

            R.id.nav_logout -> {
//                auth.signOut();
                startActivity(Intent(applicationContext, MainActivity::class.java))
            }
            R.id.nav_add_meal-> {
                setToolbarTitle("Chefs Corner")
                changeFragment(AddMealFragment())
            }
        }
        return true

    }
    //change the title bar
    private fun setToolbarTitle(title:String){
        supportActionBar?.title = title
    }

    //private fun to change the fragment
    private fun changeFragment(fragName : Fragment){
        val fragment = supportFragmentManager.beginTransaction()
        fragment.replace(R.id.fragment_container, fragName).commit()
    }



}