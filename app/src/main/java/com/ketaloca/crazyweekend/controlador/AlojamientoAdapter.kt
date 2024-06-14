package com.ketaloca.crazyweekend.controlador

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ketaloca.crazyweekend.R
import com.ketaloca.crazyweekend.modelo.DataClasses

class AlojamientoAdapter(
    private var hotelesList: List<DataClasses.Alojamiento>,
    private val onClickListener: (DataClasses.Alojamiento) -> Unit
) :
    RecyclerView.Adapter<AlojamientoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlojamientoViewHolder {
        val layaoutInflater = LayoutInflater.from(parent.context)
        return AlojamientoViewHolder(layaoutInflater.inflate(R.layout.item_hotel, parent, false))
    }

    override fun onBindViewHolder(holder: AlojamientoViewHolder, position: Int) {
        val item = hotelesList[position]
        holder.render(item, onClickListener)
    }


    override fun getItemCount(): Int {
        return hotelesList.size
    }

}