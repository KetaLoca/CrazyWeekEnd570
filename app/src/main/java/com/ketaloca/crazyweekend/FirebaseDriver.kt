package com.ketaloca.crazyweekend

import android.app.AlertDialog
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class FirebaseDriver {
    private val db = FirebaseFirestore.getInstance()
    fun addUser(user: DataClasses.user) {
        db.collection("users").document(user.email).set(
            hashMapOf(
                "nombre" to user.nombre,
                "apellidos" to user.apellidos
            )
        )
    }

    fun deleteUser(email: String) {
        db.collection("users").document(email).delete()
    }

    fun getUser(email: String): DataClasses.user {
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("users").document(email)
        var user: DataClasses.user = DataClasses.user("", "Usuario", "vacío")
        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document.exists()) {
                    val data = document.data
                    val hashMap = data as HashMap<String, Any>

                    val nombre:String = hashMap["nombre"] as String
                    val apellidos:String = hashMap["apellidos"] as String
                    user.nombre = nombre
                    user.apellidos = apellidos

                } else {
                    println("El documento no existe")
                }
            } else {
                println("Error al leer el documento: ${task.exception}")
            }
        }
        return user
    }
}