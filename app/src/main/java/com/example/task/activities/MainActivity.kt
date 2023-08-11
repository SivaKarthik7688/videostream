package com.example.task.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.task.R
import com.example.task.fragments.MovieFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById(R.id.bottom_nav)
        toolbar = findViewById(R.id.toolbar_main)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, MovieFragment()).commit()
        toolbar.setTitle("Home")
        bottomNavigationView.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> if (supportFragmentManager.findFragmentById(R.id.fragment_container)!!.javaClass.simpleName != "MovieFragment") {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, MovieFragment()).commit()
                }
                R.id.nav_search -> if (supportFragmentManager.findFragmentById(R.id.fragment_container)!!.javaClass.simpleName != "MovieFragment") {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, MovieFragment()).commit()

                }
                R.id.nav_cs -> if (supportFragmentManager.findFragmentById(R.id.fragment_container)!!.javaClass.simpleName != "MovieFragment") {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, MovieFragment()).commit()

                }
                R.id.nav_download -> if (supportFragmentManager.findFragmentById(R.id.fragment_container)!!.javaClass.simpleName != "MovieFragment") {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, MovieFragment()).commit()

                }
                R.id.nav_more -> if (supportFragmentManager.findFragmentById(R.id.fragment_container)!!.javaClass.simpleName != "MovieFragment") {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, MovieFragment()).commit()

                }
            }
            true
        })
    }
}