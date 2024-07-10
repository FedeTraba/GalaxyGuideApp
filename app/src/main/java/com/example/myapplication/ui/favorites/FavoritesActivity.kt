package com.example.myapplication.ui.favorites


import FavoritesViewModel
import MainViewModel
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.ui.filter.FilterActivity
import com.example.myapplication.ui.main.MainActivity
import com.example.myapplication.ui.main.PublicationAdapter
import com.google.firebase.auth.FirebaseAuth

class FavoritesActivity : AppCompatActivity() {

    private lateinit var viewModel : FavoritesViewModel
    private lateinit var adapter: PublicationAdapter
    private lateinit var navHome: ImageView
    private lateinit var navSearch: ImageView
    private lateinit var navFav: ImageView
    private lateinit var txtEmailUser: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        navFav = findViewById(R.id.favNav)
        navHome = findViewById(R.id.homeNav)
        navSearch = findViewById(R.id.searchNav)
        txtEmailUser = findViewById(R.id.txtEmail)

        val user = FirebaseAuth.getInstance().currentUser
        txtEmailUser.text = user?.email

        viewModel = ViewModelProvider(this)[FavoritesViewModel::class.java]
        adapter = PublicationAdapter(MainViewModel())

        val recyclerView = findViewById<RecyclerView>(R.id.rvFav)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        viewModel.favorites.observe(this) { favPublications ->
            adapter.update(favPublications)
        }

        initListeners()

    }
    fun initListeners(){
        navFav.setOnClickListener{
            Log.i("TPO-LOG","click para ver favoritos")
            val intent = Intent(this,FavoritesActivity::class.java)
            startActivity(intent)
        }
        navHome.setOnClickListener{
            Log.i("TPO-LOG","click para ver favoritos")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        navSearch.setOnClickListener{
            Log.i("TPO-LOG","click para ver favoritos")
            val intent = Intent(this, FilterActivity::class.java)
            startActivity(intent)
        }
    }



}
