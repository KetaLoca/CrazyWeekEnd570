package com.ketaloca.crazyweekend.modelo

import java.time.LocalDate

class DataClasses {
    data class user(
        var email: String? = null,
        var nombre: String? = null,
        var apellidos: String? = null
    )


    data class alojamiento(
       // var id: Int? = null,
        var nombre: String? = null,
        var descripcion: String? = null,
       // var imagen: String? = null
    )

    data class reserva(
        var emailUser: Int? = null,
        var idalojamiento: alojamiento? = null,
        var fechaInicio: LocalDate? = null,
        var fechaFin: LocalDate? = null
    )

}