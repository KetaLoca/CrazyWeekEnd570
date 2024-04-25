package com.ketaloca.crazyweekend.controlador

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ketaloca.crazyweekend.R
import com.ketaloca.crazyweekend.modelo.DataClasses

class HotelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val img = view.findViewById<ImageView>(R.id.imageView)
    val nombre = view.findViewById<TextView>(R.id.txtNombre)
    val descripcion = view.findViewById<TextView>(R.id.txtDescripcion)

    fun render(alojamientoModel:DataClasses.alojamiento) {
        nombre.text = alojamientoModel.nombre
        descripcion.text = alojamientoModel.descripcion
    }
}