package com.ketaloca.crazyweekend

import com.google.firebase.firestore.FirebaseFirestore

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
}