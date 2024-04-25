package com.ketaloca.crazyweekend.vista

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ketaloca.crazyweekend.R
import com.ketaloca.crazyweekend.R.*
import com.ketaloca.crazyweekend.controlador.HotelAdapter
import com.ketaloca.crazyweekend.controlador.HotelesListProvider
import com.ketaloca.crazyweekend.databinding.ActivityBuscarBinding
import com.ketaloca.crazyweekend.modelo.DataClasses

class BuscarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(layout.activity_buscar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        inicio()
    }

    private fun inicio() {
        initRecyclerView()
        val btnLogo: ImageView = findViewById(R.id.imgLogoApp)
        btnLogo.setOnClickListener { finish() }
    }

    private fun initRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(id.recyclerViewBuscar)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter =
            HotelAdapter(HotelesListProvider.lista) { alojamiento -> onItemSelected(alojamiento) }
    }

    private fun onItemSelected(alojamiento: DataClasses.alojamiento) {
        val intent = Intent(this, HotelActivity::class.java)
        startActivity(intent)
    }

}