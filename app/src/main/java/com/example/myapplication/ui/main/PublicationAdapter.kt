package com.example.myapplication.ui.main

import MainViewModel
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.Publication

class PublicationAdapter(private val viewModel: MainViewModel) : RecyclerView.Adapter<PublicationViewHolder>() {

    private var items: List<Publication> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.publication_item, parent, false)
        return PublicationViewHolder(view, viewModel)
    }



    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PublicationViewHolder, position: Int) {
        holder.bind(items[position])
    }


    fun update(items: List<Publication>){
        this.items = items
        this.notifyDataSetChanged()
    }


}