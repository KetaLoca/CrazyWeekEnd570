package com.ketaloca.crazyweekend.controlador

import com.ketaloca.crazyweekend.modelo.DataClasses
import kotlinx.coroutines.runBlocking
import java.util.UUID

class HotelesListProvider {
    companion object {
        val driver = FirebaseDriver()
        val lista = runBlocking { driver.getAlojamientosList() }
    }
}