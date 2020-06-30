package com.myapplication.bookhub.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.myapplication.bookhub.*
import com.myapplication.bookhub.fragment.AboutAppFragment
import com.myapplication.bookhub.fragment.DashboardFragment
import com.myapplication.bookhub.fragment.FavouritesFragment
import com.myapplication.bookhub.fragment.ProfileFragment

class MainActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView

    var previousMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerLayout)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        toolbar = findViewById(R.id.toolbar)
        frameLayout = findViewById(R.id.frame)
        navigationView = findViewById(R.id.navigationView)

        setUpToolbar()

        // Making the first page to open as a Home page ie dashboard
        openDashboard()

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this@MainActivity,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )

        drawerLayout.addDrawerListener(actionBarDrawerToggle)   //making work hamburger icon
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {
            // it will give currently selected item

            if (previousMenuItem!=null){
                previousMenuItem?.isChecked = false
            }

            it.isCheckable = true
            it.isChecked = true
            previousMenuItem = it

            when(it.itemId){
                R.id.dashboard ->{
                    supportFragmentManager.beginTransaction()
                    openDashboard()
                    //  .addToBackStack("Dashboard")         adding to backstack so the app does not exit out but goes to the previous screen

                    drawerLayout.closeDrawers()
                }
                R.id.favourites ->{
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frame,
                            FavouritesFragment()
                        )   // dashboard fragment is replacing the blank frame

                        .commit()

                    supportActionBar?.title = "Favourites"    //giving the title
                    drawerLayout.closeDrawers()
                }
                R.id.profile ->{
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frame,
                            ProfileFragment()
                        )   // dashboard fragment is replacing the blank frame

                        .commit()

                    supportActionBar?.title = "Profile"    //giving the title
                    drawerLayout.closeDrawers()
                }
                R.id.aboutApp ->{
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frame,
                            AboutAppFragment()
                        )   // dashboard fragment is replacing the blank frame

                        .commit()

                    supportActionBar?.title = "About App"    //giving the title
                    drawerLayout.closeDrawers()
                }
            }

            return@setNavigationItemSelectedListener true
        }
    }

    fun setUpToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Toolbar title"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    // for the clicking on hamburger and opening of drawer from the left side
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        if(id==android.R.id.home)
        {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        return super.onOptionsItemSelected(item)
    }

    fun openDashboard(){
        val fragment = DashboardFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(
            R.id.frame,
            DashboardFragment()
        )   // dashboard fragment is replacing the blank frame
        transaction.commit()
        supportActionBar?.title = "Dashboard"    //giving the title to each fragment
        navigationView.setCheckedItem(R.id.dashboard)
    }

    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.frame)

        when(frag){
            !is DashboardFragment -> openDashboard()    // if at any frame ie other than dashboard this will bring back to dasboard on pressing back key

            else -> super.onBackPressed()     // Exit the app (default)
        }
    }


}