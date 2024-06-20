package com.example.myapplication.ui

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.Publication


class PublicationAdapter : RecyclerView.Adapter<PublicationViewHolder>() {

    var items: MutableList<Publication> = ArrayList<Publication>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.publication_item, parent, false)
        return PublicationViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d("DEMO_APIS", "Size" + items.size)
        return items.size
    }

    override fun onBindViewHolder(holder: PublicationViewHolder, position: Int) {
        Log.d("DEMO_APIS", "Position" + position)
        holder.title.text = items[position].title
        holder.url.text = items[position].url

        holder.itemView.setOnClickListener{
            val id = items[position].date
            val intent = Intent(holder.itemView.context, PublicationActivity::class.java)
            intent.putExtra("id", id)
            holder.itemView.context.startActivity(intent)

        }

    }

    fun update(lista: MutableList<Publication>){
        items = lista
        this.notifyDataSetChanged()
    }
}