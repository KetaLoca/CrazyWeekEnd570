package com.ketaloca.crazyweekend.controlador

import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.ketaloca.crazyweekend.modelo.DataClasses
import kotlinx.coroutines.tasks.await
import java.time.LocalDate

class FirebaseDriver {

    private val db = Firebase.firestore

    fun addUser(user: DataClasses.User, context: Context) {
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

    suspend fun getUser(email: String?): DataClasses.User? {
        val docRef = db.collection("users").document(email!!)
        val document = docRef.get().await()

        return document.toObject<DataClasses.User>()
    }

    fun addAlojammiento(alojamiento: DataClasses.Alojamiento) {
        db.collection("alojamientos").document(alojamiento.id!!).set(alojamiento)
    }

    suspend fun getAlojamiento(idAlojamiento: String): DataClasses.Alojamiento? {
        val docRef = db.collection("alojamientos").document(idAlojamiento)
        val document = docRef.get().await()

        return document.toObject<DataClasses.Alojamiento>()
    }


    suspend fun getAlojamientosList(): List<DataClasses.Alojamiento> {
        val collectionRef = db.collection("alojamientos")
        val snapshot = collectionRef.get().await()

        return snapshot.documents.map { document ->
            document.toObject<DataClasses.Alojamiento>() ?: DataClasses.Alojamiento()
        }
    }

    fun addReserva(reserva: DataClasses.Reserva) {
        db.collection("reservas").document(reserva.id!!).set(reserva)
    }

    fun deleteReserva(idReserva: String) {
        db.collection("reservas").document(idReserva).delete()
    }

    suspend fun getReserva(idReserva: String): DataClasses.Reserva? {
        val docRef = db.collection("reservas").document(idReserva)
        val document = docRef.get().await()

        return document.toObject<DataClasses.Reserva>()
    }

    suspend fun getReservasByEmail(email: String): List<DataClasses.Reserva> {
        val collection = db.collection("reservas").whereEqualTo("emailuser", email)
        val snapshot = collection.get().await()

        return snapshot.documents.map { document ->
            document.toObject<DataClasses.Reserva>() ?: DataClasses.Reserva()
        }

    }

    suspend fun updateAlojamientos(
        inicio: LocalDate,
        fin: LocalDate
    ): List<DataClasses.Alojamiento> {
        val listAlojamientos = getAlojamientosList()
        val listaFiltrada = mutableListOf<DataClasses.Alojamiento>()
        val collection = db.collection("reservas")
        val snapshot = collection.get().await()
        val listReservas = snapshot.documents.map { document ->
            document.toObject<DataClasses.Reserva>() ?: DataClasses.Reserva()
        }
        var isAvailable = true

        for (alojamiento in listAlojamientos) {
            for (reserva in listReservas) {
                if (ComprobarRangos(inicio, fin, reserva)) {
                    isAvailable = false
                    break
                } else {
                    isAvailable = true
                }
            }

            if (isAvailable) {
                listaFiltrada.add(alojamiento)
            }
        }
        return listaFiltrada.toList()
    }

    private fun ComprobarRangos(
        inicio: LocalDate,
        fin: LocalDate,
        reserva: DataClasses.Reserva
    ): Boolean {
        var comprobar = false
        if (inicio <= LocalDate.parse(reserva.fechaInicio) && fin >= LocalDate.parse(reserva.fechaFin)) {
            comprobar = true
        }
        return comprobar
    }
}