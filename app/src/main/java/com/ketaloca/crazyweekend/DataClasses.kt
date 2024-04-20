package com.ketaloca.crazyweekend

import com.google.type.LatLng
import java.time.LocalDate

class DataClasses {
    data class user(var iduser: Int, var email: String, var reservas: ArrayList<reserva>)


    data class hotel(
        var idhotel: Int,
        var idmanager: Int,
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