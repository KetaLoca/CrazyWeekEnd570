package com.ketaloca.crazyweekend.modelo

class DataClasses {
    data class User(
        val email: String? = null,
        val nombre: String? = null,
        val apellidos: String? = null,
        val telefono: Int? = null
    )


    data class Alojamiento(
        val id: String? = null,
        val nombre: String? = null,
        val descripcion: String? = null,
        val precio: Float? = null,
        val img: String? = null
    )

    data class Reserva(
        val id: String? = null,
        val emailuser: String? = null,
        val idalojamiento: String? = null,
        val fechaInicio: String? = null,
        val fechaFin: String? = null
    )


}