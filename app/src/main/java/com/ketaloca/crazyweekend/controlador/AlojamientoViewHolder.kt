package com.ketaloca.crazyweekend.controlador

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import com.ketaloca.crazyweekend.R
import com.ketaloca.crazyweekend.modelo.DataClasses
import com.ketaloca.crazyweekend.vista.AlojamientosActivity

class AlojamientoViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    val storageRef = FirebaseStorage.getInstance()
    val imgref = storageRef.reference.child("images/casarural.jpg")
    val imgURL = "https://firebasestorage.googleapis.com/v0/b/crazyweekend570.appspot.com/o/images%2Fcasarural.jpg?alt=media&token=2630ad56-b1a9-4f00-bfd4-fc422f0c95f0"
    val img = view.findViewById<ImageView>(R.id.imageView)
    val nombre = view.findViewById<TextView>(R.id.txtNombre)
    val descripcion = view.findViewById<TextView>(R.id.txtDescripcionReserva)

    fun render(
        alojamientoModel: DataClasses.alojamiento,
        onClickListener: (DataClasses.alojamiento) -> Unit
    ) {
        nombre.text = alojamientoModel.nombre
        descripcion.text = alojamientoModel.descripcion
        itemView.setOnClickListener { onClickListener(alojamientoModel) }
    }
}