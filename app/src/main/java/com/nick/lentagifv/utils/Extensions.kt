package com.nick.lentagifv.utils

import java.text.ParseException
import java.util.*
import java.util.concurrent.TimeUnit

fun Long?.humanDate(): String {
    if (this == null){
        return "Неизвестно"
    }

    var convTime: String? = null
    try {

    
        val nowTime = Calendar.getInstance(TimeZone.getTimeZone("UTC")).time
        val dateDiff = nowTime.time - this*1000
        val second: Long = TimeUnit.MILLISECONDS.toSeconds(dateDiff)
        val minute: Long = TimeUnit.MILLISECONDS.toMinutes(dateDiff)
        val hour: Long = TimeUnit.MILLISECONDS.toHours(dateDiff)
        val day: Long = TimeUnit.MILLISECONDS.toDays(dateDiff)
        when {
            second < 1 -> {
                convTime = "сейчас"
            }
            second < 60 -> {
                convTime = "$second секунд"
            }
            minute < 60 -> {
                convTime = "$minute минут"
            }
            hour < 24 -> {
                convTime = "$hour час"
            }
            day >= 7 -> {
                convTime = if (day > 360) {
                    (day / 360).toString() + " лет"
                } else if (day > 30) {
                    (day / 30).toString() + " месяцев"
                } else {
                    (day / 7).toString() + " недель"
                }
            }
            day < 7 -> {
                convTime = "$day дней"
            }
        }
    } catch (e: ParseException) {
        return ""
    }

    return convTime!!
}
