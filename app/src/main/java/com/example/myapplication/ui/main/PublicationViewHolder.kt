package com.example.myapplication.ui.main

import MainViewModel
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.Publication
import com.example.myapplication.ui.publicationsDetail.PublicationDetailActivity
import com.squareup.picasso.Picasso

class PublicationViewHolder(view: View, private val viewModel: MainViewModel) : RecyclerView.ViewHolder(view) {
    val title: TextView = view.findViewById(R.id.txtTitulo)
    val date: TextView = view.findViewById(R.id.txtDate)
    val img: ImageView = view.findViewById(R.id.imgPublication)
    val buttonFav: Button = view.findViewById(R.id.buttonFav)



    fun bind(publication: Publication) {
        title.text = publication.title
        date.text = publication.date
        Picasso.get()
            .load(publication.url)
            .placeholder(R.drawable.logovioleta)
            .error(R.drawable.logovioleta)
            .into(img)

        buttonFav.setOnClickListener {
            viewModel.cambiarFavorite(publication)
        }

    }


    init {
        view.setOnClickListener {
            val dateText = date.text.toString()
            Log.d("TPO-LOG", "click en item $dateText")
            val intent = Intent(view.context, PublicationDetailActivity::class.java)
            intent.putExtra("date",dateText)
            view.context.startActivity(intent)
        }
    }
}
