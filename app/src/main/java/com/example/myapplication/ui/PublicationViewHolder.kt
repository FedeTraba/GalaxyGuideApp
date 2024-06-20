package com.example.myapplication.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class PublicationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title: TextView = itemView.findViewById(R.id.txtNAME)
    val url: TextView = itemView.findViewById(R.id.txtUrl)
}