package com.example.myapplication.ui.publicationsDetail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import com.example.myapplication.R
import com.example.myapplication.model.Publication
import com.example.myapplication.ui.favorites.FavoritesActivity
import com.example.myapplication.ui.main.MainActivity
import com.squareup.picasso.Picasso

class PublicationDetailActivity : AppCompatActivity() {
    private lateinit var viewModel: PublicationDetailViewModel
    private lateinit var tvDate: TextView
    private lateinit var tvTitle: TextView
    private lateinit var tvDescription: TextView
    private lateinit var imgPublication: ImageView
    private lateinit var tvAutor: TextView
    private lateinit var navHome: ImageView
    private lateinit var navSearch: ImageView
    private lateinit var navFav: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_publication_detail)

        tvDate = findViewById(R.id.txtDate)
        tvTitle = findViewById(R.id.txtTitulo)
        tvDescription = findViewById(R.id.txtDescripcion)
        imgPublication = findViewById(R.id.imgPublication)
        tvAutor = findViewById(R.id.txtAutor)
        navFav = findViewById(R.id.favNav)
        navHome = findViewById(R.id.homeNav)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val date = intent.getStringExtra("date").toString()
        viewModel = ViewModelProvider(this)[PublicationDetailViewModel::class.java]

        viewModel.publication.observe(this, Observer { publication ->
            publication?.let { updateUI(it) }
        })

        viewModel.init(date)
        initListeners()


    }
    fun initListeners(){
        navFav.setOnClickListener{
            Log.i("TPO-LOG","click para ver favoritos")
            val intent = Intent(this, FavoritesActivity::class.java)
            startActivity(intent)
        }
        navHome.setOnClickListener{
            Log.i("TPO-LOG","click para ver favoritos")
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateUI(publication: Publication) {
        tvDate.text = publication.date
        tvTitle.text = publication.title
        tvDescription.text = publication.explanation
        Picasso.get().load(publication.url).into(imgPublication)
        tvAutor.text = if (publication.copyright.isNullOrEmpty()) {
            "Nasa"
        } else {
            publication.copyright
        }

    }

}
