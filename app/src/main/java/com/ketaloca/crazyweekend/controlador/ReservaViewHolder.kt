package com.ketaloca.crazyweekend.controlador

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ketaloca.crazyweekend.R
import com.ketaloca.crazyweekend.modelo.DataClasses
import kotlinx.coroutines.runBlocking

class ReservaViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val img = view.findViewById<ImageView>(R.id.imgReserva)
    val nombre = view.findViewById<TextView>(R.id.txtNombreReserva)
    val descripcion = view.findViewById<TextView>(R.id.txtDescripcionReserva)
    val fechaReserva = view.findViewById<TextView>(R.id.txtFechaReserva)

    fun render(reservaModel: DataClasses.reserva, onClickListener: (DataClasses.reserva) -> Unit) {
        val driver = FirebaseDriver()
        val alojamiento = runBlocking { driver.getAlojamiento(reservaModel.idalojamiento!!) }
        nombre.text = alojamiento!!.nombre
        descripcion.text = alojamiento.descripcion
        fechaReserva.text =  "Reserva -> ${reservaModel.fechaInicio}"
        itemView.setOnClickListener { onClickListener(reservaModel) }
    }
}