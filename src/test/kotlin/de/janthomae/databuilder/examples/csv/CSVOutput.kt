package de.janthomae.databuilder.examples.csv;

import de.janthomae.databuilder.data.uuid
import de.janthomae.databuilder.expressions.*
import de.janthomae.databuilder.obj
import de.janthomae.databuilder.serialization.toCsv

fun main(args: Array<String>) {

    // Define 20k objects (must be flat for CSV output)
    val data = 22 * obj {
        // This column will just count up
        prop("Index", counter())
        // This combines a string with a counter
        prop("Combined", string("someSerial") + counter())
        // This column will contain a unique id
        prop("ID", uuid())
        // This column will contain a random 3 to 7 words of 'Lorem Ipsum'
        prop("Text", lorem(int(3,7)))
        // This column will contain a random date within the last 3 months
        prop("Date", isoDate(lastMonth(3)))
        // This column will contain a random number between 5 and 5000
        prop("Integer", int(5, 5000))
    }

    // write 20k lines to the file given through the command line
    println(data.toCsv())//, args[0])
}