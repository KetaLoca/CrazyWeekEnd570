package com.ketaloca.crazyweekend.controlador

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ketaloca.crazyweekend.R
import com.ketaloca.crazyweekend.modelo.DataClasses

class ReservaAdapter(
    private val reservasList: List<DataClasses.Reserva>,
    private val onClickListener: (DataClasses.Reserva) -> Unit
) : RecyclerView.Adapter<ReservaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ReservaViewHolder(layoutInflater.inflate(R.layout.item_reserva, parent, false))
    }

    override fun getItemCount(): Int {
        return reservasList.size
    }

    override fun onBindViewHolder(holder: ReservaViewHolder, position: Int) {
        val item = reservasList[position]
        holder.render(item, onClickListener)
    }
}