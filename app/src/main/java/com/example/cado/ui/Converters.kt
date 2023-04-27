package com.example.cado.ui

import android.text.TextUtils
import androidx.databinding.InverseMethod
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

object Converters {


    @InverseMethod("stringToLocalDate")
    @JvmStatic fun localDateToString(value: LocalDate?): String {
        val local: Locale = Locale.getDefault()
        val dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", local)
        return if (value != null) "Acheté le : " + dateFormat.format(value) else ""
    }

    @JvmStatic fun stringToLocalDate(value: String?): LocalDate? {
        // Converts String to long.
        return null //à compléter
    }

    @InverseMethod("stringToReal")
    @JvmStatic fun realToString(value: Double): String? {
        val decimalFormat = DecimalFormat("#,###,###.## €")
        return decimalFormat.format(value)
    }

    @JvmStatic fun stringToReal(value: String): Double {
        var value = value
        var convert = 0.0
        // Converts String to double.
        if (!TextUtils.isEmpty(value)) {
            value = value.replace(',', '.')
            value = value.replace("€", "")
            value = value.replace("\\s".toRegex(), "")
            if (!TextUtils.isEmpty(value) && TextUtils.isDigitsOnly(value)) {
                convert = java.lang.Double.valueOf(value)
            }
        }
        return convert
    }

}