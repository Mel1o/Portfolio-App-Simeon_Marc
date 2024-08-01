package com.example.portfolio

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
import android.view.View
import android.widget.ScrollView
import android.content.Intent
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import androidx.core.content.FileProvider


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var scrollView: ScrollView
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
        scrollView = findViewById(R.id.scroll)
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
            R.id.nav_me -> {
                supportFragmentManager.beginTransaction().replace(R.id.frame_layout, FragmentAboutMe()).commit()
                scrollToAboutMe()
            }
            R.id.nav_projects -> {
                supportFragmentManager.beginTransaction().replace(R.id.frame_layout, FragmentProjects()).commit()
                scrollToProjects()
            }
            R.id.nav_experience -> {
                supportFragmentManager.beginTransaction().replace(R.id.frame_layout, FragmentExperience()).commit()
                scrollToExperience()
            }
            R.id.nav_education -> {
                supportFragmentManager.beginTransaction().replace(R.id.frame_layout, FragmentEducation()).commit()
                scrollToEducation()
            }

            R.id.nav_email -> {
                copyTextToClipboard("simeon.marc.2002@gmail.com")
                Toast.makeText(this, "Email copied to clipboard", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_number -> {
                copyTextToClipboard("09604616174")
                Toast.makeText(this, "Number copied to clipboard", Toast.LENGTH_SHORT).show()
            }

            R.id.nav_resume -> {
                openPdfFromAssets("Resume.pdf")
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun scrollToAboutMe() {
        val aboutMeView = findViewById<View>(R.id.background_skyblue)
        scrollView.post {
            scrollView.smoothScrollTo(0, aboutMeView.top)
        }
    }

    private fun scrollToProjects() {
        val aboutMeView = findViewById<View>(R.id.background_aboutMe)
        scrollView.post {
            scrollView.smoothScrollTo(0, aboutMeView.bottom)
        }
    }

    private fun scrollToExperience() {
        val aboutMeView = findViewById<View>(R.id.background_projects)
        scrollView.post {
            scrollView.smoothScrollTo(0, aboutMeView.bottom)
        }
    }

    private fun scrollToEducation() {
        val aboutMeView = findViewById<View>(R.id.background_experience)
        scrollView.post {
            scrollView.smoothScrollTo(0, aboutMeView.bottom)
        }
    }

    private fun copyTextToClipboard(text: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("", text)
        clipboard.setPrimaryClip(clip)
    }

    private fun openPdfFromAssets(fileName: String) {
        try {
            val inputStream: InputStream = assets.open(fileName)
            val file = File(cacheDir, fileName)
            val outputStream = FileOutputStream(file)
            val buffer = ByteArray(1024)
            var length: Int
            while (inputStream.read(buffer).also { length = it } > 0) {
                outputStream.write(buffer, 0, length)
            }
            outputStream.close()
            inputStream.close()

            val pdfUri: Uri = FileProvider.getUriForFile(this, "${packageName}.fileprovider", file)
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(pdfUri, "application/pdf")
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NO_HISTORY
            val chooser = Intent.createChooser(intent, "Open with")

            // Check if there's an app that can handle the intent
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(chooser)
            } else {
                Toast.makeText(this, "No PDF viewer found", Toast.LENGTH_SHORT).show()
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error opening PDF", Toast.LENGTH_SHORT).show()
        }
    }




    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
