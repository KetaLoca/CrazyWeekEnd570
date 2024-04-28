package com.ketaloca.crazyweekend.vista

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.util.Calendar

class DatePickerFragment(
    val listener: (day: Int, montyh: Int, year: Int) -> Unit,
    val check1: Boolean,
    val check2: Boolean,
    val diaComprobado: LocalDate?
) :
    DialogFragment(), DatePickerDialog.OnDateSetListener {
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        listener(dayOfMonth, month, year)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c: Calendar = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)
        val picker =
            DatePickerDialog(activity as Context, this, year, month, day)

        if (check1 && check2) {
            val days = getDiasDiferencia(LocalDate.now(), diaComprobado!!)
            picker.datePicker.minDate = c.timeInMillis
            c.add(Calendar.DAY_OF_MONTH, +days)
            picker.datePicker.maxDate = c.timeInMillis
        } else if (check1) {
            picker.datePicker.minDate = c.timeInMillis
        } else if (check2) {
            val days = getDiasDiferencia(LocalDate.now(), diaComprobado!!)
            c.add(Calendar.DAY_OF_MONTH, +days)
            picker.datePicker.minDate = c.timeInMillis
        } else {
            picker.datePicker.minDate = c.timeInMillis
        }


        return picker
    }

    private fun getDiasDiferencia(date1: LocalDate, date2: LocalDate): Int {
        val periodo = Period.between(date1, date2)
        val days = periodo.days
        return days
    }
}