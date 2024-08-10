package com.example.notesapplication.util

import android.text.format.DateFormat
import java.util.Calendar

object DateUtil {
    const val YYYY_MM_DD_HH_MM_SS = "dd/MM/yyyy HH:mm:ss"
    fun getDate(timestamp: Long): String {
        return try {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = timestamp
            return DateFormat.format(YYYY_MM_DD_HH_MM_SS, calendar).toString()
        } catch (e: Exception) {
            ""
        }
    }
}