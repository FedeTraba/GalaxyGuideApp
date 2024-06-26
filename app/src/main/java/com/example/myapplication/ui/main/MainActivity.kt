package com.example.myapplication.ui.main


import MainViewModel
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.ui.favorites.FavoritesActivity

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var rvItems: RecyclerView
    private lateinit var adapter: PublicationAdapter
    private lateinit var navHome: ImageView
    private lateinit var navSearch: ImageView
    private lateinit var navFav: ImageView
    private lateinit var pb: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        navFav = findViewById(R.id.favNav)
        navHome = findViewById(R.id.homeNav)
        pb = findViewById(R.id.progressbar)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        rvItems = findViewById(R.id.rvMainItems)
        rvItems.layoutManager = LinearLayoutManager(this)
        adapter = PublicationAdapter(viewModel)
        rvItems.adapter = adapter


        viewModel.publications.observe(this){
            adapter.update(it)
            pb.visibility = View.INVISIBLE
        }

        pb.visibility = View.VISIBLE
        viewModel.init()


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
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }







}
