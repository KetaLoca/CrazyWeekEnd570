package com.ketaloca.crazyweekend

import com.google.firebase.firestore.FirebaseFirestore

class FirebaseDriver {
    val db = FirebaseFirestore.getInstance()
    private fun addUser(user: DataClasses.user) {
        db.collection("users").document(user.email).set(hashMapOf("reservas" to user.reservas))
    }
}