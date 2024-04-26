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
import com.ketaloca.crazyweekend.R
import com.ketaloca.crazyweekend.controlador.FirebaseDriver
import com.ketaloca.crazyweekend.modelo.DataClasses
import kotlinx.coroutines.runBlocking

class ReservaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reserva)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //inicio()
    }

    private fun inicio() {
        val txtNombreAlojamiento: TextView = findViewById(R.id.txtNombreReservaActivity)
        val txtFechaInicio: EditText = findViewById(R.id.etDateInicioReserva)
        val txtFechaFinal: EditText = findViewById(R.id.etDateFinReserva)

        val reserva = getReserva()
        val alojamiento = getAlojamiento(reserva.idalojamiento!!)

        txtNombreAlojamiento.text = alojamiento.nombre
        txtFechaInicio.setText(reserva.fechaInicio.toString())
        txtFechaFinal.setText(reserva.fechaFin.toString())

        botones()
    }

    private fun botones() {
        val btnLogo: ImageView = findViewById(R.id.LogoAppReservaView)
        val btnEliminarReserva: Button = findViewById(R.id.btnEliminarReserva)
        val idReserva = intent.getStringExtra("idreserva")

        btnLogo.setOnClickListener { finish() }

        btnEliminarReserva.setOnClickListener {
            val driver = FirebaseDriver()
            driver.deleteReserva(idReserva!!)
            finish()
        }
    }

    private fun getReserva(): DataClasses.reserva {
        val driver = FirebaseDriver()
        val idReserva = intent.getStringExtra("idreserva")
        val reserva = runBlocking { driver.getReserva(idReserva!!) }

        return reserva!!
    }

    private fun getAlojamiento(id: String): DataClasses.alojamiento {
        val driver = FirebaseDriver()
        val alojamiento = runBlocking { driver.getAlojamiento(id) }

        return alojamiento!!


    }

}