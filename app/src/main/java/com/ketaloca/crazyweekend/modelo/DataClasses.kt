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
        val id: String?,
        var nombre: String? = null,
        var descripcion: String? = null,
        // var imagen: String? = null
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(id)
            parcel.writeString(nombre)
            parcel.writeString(descripcion)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<alojamiento> {
            override fun createFromParcel(parcel: Parcel): alojamiento {
                return alojamiento(parcel)
            }

            override fun newArray(size: Int): Array<alojamiento?> {
                return arrayOfNulls(size)
            }
        }
    }

    data class reserva(
        val emailUser: String,
        val idalojamiento: String,
        var fechaInicio: LocalDate,
        var fechaFin: LocalDate
    )


}