package com.ketaloca.crazyweekend.controlador

import com.ketaloca.crazyweekend.modelo.DataClasses
import java.util.UUID

class HotelesListProvider {
    companion object {
        val lista = listOf<DataClasses.alojamiento>(
            DataClasses.alojamiento(
                UUID.randomUUID().toString(),
                "Alojamiento cósmico",
                "Un auténtico paraíso para desonectar de la realidad"
            ),
            DataClasses.alojamiento(
                UUID.randomUUID().toString(),
                "Anfetas",
                "Aquí se mueve toda la merca"
            )
        )
    }
}