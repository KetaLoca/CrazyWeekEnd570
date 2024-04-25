package com.ketaloca.crazyweekend.modelo

import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDate

class DataClasses {
    data class user(
        val email: String? = null,
        var nombre: String? = null,
        var apellidos: String? = null
    )


    data class alojamiento(
        val id: String? = null,
        var nombre: String? = null,
        var descripcion: String? = null,
    )

    data class reserva(
        val emailUser: String,
        val idalojamiento: String,
        var fechaInicio: LocalDate,
        var fechaFin: LocalDate
    )


}