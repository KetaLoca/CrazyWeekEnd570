package com.ketaloca.crazyweekend.modelo

class DataClasses {
    data class user(
        val email: String? = null,
        var nombre: String? = null,
        var apellidos: String? = null
    )


    data class alojamiento(
        val id: String? = null,
        val nombre: String? = null,
        val descripcion: String? = null,
        val img: String? = null
    )

    data class reserva(
        val id: String? = null,
        val emailuser: String? = null,
        val idalojamiento: String? = null,
        val fechaInicio: String? = null,
        val fechaFin: String? = null
    )


}