package com.ketaloca.crazyweekend.controlador

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.ketaloca.crazyweekend.modelo.DataClasses
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.util.UUID
import kotlin.streams.toList

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

    fun deleteReserva(idReserva: String) {
        db.collection("reservas").document(idReserva).delete()
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

    suspend fun updateAlojamientos(
        inicio: LocalDate,
        fin: LocalDate
    ): List<DataClasses.alojamiento> {
        val rangoComprobar = inicio.datesUntil(fin.plusDays(1)).toList()
        val listAlojamientos = getAlojamientosList()
        val listaFiltrada = mutableListOf<DataClasses.alojamiento>()
        val collection = db.collection("reservas")
        val snapshot = collection.get().await()
        val listReservas = snapshot.documents.map { document ->
            document.toObject<DataClasses.reserva>() ?: DataClasses.reserva()
        }

        for (alojamiento in listAlojamientos) {
            var isAvailable = true
            for (reserva in listReservas) {
                if (ComprobarRangos(inicio, fin, reserva)) {
                    isAvailable = false
                }
            }

            if (isAvailable) {
                listaFiltrada.add(alojamiento)
            }
        }
        return listaFiltrada
    }

    private fun ComprobarRangos(
        inicio: LocalDate,
        fin: LocalDate,
        reserva: DataClasses.reserva
    ): Boolean {
        var comprobar = false
        if (inicio <= LocalDate.parse(reserva.fechaInicio) && fin >= LocalDate.parse(reserva.fechaFin)) {
            comprobar = true
        }
        return comprobar
    }
}