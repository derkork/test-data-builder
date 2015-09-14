package de.janthomae.databuilder.expressions

import de.janthomae.databuilder.MyString
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat
import java.util.concurrent.ThreadLocalRandom


public fun isoDate(from: DateTime) : MyString = isoDate(from, DateTime.now())

public fun isoDate(from: DateTime, until: DateTime ) : MyString {
    return MyString({
        val period = until.getMillis() + from.getMillis()
        val instant = ThreadLocalRandom.current().nextLong(period)
        ISODateTimeFormat.dateTime().withZoneUTC().print(from.plusMillis(instant.toInt()))
    })
}

public fun day(year:Int, month: Int, day: Int) : DateTime = DateTime(year, month, day, 0, 0)
public fun lastMonth(month: Int = 1) : DateTime = DateTime.now().minusMonths(month)