package com.ketaloca.crazyweekend.modelo

import java.time.LocalDate

class DataClasses {
    data class user(
        var email: String? = null,
        var nombre: String? = null,
        var apellidos: String? = null
    )


    data class alojamiento(
        val id:String,
        var nombre: String? = null,
        var descripcion: String? = null,
        // var imagen: String? = null
    )

    data class reserva(
        var emailUser: String,
        var idalojamiento: String,
        var fechaInicio: LocalDate,
        var fechaFin: LocalDate
    )


}