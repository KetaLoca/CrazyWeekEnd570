package com.ketaloca.crazyweekend.controlador

import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.ketaloca.crazyweekend.modelo.DataClasses
import kotlinx.coroutines.tasks.await
import java.util.UUID

class FirebaseDriver {
    private val db = Firebase.firestore

    fun generateRandomID(): String {
        return UUID.randomUUID().toString()
    }

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

    fun addAlojammiento(alojamiento: DataClasses.alojamiento) {
        db.collection("alojamientos").document(alojamiento.id!!).set(alojamiento)
    }

    suspend fun getAlojamiento(idAlojamiento: String): DataClasses.alojamiento? {
        val docRef = db.collection("alojamientos").document(idAlojamiento)
        val document = docRef.get().await()

        return document.toObject<DataClasses.alojamiento>()
    }


    suspend fun getAlojamientosList(): List<DataClasses.alojamiento> {
        val collectionRef = db.collection("alojamientos")
        val snapshot = collectionRef.get().await()

        return snapshot.documents.map { document ->
            document.toObject<DataClasses.alojamiento>() ?: DataClasses.alojamiento()
        }
    }

    fun addReserva(reserva: DataClasses.reserva) {
        db.collection("reservas").document(reserva.id!!).set(reserva)
    }

    suspend fun getReserva(idReserva: String): DataClasses.reserva? {
        val docRef = db.collection("reservas").document(idReserva)
        val document = docRef.get().await()

        return document.toObject<DataClasses.reserva>()
    }

    suspend fun getReservasByEmail(email: String): List<DataClasses.reserva> {
        val collection = db.collection("reservas").whereEqualTo("emailuser", email)
        val snapshot = collection.get().await()

        return snapshot.documents.map { document ->
            document.toObject<DataClasses.reserva>() ?: DataClasses.reserva()
        }

    }

}