package com.ketaloca.crazyweekend

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        inicio()
    }

    fun inicio() {
        title = "Autenticación"
        val btnlogin: Button = findViewById(R.id.btnlogin)
        val btnregistrar: Button = findViewById((R.id.btnregistrar))
        btnlogin.setOnClickListener() {
            login()
        }

        btnregistrar.setOnClickListener() {
            registrar()
        }

    }

    private fun login() {

        val auth = FirebaseAuth.getInstance()
        val txtemail: EditText = findViewById(R.id.viewemail)
        val txtpassword: EditText = findViewById(R.id.txtpassword)

        if (txtemail.text.isEmpty()) {
            val builder = AlertDialog.Builder(this)
                .setTitle("Datos incompletos")
                .setMessage("Por favor, introduzca el correo electrónico")
                .setPositiveButton("Aceptar") { dialog, _ ->
                    txtemail.requestFocus()
                }
                .create()
                .show()
        } else if (txtpassword.text.isEmpty()) {
            val builder = AlertDialog.Builder(this)
                .setTitle("Datos incompletos")
                .setMessage("Por favor, introduzca la contraseña")
                .setPositiveButton("Aceptar") { dialog, _ ->
                    txtpassword.requestFocus()
                }
                .create()
                .show()
        } else
            auth.signInWithEmailAndPassword(
                txtemail.text.toString(),
                txtpassword.text.toString()
            ).addOnCompleteListener() {
                if (it.isSuccessful) {
                    val builder = AlertDialog.Builder(this)
                        .setTitle("Login correcto")
                        .setMessage("Usuario logueado correctamente")
                        .setPositiveButton("Aceptar") { dialog, _ ->
                            txtemail.text.clear()
                            txtpassword.text.clear()
                            txtemail.requestFocus()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }
                        .create()
                        .show()
                } else {
                    val builder = AlertDialog.Builder(this)
                        .setTitle("Ha ocurrido un error")
                        .setMessage("No ha podido loguearse")
                        .setPositiveButton("Aceptar") { dialog, _ ->
                            txtemail.text.clear()
                            txtpassword.text.clear()
                        }
                        .create()
                        .show()
                }
            }
    }

    private fun registrar() {
        val auth = FirebaseAuth.getInstance()
        val txtemail: EditText = findViewById(R.id.viewemail)
        val txtpassword: EditText = findViewById(R.id.txtpassword)

        if (txtemail.text.isEmpty()) {
            val builder = AlertDialog.Builder(this)
                .setTitle("Datos incompletos")
                .setMessage("Por favor, introduzca el correo electrónico")
                .setPositiveButton("Aceptar") { dialog, _ ->
                    txtemail.requestFocus()
                }
                .create()
                .show()
        } else if (txtpassword.text.isEmpty()) {
            val builder2 = AlertDialog.Builder(this)
                .setTitle("Datos incompletos")
                .setMessage("Por favor, introduzca la contraseña")
                .setPositiveButton("Aceptar") { dialog, _ ->
                    txtpassword.requestFocus()
                }
                .create()
                .show()
        } else
            auth.createUserWithEmailAndPassword(
                txtemail.text.toString(),
                txtpassword.text.toString()
            ).addOnCompleteListener() {
                if (it.isSuccessful) {
                    val builder = AlertDialog.Builder(this)
                        .setTitle("Registro correcto")
                        .setMessage("Usuario creado correctamente, ya puede loguearse")
                        .setPositiveButton("Aceptar") { dialog, _ ->
                            txtemail.text.clear()
                            txtpassword.text.clear()
                            txtemail.requestFocus()
                        }
                        .create()
                        .show()
                } else {
                    val builder = AlertDialog.Builder(this)
                        .setTitle("Ha ocurrido un error")
                        .setMessage("Compruebe que los datos introducidos son correctos")
                        .setPositiveButton("Aceptar") { dialog, _ ->
                            txtemail.text.clear()
                            txtpassword.text.clear()
                        }
                        .create()
                        .show()
                }
            }

    }

}


