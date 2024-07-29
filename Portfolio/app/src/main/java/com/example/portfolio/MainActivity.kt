package com.example.portfolio

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import android.view.MenuItem

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imageView: ImageView = findViewById(R.id.prtf_animation)
        Glide.with(this)
            .load(R.drawable.portfolio_animation)
            .into(imageView)


        val toolbar: Toolbar = findViewById(R.id.toolbar)
        if (toolbar != null) {
            Log.d(TAG, "Toolbar was found but wont be set i need help ahhh")
            setSupportActionBar(toolbar)
        } else {
            Log.d(TAG, "Toolbar is Null bruh")
        }

        drawerLayout = findViewById(R.id.drawer_layout)
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.frame_layout, FragmentAboutMe()).commit()
            navigationView.setCheckedItem(R.id.nav_me)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_me -> supportFragmentManager.beginTransaction().replace(R.id.frame_layout, FragmentAboutMe()).commit()
            R.id.nav_education -> supportFragmentManager.beginTransaction().replace(R.id.frame_layout, FragmentEducation()).commit()
            R.id.nav_experience -> supportFragmentManager.beginTransaction().replace(R.id.frame_layout, FragmentExperience()).commit()
            R.id.nav_projects -> supportFragmentManager.beginTransaction().replace(R.id.frame_layout, FragmentProjects()).commit()
            R.id.nav_resume-> supportFragmentManager.beginTransaction().replace(R.id.frame_layout, FragmentResume()).commit()
            R.id.nav_email -> supportFragmentManager.beginTransaction().replace(R.id.frame_layout, FragmentEmail()).commit()
            R.id.nav_number -> supportFragmentManager.beginTransaction().replace(R.id.frame_layout, FragmentNumber()).commit()
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
