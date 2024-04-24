package com.ketaloca.crazyweekend

import android.icu.text.CaseMap
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ketaloca.crazyweekend.R.*
import com.ketaloca.crazyweekend.controlador.HotelAdapter
import com.ketaloca.crazyweekend.controlador.HotelesListProvider
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
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewBuscar)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = HotelAdapter(HotelesListProvider.lista)
    }

}