package com.ketaloca.crazyweekend

import android.app.AlertDialog
import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

class FirebaseDriver {
    private val db = Firebase.firestore
    fun addUser(user: DataClasses.user, context: Context) {
        db.collection("users").document(user.email!!).set(user).addOnSuccessListener {
            val builder = androidx.appcompat.app.AlertDialog.Builder(context)
                .setTitle("Usuario actualizado")
                .setMessage("Datos del usuario actualizados correctamente")
                .setPositiveButton("Entendido") { dialog, _ ->
                }
                .create()
                .show()
        }
            .addOnFailureListener {
                val builder = androidx.appcompat.app.AlertDialog.Builder(context)
                    .setTitle("Error")
                    .setMessage("Ha ocurrido un error actualizando el usuario")
                    .setPositiveButton("Entendido") { dialog, _ ->
                    }
                    .create()
                    .show()
            }

    }

    fun deleteUser(email: String) {
        db.collection("users").document(email).delete()
    }

    suspend fun getUser(email: String?): DataClasses.user? {
        val docRef = db.collection("users").document(email!!)
        val document = docRef.get().await()
        return document.toObject<DataClasses.user>()
    }

}