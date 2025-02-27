package com.ketaloca.crazyweekend.vista

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
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
import java.lang.Exception
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

        btnLogo.setOnClickListener {
            finish()
            finish()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        dateInicio.setOnClickListener { showDatePickerDialogInicio() }
        dateFin.setOnClickListener { showDatePickerDialogFin() }

    }

    private fun initRecyclerView() {
        try {
            val recyclerView = findViewById<RecyclerView>(id.recyclerViewBuscar)
            val driver = FirebaseDriver()
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter =
                AlojamientoAdapter(runBlocking { driver.getAlojamientosList() }) { alojamiento ->
                    onItemSelected(
                        alojamiento
                    )
                }
        } catch (e: Exception) {
            e.printStackTrace()
            val builder = AlertDialog.Builder(this).setTitle("Error")
                .setMessage("Ha ocurrido un error al iniciar el RecyclerView")
                .setNeutralButton("Entendido") { dialog, _ -> }.create().show()
        }
    }

    private fun onItemSelected(alojamiento: DataClasses.Alojamiento) {
        if (comprobarFinal && comprobarInicio) {
            val intent = Intent(this, AlojamientoActivity::class.java)
            intent.putExtra("idAlojamiento", alojamiento.id)
            intent.putExtra("fechaInicio", fechaInicio.toString())
            intent.putExtra("fechaFinal", fechaFinal.toString())

            startActivity(intent)

        } else {
            val builder = AlertDialog.Builder(this)
                .setTitle("Seleccione las fechas")
                .setMessage("Debe seleccionar fechas de inicio y fin antes de continuar")
                .setPositiveButton("Aceptar") { dialog, _ ->
                }
                .create()
                .show()
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
        val etDate: EditText = findViewById(R.id.etDatePrueba)
        val mes = month + 1
        val date = LocalDate.of(year, mes, day)
        fechaInicio = date

        etDate.setText("Día inicio Reserva -> $date")
        comprobarInicio = true
        //actualizarLista()
    }

    fun onDateSelectedFin(day: Int, month: Int, year: Int) {
        val etDateFin: EditText = findViewById(R.id.etDateFinPrueba)
        val mes = month + 1
        val date = LocalDate.of(year, mes, day)
        fechaFinal = date

        etDateFin.setText("Día final Reserva -> $date")
        comprobarFinal = true
        //actualizarLista()
    }

    private fun actualizarLista() {
        if (comprobarInicio && comprobarFinal) {

            val driver = FirebaseDriver()
            val recyclerView = findViewById<RecyclerView>(id.recyclerViewBuscar)
            recyclerView.adapter =
                AlojamientoAdapter(runBlocking {
                    driver.updateAlojamientos(
                        fechaInicio,
                        fechaFinal,
                    )
                }) { alojamiento ->
                    onItemSelected(
                        alojamiento
                    )
                }
        }
    }
}