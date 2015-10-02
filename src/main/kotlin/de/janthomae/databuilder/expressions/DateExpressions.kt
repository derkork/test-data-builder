package de.janthomae.databuilder.expressions

import de.janthomae.databuilder.MyString
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.ISODateTimeFormat
import java.util.concurrent.ThreadLocalRandom


public fun isoDate(from: DateTime): MyString = isoDate(from, DateTime.now())

public fun isoDate(from: DateTime, until: DateTime): MyString {
    return MyString({
        val period = until.millis + from.millis
        val instant = ThreadLocalRandom.current().nextLong(period)
        ISODateTimeFormat.dateTime().withZoneUTC().print(from.plusMillis(instant.toInt()))
    })
}

public fun date(pattern: String, from: DateTime): MyString = date(pattern, from, DateTime.now())

public fun date(pattern: String, from: DateTime, until: DateTime): MyString {
    return MyString({
        val period = until.millis + from.millis
        val instant = ThreadLocalRandom.current().nextLong(period)
        DateTimeFormat.forPattern(pattern).print(from.plusMillis(instant.toInt()))
    })
}

public fun day(year: Int, month: Int, day: Int): DateTime = DateTime(year, month, day, 0, 0)
public fun lastMinute(minute: Int = 1) : DateTime = DateTime.now().minusMinutes(minute)
public fun lastHour(hour: Int = 1) : DateTime = DateTime.now().minusHours(hour)
public fun lastDay(day: Int = 1) : DateTime = DateTime.now().minusDays(day)
public fun lastMonth(month: Int = 1): DateTime = DateTime.now().minusMonths(month)