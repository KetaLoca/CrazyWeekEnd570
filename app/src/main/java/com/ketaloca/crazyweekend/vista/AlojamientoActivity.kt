package com.ketaloca.crazyweekend.vista

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.ketaloca.crazyweekend.R
import com.ketaloca.crazyweekend.controlador.FirebaseDriver
import com.ketaloca.crazyweekend.modelo.DataClasses
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import java.time.Period
import java.util.UUID

class AlojamientoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_alojamiento)
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
        val txtDate: TextView = findViewById(R.id.txtSelectedDate)
        val fechaInicio: LocalDate = LocalDate.parse(intent.getStringExtra("fechaInicio"))
        val fechaFin: LocalDate = LocalDate.parse(intent.getStringExtra("fechaFinal"))
        val alojamiento = getAlojamiento()

        txtNombre.text = alojamiento.nombre
        txtDate.text = "$fechaInicio <--> $fechaFin"
    }

    private fun botones() {
        val btnLogo: ImageView = findViewById(R.id.LogoAppHotelView)
        val btnReservar: Button = findViewById(R.id.btnReservar)

        btnLogo.setOnClickListener {
            finish()
        }

        btnReservar.setOnClickListener {
            añadirReserva()
        }
    }

    private fun añadirReserva() {
        val driver = FirebaseDriver()
        val auth = FirebaseAuth.getInstance()

        val idAlojamiento = intent.getStringExtra("idAlojamiento")
        val fechaInicio: LocalDate = LocalDate.parse(intent.getStringExtra("fechaInicio"))
        val fechaFinal: LocalDate = LocalDate.parse(intent.getStringExtra("fechaFinal"))
        val email = auth.currentUser?.email

        val reserva: DataClasses.reserva = DataClasses.reserva(
            UUID.randomUUID().toString(),
            email,
            idAlojamiento,
            fechaInicio.toString(),
            fechaFinal.toString()
        )

        driver.addReserva(reserva)
    }

    private fun getAlojamiento(): DataClasses.alojamiento {
        val driver = FirebaseDriver()
        val idAlojamiento = intent.getStringExtra("idAlojamiento")
        val alojamiento = runBlocking { driver.getAlojamiento(idAlojamiento!!) }
        return alojamiento!!
    }

    private fun comprobarRango(reserva: DataClasses.reserva): Boolean {
        val driver = FirebaseDriver()
        var date1 = LocalDate.parse(reserva.fechaInicio)
        val date2: LocalDate = LocalDate.parse(reserva.fechaFin)
        val periodo = Period.between(date1, date2)
        val mutableList = mutableListOf<LocalDate>()

        while (date1 <= date2) {
            mutableList.add(date1)
            date1 = date1.plusDays(1)
        }

        val listComprobar = mutableList
        val listTotal = runBlocking { driver.getDiasReservados() }
        if (listTotal.containsAll(listComprobar)) {
            return true
        } else {
            return false
        }
    }


}