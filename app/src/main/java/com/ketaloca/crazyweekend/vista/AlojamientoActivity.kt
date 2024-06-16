package com.ketaloca.crazyweekend.vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.ketaloca.crazyweekend.R
import com.ketaloca.crazyweekend.controlador.FirebaseDriver
import com.ketaloca.crazyweekend.modelo.DataClasses
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import java.time.LocalDate
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
        try {
            val driver = FirebaseDriver()
            val auth = FirebaseAuth.getInstance()

            val idAlojamiento = intent.getStringExtra("idAlojamiento")
            val fechaInicio: LocalDate = LocalDate.parse(intent.getStringExtra("fechaInicio"))
            val fechaFinal: LocalDate = LocalDate.parse(intent.getStringExtra("fechaFinal"))
            val email = auth.currentUser?.email

            val reserva: DataClasses.Reserva = DataClasses.Reserva(
                UUID.randomUUID().toString(),
                email,
                idAlojamiento,
                fechaInicio.toString(),
                fechaFinal.toString()
            )

            driver.addReserva(reserva)
            val builder = AlertDialog.Builder(this).setTitle("Reserva añadida")
                .setMessage("Reserva creada correctamente")
                .setPositiveButton("Entendido") { dialog, _ ->
                    finish()
                    finish()
                    finish()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }.create().show()
        } catch (e: Exception) {
            e.printStackTrace()
            val builder = AlertDialog.Builder(this).setTitle("Error")
                .setMessage("Ha ocurrido un error al intentar añadir la Reserva")
                .setNeutralButton("Entendido") { dialog, _ -> }.create().show()
        }
    }

    private fun getAlojamiento(): DataClasses.Alojamiento {
        val driver = FirebaseDriver()
        val idAlojamiento = intent.getStringExtra("idAlojamiento")
        val alojamiento = runBlocking { driver.getAlojamiento(idAlojamiento!!) }
        return alojamiento!!
    }
}