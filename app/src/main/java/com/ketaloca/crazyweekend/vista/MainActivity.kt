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
import com.ketaloca.crazyweekend.controlador.HotelesListProvider
import com.ketaloca.crazyweekend.modelo.DataClasses
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
        val auth = FirebaseAuth.getInstance()
        val email: String? = auth.currentUser?.email
        val viewemail: TextView = findViewById(R.id.viewemail)
        val viewreservas: TextView = findViewById(R.id.viewreservas)

        if (email != null) {
            viewemail.text = email
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
            val intent = Intent(this, BuscarActivity::class.java)
            startActivity(intent)
        }

        btnMisReservas.setOnClickListener() {
            añadirAlojamientos()
        }
    }

    private fun LogOut() {
        val intent = Intent(this, LoginActivity::class.java)
        val auth = FirebaseAuth.getInstance()
        auth.signOut()
        finish()
    }

    fun añadirAlojamientos() {
        val driver = FirebaseDriver()
        val house: DataClasses.alojamiento =
            DataClasses.alojamiento(
                UUID.randomUUID().toString(),
                "Torre Lago",
                "Un sitio mágico para relajarse"
            )
        driver.addAlojammiento(house)
    }
}
