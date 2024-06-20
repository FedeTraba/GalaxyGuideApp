package com.example.myapplication.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.myapplication.R

class PublicationActivity : AppCompatActivity() {

    lateinit var title: TextView
    lateinit var url: TextView
    lateinit var img: ImageView
    lateinit var pb: ProgressBar
    lateinit var vm : PublicationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_publication)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        vm = ViewModelProvider(this)[PublicationViewModel::class.java]

        title = findViewById(R.id.txtNAME)
        url =  findViewById(R.id.txtUrl)
        img = findViewById(R.id.imgFoto)
        pb = findViewById(R.id.progressBar2)

        val date = intent.getStringExtra("date")!!
        vm.publication.observe(this){
            if (it != null) {
                title.text = it.title
            }

            if (it != null) {
                url.text = it.url
            }

            if (it != null) {
                Glide.with(this).load(it.url).into(img)
            }
            pb.visibility = View.INVISIBLE

        }
        pb.visibility = View.VISIBLE
        vm.init(date)
    }
}