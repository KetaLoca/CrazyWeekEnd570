package com.ketaloca.crazyweekend.controlador

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ketaloca.crazyweekend.R
import com.ketaloca.crazyweekend.modelo.DataClasses
import com.ketaloca.crazyweekend.vista.HotelActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.NonDisposableHandle.parent

class HotelViewHolder(val view: View) : RecyclerView.ViewHolder(view) {


    val img = view.findViewById<ImageView>(R.id.imageView)
    val nombre = view.findViewById<TextView>(R.id.txtNombre)
    val descripcion = view.findViewById<TextView>(R.id.txtDescripcion)

    fun render(
        alojamientoModel: DataClasses.alojamiento,
        onClickListener: (DataClasses.alojamiento) -> Unit
    ) {
        nombre.text = alojamientoModel.nombre
        descripcion.text = alojamientoModel.descripcion
        itemView.setOnClickListener { onClickListener(alojamientoModel) }
    }
}