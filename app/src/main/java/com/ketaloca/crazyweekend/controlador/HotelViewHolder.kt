package com.ketaloca.crazyweekend.controlador

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ketaloca.crazyweekend.R
import com.ketaloca.crazyweekend.modelo.DataClasses

class HotelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val img = view.findViewById<ImageView>(R.id.imageView)
    val nombre = view.findViewById<TextView>(R.id.txtNombre)
    val descripcion = view.findViewById<TextView>(R.id.txtDescripcion)

    fun render(hotelModel:DataClasses.hotel) {
        nombre.text = hotelModel.nombre
        descripcion.text = hotelModel.descripcion
    }
}