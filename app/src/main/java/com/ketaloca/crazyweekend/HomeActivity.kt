package com.ketaloca.crazyweekend

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        inicio()
        botones()
    }

    private fun inicio() {

        val auth = FirebaseAuth.getInstance()
        val email: String? = auth.currentUser?.email
        val viewemail: TextView = findViewById(R.id.viewemail)
        val viewreservas: TextView = findViewById(R.id.viewreservas)
        val viewhoteles: TextView = findViewById(R.id.viewhoteles)

        if (email != null) {
            viewemail.text = email
        }
    }

    private fun botones() {
        val btnlogout: Button = findViewById(R.id.btnLogOut)
        val btnreservar: Button = findViewById(R.id.btnreservar)
        val btnofertar: Button = findViewById(R.id.btnofertar)
        val btnMisReservas: Button = findViewById(R.id.btnMisReservas)
        val btnMisHoteles: Button = findViewById(R.id.btnMisHoteles)

        btnlogout.setOnClickListener() {
            LogOut()
        }
    }

    private fun LogOut() {
        val intent = Intent(this, MainActivity::class.java)
        val auth = FirebaseAuth.getInstance()
        auth.signOut()
        finish()
    }
}
