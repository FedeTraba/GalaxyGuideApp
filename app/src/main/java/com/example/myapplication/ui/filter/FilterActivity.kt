package com.example.myapplication.ui.filter

import MainViewModel
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.ui.favorites.FavoritesActivity
import com.example.myapplication.ui.main.MainActivity
import com.example.myapplication.ui.main.PublicationAdapter


class FilterActivity : AppCompatActivity() {
    private lateinit var navHome: ImageView
    private lateinit var navSearch: ImageView
    private lateinit var navFav: ImageView
    private lateinit var adapter: PublicationAdapter
    private lateinit var viewModel : FilterViewModel
    private lateinit var etStartDate: EditText
    private lateinit var etEndDate : EditText
    private lateinit var etKeywords : EditText
    private lateinit var clBuscar : ConstraintLayout
    private lateinit var clBorrar: ConstraintLayout
    private lateinit var rvFilter: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_filter)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        navFav = findViewById(R.id.favNav)
        navHome = findViewById(R.id.homeNav)
        navSearch = findViewById(R.id.searchNav)
        etStartDate = findViewById(R.id.etStartDate)
        etEndDate = findViewById(R.id.etEndDate)
        etKeywords = findViewById(R.id.keyWords)
        clBuscar = findViewById(R.id.buscar)
        clBorrar = findViewById(R.id.borrar)
        rvFilter = findViewById(R.id.rvFilter)

        viewModel = ViewModelProvider(this)[FilterViewModel::class.java]
        adapter = PublicationAdapter(MainViewModel())

        rvFilter.layoutManager = LinearLayoutManager(this)
        rvFilter.adapter = adapter


        viewModel.publicationsFiltradas.observe(this) { publications ->
            adapter.update(publications)
        }



        clBuscar.setOnClickListener {
            val startDate = etStartDate.text.toString()
            val endDate = etEndDate.text.toString()
            val keywords = etKeywords.text.toString()

            if (startDate.isNotEmpty()) {
                viewModel.publicationsFiltradas.value = emptyList() //Borrar la lista
                if (keywords.isNotEmpty()) {
                    if (endDate.isNotEmpty()) {
                        viewModel.buscarPublicationsByKeywords(startDate, endDate, keywords)
                        Log.d("TPO-LOG", "Busqueda por palabras clave $keywords en rango de fechas $startDate - $endDate")
                    } else {
                        Toast.makeText(this, "Por favor, ingrese una fecha de finalización.", Toast.LENGTH_SHORT).show()
                        Log.d("TPO-LOG", "Entrada inválida: se necesita endDate para la búsqueda por palabras clave")
                    }
                } else if (endDate.isNotEmpty()) {
                    viewModel.searchPublicationsByDateRange(startDate, endDate, this,false)
                    Log.d("TPO-LOG", "Busqueda por rango de fechas $startDate - $endDate")
                } else {
                    viewModel.searchPublicationsByDate(startDate)
                    Log.d("TPO-LOG", "Busqueda por fecha $startDate")
                }
            } else {
                // Manejo de casos en los que la entrada no es válida o está incompleta
                Toast.makeText(this, "Entrada inválida. Por favor, ingrese una fecha de inicio.", Toast.LENGTH_SHORT).show()
                Log.d("TPO-LOG", "Entrada inválida para la búsqueda de publicaciones")
            }
        }

        clBorrar.setOnClickListener {
            // borro los campos
            etStartDate.text.clear()
            etEndDate.text.clear()
            etKeywords.text.clear()
            // limpio la lista de filtrados
            viewModel.publicationsFiltradas.value = emptyList()
            Log.d("TPO-LOG", "Campos de búsqueda borrados")
        }

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
