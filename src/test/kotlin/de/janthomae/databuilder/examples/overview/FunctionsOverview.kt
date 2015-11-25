package de.janthomae.databuilder.examples.overview

import de.janthomae.databuilder.data.*
import de.janthomae.databuilder.expressions.*
import de.janthomae.databuilder.obj
import de.janthomae.databuilder.serialization.SqlSerializationFormat
import de.janthomae.databuilder.serialization.toJson
import de.janthomae.databuilder.serialization.toSqlInsert

fun main(args: Array<String>) {

    // This is an overview about the built-in functions.

    printHeading("Strings")

    val person = obj {
        prop("firstName", firstName())       // a random first name
        prop("lastName", lastName())         // a random last name
        prop("mother", femaleFirstName())    // a random female first name
        prop("father", maleFirstName())      // a random male first name
        prop("id", uuid())                   // a unique id
        prop("shortId", uuidShort())         // a shorter unique id
        prop("lorem", lorem(6))              // the six first words of "Lorem ipsum"
    }

    println(person.toJson())                 // objects can be converted into JSON

    printHeading("Numbers")

    val numbers = obj {
        prop("randomInt", int(25))           // a random int between 0 and 25
        prop("rangedInt", int(-10, 10))      // a random int between -10 and 10
        prop("gtin", gtin(205023))           // calculates a GTIN checksum (formerly known as EAN) from the input and adds it to the number

        val counter = counter()              // a counter. Everytime it is used it counts one up
        prop("counter1", counter)
        prop("counter2", counter)
        prop("counter3", counter)
    }

    println(numbers.toJson())

    printHeading("Date and Time")

    val dates = obj {
        prop("pastDate", sqlDate(lastDay(7)))           // some random instant in the last 7 days formatted as SQL date
        prop("pastDateTime", sqlDateTime(lastHour(3)))  // some random instant in the last 3 hours formatted as SQL datetime
        prop("pastIsoDate", isoDate(lastMonth(5)))      // some random instant in the last 5 months formatted as ISO8601
        prop("futureTime", sqlTime(nextMinute(10)))     // some random instant in the next 10 minutes formatted as SQL time
        prop("rangedDate",                              // some random instant within the last 5 and the next 5 months
            isoDate(lastMonth(5), nextMonth(5)))        // formatted as ISO8601 date

    }

    println(dates.toJson())

    printHeading("Functions")

    val albums = 3 * obj { // creates 3 objects
        prop("name", string("Album ") + counter())      // builds a name from a fixed string and a counter.
        prop("rating", string("*").repeat(int(1, 5)))   // a star rating from 1 to 5 stars
        prop("ranking",                                 // picks randomly from the list of options
                oneOf("Top100", "Top10", "NumberOne"))
    }

    println(albums.toSqlInsert(// list of objects can be converted into SQL INSERT statements
            "albums",
            SqlSerializationFormat.mysql()))

}

fun printHeading(heading: String) {
    println()
    println(heading)
    println("----------------------------------------------------------------")
}
