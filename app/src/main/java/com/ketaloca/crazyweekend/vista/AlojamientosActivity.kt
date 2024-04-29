package com.ketaloca.crazyweekend.vista

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ketaloca.crazyweekend.R
import com.ketaloca.crazyweekend.R.*
import com.ketaloca.crazyweekend.controlador.FirebaseDriver
import com.ketaloca.crazyweekend.controlador.AlojamientoAdapter
import com.ketaloca.crazyweekend.modelo.DataClasses
import kotlinx.coroutines.runBlocking
import java.time.LocalDate

class AlojamientosActivity : AppCompatActivity() {

    private lateinit var fechaInicio: LocalDate
    private lateinit var fechaFinal: LocalDate
    private var comprobarInicio: Boolean = false
    private var comprobarFinal: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(layout.activity_alojamientos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        inicio()
    }

    private fun inicio() {
        initRecyclerView()
        botones()
    }

    private fun botones() {
        val btnLogo: ImageView = findViewById(R.id.imgLogoApp)
        val dateInicio: EditText = findViewById(R.id.etDatePrueba)
        val dateFin: EditText = findViewById(R.id.etDateFinPrueba)

        btnLogo.setOnClickListener { finish() }
        dateInicio.setOnClickListener { showDatePickerDialogInicio() }
        dateFin.setOnClickListener { showDatePickerDialogFin() }

    }

    private fun initRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(id.recyclerViewBuscar)
        val driver = FirebaseDriver()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter =
            AlojamientoAdapter(runBlocking { driver.getAlojamientosList() }) { alojamiento ->
                onItemSelected(
                    alojamiento
                )
            }
    }

    private fun onItemSelected(alojamiento: DataClasses.alojamiento) {
        val intent = Intent(this, AlojamientoActivity::class.java)
        intent.putExtra("idAlojamiento", alojamiento.id)
        startActivity(intent)
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
        val etDate: EditText = findViewById(R.id.etDatePrueba)
        val mes = month + 1
        val date = LocalDate.of(year, mes, day)
        fechaInicio = date

        etDate.setText("Día inicio reserva -> $date")
        comprobarInicio = true

    }

    fun onDateSelectedFin(day: Int, month: Int, year: Int) {
        val etDateFin: EditText = findViewById(R.id.etDateFinPrueba)
        val mes = month + 1
        val date = LocalDate.of(year, mes, day)
        fechaFinal = date

        etDateFin.setText("Día final reserva -> $date")
        comprobarFinal = true

    }
}