package com.ketaloca.crazyweekend

import com.google.type.LatLng
import java.time.LocalDate

class DataClasses {
    data class user(
        var email: String? = null,
        var nombre: String? = null,
        var apellidos: String? = null
    )


    data class hotel(
        var idhotel: Int,
        var emailgerente: String,
        var nombre: String,
        var habitaciones: ArrayList<habitacion>,
        var ubicacion: LatLng
    )

    data class habitacion(var numero: Int, var idhotel: Int)

    data class reserva(
        var iduser: Int,
        var habitacion: habitacion,
        var fechaInicio: LocalDate,
        var fechaFin: LocalDate
    )

}