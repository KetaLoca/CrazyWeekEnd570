package com.ketaloca.crazyweekend.vista

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import java.util.UUID

class AlojamientoActivity : AppCompatActivity() {

    private lateinit var fechaInicio: LocalDate
    private lateinit var fechaFinal: LocalDate
    var comprobarInicio: Boolean = false
    var comprobarFinal: Boolean = false


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
        val alojamiento = getAlojamiento()

        txtNombre.text = alojamiento.nombre
    }

    private fun botones() {
        val btnLogo: ImageView = findViewById(R.id.LogoAppHotelView)
        val etDAte: EditText = findViewById(R.id.etDate)
        val etDateFin: EditText = findViewById(R.id.etDateFin)
        val btnReservar: Button = findViewById(R.id.btnReservar)

        btnLogo.setOnClickListener {
            finish()
        }

        etDAte.setOnClickListener {
            showDatePickerDialogInicio()
        }
        etDateFin.setOnClickListener {
            showDatePickerDialogFin()
        }

        btnReservar.setOnClickListener {
            añadirReserva()
        }
    }

    private fun showDatePickerDialogInicio() {
        if (comprobarFinal) {
            val datePicker =
                DatePickerFragment(
                    { day, month, year -> onDateSelectedInicio(day, month, year) },
                    true,
                    true,
                    fechaFinal
                )
            datePicker.show(supportFragmentManager, "DatePicker")
        } else {
            val datePicker =
                DatePickerFragment(
                    { day, month, year -> onDateSelectedInicio(day, month, year) },
                    true,
                    false,
                    null
                )
            datePicker.show(supportFragmentManager, "DatePicker")
        }
    }

    private fun showDatePickerDialogFin() {
        if (comprobarInicio) {
            val datePicker =
                DatePickerFragment(
                    { day, month, year -> onDateSelectedFin(day, month, year) },
                    false,
                    true,
                    fechaInicio
                )
            datePicker.show(supportFragmentManager, "DatePicker")
        } else {
            val datePicker =
                DatePickerFragment(
                    { day, month, year -> onDateSelectedFin(day, month, year) },
                    false,
                    false,
                    null
                )
            datePicker.show(supportFragmentManager, "DatePicker")
        }
    }

    fun onDateSelectedInicio(day: Int, month: Int, year: Int) {
        val etDate: EditText = findViewById(R.id.etDate)
        val mes = month + 1
        val date = LocalDate.of(year, mes, day)
        fechaInicio = date

        etDate.setText("Día inicio reserva -> $date")
        comprobarInicio = true

    }

    fun onDateSelectedFin(day: Int, month: Int, year: Int) {
        val etDateFin: EditText = findViewById(R.id.etDateFin)
        val mes = month + 1
        val date = LocalDate.of(year, mes, day)
        fechaFinal = date

        etDateFin.setText("Día final reserva -> $date")
        comprobarFinal = true

    }

    private fun añadirReserva() {
        val driver = FirebaseDriver()
        val id = UUID.randomUUID().toString()
        val auth = FirebaseAuth.getInstance()
        val emailUsuario = auth.currentUser!!.email
        val idAlojamiento = intent.getStringExtra("idAlojamiento")


        if (comprobarInicio && comprobarFinal) {

            val reserva = DataClasses.reserva(
                id,
                emailUsuario,
                idAlojamiento,
                fechaInicio.toString(),
                fechaFinal.toString()
            )

            driver.addReserva(reserva)

            val builder = AlertDialog.Builder(this).setTitle("Reserva añadida")
                .setMessage("Alojamiento reservado correctamente")
                .setPositiveButton("Entendido") { dialog, _ -> finish() }.create().show()
        } else {
            val builder = AlertDialog.Builder(this).setTitle("Campos incompletos")
                .setMessage("Porfavor rellene las fechas de entrada y salida")
                .setPositiveButton("Entendido") { dialog, _ -> }.create().show()
        }

    }

    private fun getAlojamiento(): DataClasses.alojamiento {
        val driver = FirebaseDriver()
        val idAlojamiento = intent.getStringExtra("idAlojamiento")
        val alojamiento = runBlocking { driver.getAlojamiento(idAlojamiento!!) }
        return alojamiento!!
    }


}