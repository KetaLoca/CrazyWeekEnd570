package com.ketaloca.crazyweekend.controlador

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ketaloca.crazyweekend.R
import com.ketaloca.crazyweekend.modelo.DataClasses

class HotelAdapter(
    private val hotelesList: List<DataClasses.alojamiento>,
    private val onClickListener: (DataClasses.alojamiento) -> Unit
) :
    RecyclerView.Adapter<HotelViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelViewHolder {
        val layaoutInflater = LayoutInflater.from(parent.context)
        return HotelViewHolder(layaoutInflater.inflate(R.layout.item_hotel, parent, false))
    }

    override fun onBindViewHolder(holder: HotelViewHolder, position: Int) {
        val item = hotelesList[position]
        holder.render(item, onClickListener)
    }


    override fun getItemCount(): Int {
        return hotelesList.size
    }
}