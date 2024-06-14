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
import java.lang.Exception

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
        botones()

    }

    override fun onStart() {
        super.onStart()
        actualizarCampos()
    }

    private fun inicio() {
        title = "Mi Cuenta"
    }

    private fun botones() {
        val txtemail: TextView = findViewById(R.id.txtemail)
        val btnborrar: Button = findViewById(R.id.btneliminar)
        val btnguardar: Button = findViewById(R.id.btnguardar)
        val btnLogo: ImageView = findViewById(R.id.btnLogo)
        val driver = FirebaseDriver()

        btnLogo.setOnClickListener() {
            finish()
        }

        btnguardar.setOnClickListener() {
            try {
                if (comprobarCampos()) {
                    val user = recogerUser()
                    driver.addUser(user, this)
                    limpiarCampos()
                    actualizarCampos()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        btnborrar.setOnClickListener() {
            driver.deleteUser(txtemail.text.toString())
            limpiarCampos()
            actualizarCampos()
        }

    }

    private fun recogerUser(): DataClasses.User {
        val txtemail: TextView = findViewById(R.id.txtemail)
        val txtnombre: EditText = findViewById(R.id.txtnombre)
        val txtapellidos: EditText = findViewById(R.id.txtapellidos)
        val user = DataClasses.User(
            txtemail.text.toString(),
            txtnombre.text.toString(),
            txtapellidos.text.toString()
        )
        return user
    }

    private fun comprobarCampos(): Boolean {
        val txtnombre: EditText = findViewById(R.id.txtnombre)
        val txtapellidos: EditText = findViewById(R.id.txtapellidos)
        var comprobacion = false
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
            comprobacion = true
        }
        return comprobacion
    }

    private fun limpiarCampos() {
        val txtnombre: EditText = findViewById(R.id.txtnombre)
        val txtapellidos: EditText = findViewById(R.id.txtapellidos)

        txtnombre.text.clear()
        txtapellidos.text.clear()
        txtnombre.clearFocus()
        txtapellidos.clearFocus()
    }

    private fun actualizarCampos() {
        val txtemail: TextView = findViewById(R.id.txtemail)
        val txtnombre: EditText = findViewById(R.id.txtnombre)
        val txtapellidos: EditText = findViewById(R.id.txtapellidos)
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        val email: String? = auth.currentUser?.email
        val driver = FirebaseDriver()

        txtemail.text = email
        if (email != null) {
            val user: DataClasses.User? = runBlocking { driver.getUser(email) }
            if (user != null) {
                txtnombre.setText(user.nombre)
                txtapellidos.setText(user.apellidos)
            }
        }
    }
}
