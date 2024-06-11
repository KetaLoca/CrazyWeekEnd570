package com.ketaloca.crazyweekend.vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
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
import java.util.UUID

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        inicio()
        botones()
    }

    private fun inicio() {
        title = "Home"
        val driver = FirebaseDriver()
        val auth = FirebaseAuth.getInstance()
        val email: String? = auth.currentUser?.email
        val viewemail: TextView = findViewById(R.id.viewemail)
        val viewreservas: TextView = findViewById(R.id.viewreservas)
        val reservas = runBlocking { driver.getReservasByEmail(email!!) }

        viewemail.text = email

        if (reservas.isEmpty()) {
            viewreservas.text = "No hay reservas aún"
        } else {
            viewreservas.text = "${reservas.size} reserva/s"
        }

    }

    private fun botones() {
        val btnlogout: Button = findViewById(R.id.btnLogOut)
        val btnbuscar: Button = findViewById(R.id.btnBuscar)
        val btnMisReservas: Button = findViewById(R.id.btnMisReservas)
        val btnMiCuenta: Button = findViewById(R.id.btnMiCuenta)

        btnlogout.setOnClickListener() {
            LogOut()
        }

        btnMiCuenta.setOnClickListener() {
            val intent = Intent(this, AccountActivity::class.java)
            startActivity(intent)
        }

        btnbuscar.setOnClickListener() {
            val intent = Intent(this, AlojamientosActivity::class.java)
            startActivity(intent)
        }

        btnMisReservas.setOnClickListener() {
            val intent = Intent(this, ReservasActivity::class.java)
            startActivity(intent)
        }
    }

    private fun LogOut() {
        val intent = Intent(this, LoginActivity::class.java)
        val auth = FirebaseAuth.getInstance()
        auth.signOut()
        finish()
    }
}
