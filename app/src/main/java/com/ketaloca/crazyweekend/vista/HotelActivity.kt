package com.ketaloca.crazyweekend.vista

import android.media.Image
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ketaloca.crazyweekend.R
import com.ketaloca.crazyweekend.controlador.FirebaseDriver
import kotlinx.coroutines.runBlocking

class HotelActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_hotel)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        inicio()
        botones()

    }

    private fun inicio() {
        val txtNombre: TextView = findViewById(R.id.txtNombreHotelActivity)
        val driver = FirebaseDriver()
        val idAlojamiento = intent.getStringExtra("idAlojamiento")
        val alojamiento = runBlocking { driver.getAlojamiento(idAlojamiento!!) }

        txtNombre.text = alojamiento!!.nombre
    }

    private fun botones() {
        val btnLogo: ImageView = findViewById(R.id.LogoAppHotelView)

        btnLogo.setOnClickListener {
            finish()
        }
    }
}