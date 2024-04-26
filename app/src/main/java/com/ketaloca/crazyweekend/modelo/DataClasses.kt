package com.ketaloca.crazyweekend.modelo

import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDate
import java.util.UUID

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
        val id: String? = null,
        val emailuser: String? = null,
        val idalojamiento: String? = null,
        var fechaInicio: String? = null,
        var fechaFin: String? = null
    )


}