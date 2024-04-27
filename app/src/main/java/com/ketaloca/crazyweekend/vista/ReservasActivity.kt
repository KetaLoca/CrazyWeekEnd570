package com.ketaloca.crazyweekend.vista

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.ketaloca.crazyweekend.R
import com.ketaloca.crazyweekend.controlador.FirebaseDriver
import com.ketaloca.crazyweekend.controlador.ReservaAdapter
import com.ketaloca.crazyweekend.modelo.DataClasses
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import java.util.UUID

class ReservasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reservas)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        inicio()
    }
    private fun inicio() {
        initRecyclerView()
        val btnLogo: ImageView = findViewById(R.id.imgLogoAppReservas)
        btnLogo.setOnClickListener {
            finish()
        }
    }

    private fun initRecyclerView() {
        val driver = FirebaseDriver()
        val auth = FirebaseAuth.getInstance()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewReservas)
        val email = auth.currentUser!!.email
        val list = getList()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter =
            ReservaAdapter(runBlocking { driver.getReservasByEmail(email!!) }) { reserva ->
                onItemSelected(reserva)
            }
    }

    private fun onItemSelected(reserva: DataClasses.reserva) {
        val intent = Intent(this, ReservaActivity::class.java)
        intent.putExtra("idreserva", reserva.id)
        startActivity(intent)
    }

    private fun getList(): List<DataClasses.reserva> {
        var list: List<DataClasses.reserva> = listOf(
            DataClasses.reserva(
                UUID.randomUUID().toString(),
                "anfetas@gmail.com",
                "2819ec71-bb95-4e9e-9a23-14d75cb5a9a6",
                "",
                ""
            ),
            DataClasses.reserva(
                UUID.randomUUID().toString(),
                "anfetas@gmail.com",
                "2819ec71-bb95-4e9e-9a23-14d75cb5a9a6",
                "",
                ""
            ),
            DataClasses.reserva(
                UUID.randomUUID().toString(),
                "anfetas@gmail.com",
                "2819ec71-bb95-4e9e-9a23-14d75cb5a9a6",
                "",
                ""
            )
        )
        return list
    }
}