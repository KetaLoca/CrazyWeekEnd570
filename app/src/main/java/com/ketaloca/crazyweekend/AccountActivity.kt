package com.ketaloca.crazyweekend

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.set
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class AccountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_account)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        inicio()
        botones()

    }

    private fun inicio() {
        title = "Mi Cuenta"
        val txtemail: TextView = findViewById(R.id.txtemail)
        val txtnombre: EditText = findViewById(R.id.txtnombre)
        val txtapellidos: EditText = findViewById(R.id.txtapellidos)
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        val email: String? = auth.currentUser?.email
        val driver = FirebaseDriver()

        txtemail.text = email
        val user = driver.getUser(txtemail.text.toString())
        txtnombre.setText(user.nombre)
        txtapellidos.setText(user.apellidos)

    }

    private fun botones() {
        val btnborrar: Button = findViewById(R.id.btneliminar)
        val btnguardar: Button = findViewById(R.id.btnguardar)
        val btnlimpiar: Button = findViewById(R.id.btnlimpiar)
        val btnLogo: ImageView = findViewById(R.id.btnLogo)
        val txtemail: TextView = findViewById(R.id.txtemail)
        val txtnombre: EditText = findViewById(R.id.txtnombre)
        val txtapellidos: EditText = findViewById(R.id.txtapellidos)

        btnLogo.setOnClickListener() {
            finish()
        }

        btnlimpiar.setOnClickListener() {
            txtnombre.text.clear()
            txtapellidos.text.clear()
        }

        btnguardar.setOnClickListener() {
            if (txtnombre.text.isEmpty() || txtapellidos.text.isEmpty()) {
                val builder = AlertDialog.Builder(this)
                    .setTitle("Datos incompletos")
                    .setMessage("Por favor, introduzca nombre y apellidos")
                    .setPositiveButton("Aceptar") { dialog, _ ->
                        txtnombre.text.clear()
                        txtapellidos.text.clear()
                        txtnombre.requestFocus()
                    }
                    .create()
                    .show()
            } else {
                val user = recogerUser()
                val driver = FirebaseDriver()
                driver.addUser(user!!)
            }
        }

        btnborrar.setOnClickListener() {
            val driver = FirebaseDriver()
            driver.deleteUser(txtemail.text.toString())
        }

    }

    fun recogerUser(): DataClasses.user? {
        val txtemail: TextView = findViewById(R.id.txtemail)
        val txtnombre: EditText = findViewById(R.id.txtnombre)
        val txtapellidos: EditText = findViewById(R.id.txtapellidos)
        val user = DataClasses.user(
            txtemail.text.toString(),
            txtnombre.text.toString(),
            txtapellidos.text.toString()
        )
        user.nombre = txtnombre.text.toString()
        user.apellidos = txtapellidos.text.toString()
        return user
    }

}