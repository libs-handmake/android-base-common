package common.hoangdz.lib.extensions

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun DateFormat.parseOrNull(date: String?): Date? {
    return try {
        parse(date.orEmpty())
    } catch (ex: Exception) {
        null
    }
}

fun List<DateFormat>.parseOrNull(date: String?): Date? {
    forEach { dateParser ->
        val parsedDate = dateParser.parseOrNull(date)
        if (parsedDate != null) {
            return parsedDate
        }
    }
    return null
}

fun DateFormat.formatOrNull(time: Long?): String? {
    return try {
        format(Date(time!!))
    } catch (ex: Exception) {
        null
    }
}

fun formatDateLocale(date: Long): String? {
    val dateFormat =
        DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT, Locale.getDefault())
    return dateFormat.format(date)
}

fun Float.millisToMinuteAndSecond(): String {
    val second = ((this / 1000) % 60).toLong().let { if (it < 10) "0$it" else "$it" }
    val minutes = (this / 60_000L).toLong().let { if (it < 10) "0$it" else "$it" }

    return "$minutes:$second"
}

infix fun Long.areTheSameDay(other: Long): Boolean {
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
    return format.formatOrNull(other) == format.formatOrNull(this)
}