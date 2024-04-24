package com.ketaloca.crazyweekend.modelo

import com.google.type.LatLng
import java.time.LocalDate

class DataClasses {
    data class user(
        var email: String? = null,
        var nombre: String? = null,
        var apellidos: String? = null
    )


    data class hotel(
       // var idhotel: Int? = null,
       // var emailgerente: String? = null,
        val nombre: String? = null,
        val descripcion: String? = null,
       // var imagen:String?=null
    )

    data class habitacion(var numero: Int? = null, var idhotel: Int? = null)

    data class reserva(
        var iduser: Int? = null,
        var habitacion: habitacion? = null,
        var fechaInicio: LocalDate? = null,
        var fechaFin: LocalDate? = null
    )

}