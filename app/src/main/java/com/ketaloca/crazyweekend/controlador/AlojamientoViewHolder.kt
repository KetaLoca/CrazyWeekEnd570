package com.ketaloca.crazyweekend.controlador

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ketaloca.crazyweekend.R
import com.ketaloca.crazyweekend.modelo.DataClasses

class AlojamientoViewHolder(val view: View) : RecyclerView.ViewHolder(view) {


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