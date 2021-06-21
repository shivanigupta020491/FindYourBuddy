package com.testing.findyourbudy.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.testing.findyourbudy.R

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var navigationView: NavigationView
    private var drawer: DrawerLayout? = null
    private var bottomNavigationView: BottomNavigationView? = null
    private var navControllerFragment:NavController? = null
    private var toggle: ActionBarDrawerToggle? = null
    private var toolbar: Toolbar? = null
    private var builder: AlertDialog.Builder? = null
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initView()
        initInstance()
        setListeneres()


//        val navView: BottomNavigationView = findViewById(R.id.bottom_nav_view)
//
//        val navController = findNavController(R.id.nav_host_fragment)
//
//        navView.setupWithNavController(navController)
    }

    private fun initView() {
        navigationView = findViewById(R.id.nav_view)
        drawer = findViewById(R.id.drawer_layout)
        bottomNavigationView = findViewById(R.id.bottom_nav_view)
        navControllerFragment = findNavController(R.id.nav_host_fragment)
        toolbar = findViewById(R.id.toolbar)
    }



    private fun initInstance() {
        bottomNavigationView!!.setupWithNavController(navControllerFragment!!)
        setSupportActionBar(toolbar)

        toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer!!.addDrawerListener(toggle!!)
        toggle!!.setHomeAsUpIndicator(R.drawable.ic_baseline_menu)
        toggle!!.syncState()
        builder = AlertDialog.Builder(this@HomeActivity)
    }

    private fun setListeneres() {

        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var id: Int = item.getItemId()
        System.out.println(">>>>>>>>> navigation item id$id")
        if (id == R.id.nav_profile ){
          System.out.println(">>>>>>>>> navigation item id$id")
            val i = Intent(this@HomeActivity, ProfileActivity::class.java) //your class
             startActivity(i)
            return true
        }else{
            return false
        }
    }

//    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
//        val id: Int = menuItem.getItemId()
//        println(">>>>>>>>> navigation item id$id")
//        when (id) {
//            R.id.nav_setting -> {
//
//            }
//            R.id.nav_profile -> {
//                val i = Intent(this@HomeActivity, ProfileActivity::class.java) //your class
//                startActivity(i)
//            }
//            R.id.nav_logout -> {
////                hitLogoutAPi()
//          SessionManager(this@HomeActivity).logOutUser()
//            }
//        }
//        return super.onOptionsItemSelected(menuItem)
//    }
}