package de.janthomae.databuilder.examples.sql

import de.janthomae.databuilder.data.firstName
import de.janthomae.databuilder.data.lastName
import de.janthomae.databuilder.data.uuid
import de.janthomae.databuilder.expressions.*
import de.janthomae.databuilder.obj
import de.janthomae.databuilder.serialization.SqlSerializationFormat
import de.janthomae.databuilder.serialization.toSqlInsert


fun main(args: Array<String>) {

    // SQL output cannot be nested, one can have multiple
    // objects referencing each other.

    // first 20 define persons
    var persons = (20 * obj {
        prop("id", uuid())
        // pick a random first name
        prop("firstName", firstName())
        // pick a random last name
        prop("lastName", lastName())
        // a random age
        prop("age", int(20, 55))
        // a random date within the last 5 months
        prop("subscriptionDate", isoDate(lastMonth(5)))
    }).materialize()  // materializes the data, so it can now be propertly referenced


    // Now that these records are materialized we can reference properties from them to build derived properties
    // in this case, augment each person with an email address derived from their first & last names
    persons = augment(persons, {
        prop("email", string(get(this, "firstName")).toLower() + "." + string(get(this, "lastName")).toLower() + "@" + oneOf("googlemail.com", "yahoo.com", "live.com", "outlook.com"))
    }).materialize()

    // now define a few courses
    val courses = (5 * obj {
        prop("id", uuid())
        prop("name", pick(counter(0), "Mathematics", "Physics", "Chemistry", "Astronomy", "Biology"))
        prop("startDate", date("yyyy-MM-dd", lastMonth(3)))
    }).materialize()


    // cross join persons and courses and select 40 random elements from this.
    val assignments = choose(cross(persons, courses, {
        prop("person", get(firstObject(), "id"))
        prop("course", get(secondObject(), "id"))
    }), 40)


    // finally print SQL INSERT statements for MySQL
    println(persons.toSqlInsert("persons", SqlSerializationFormat.mysql()))
    println(courses.toSqlInsert("courses", SqlSerializationFormat.mysql()))
    println(assignments.toSqlInsert("person_course_assignments", SqlSerializationFormat.mysql()))

    // you can also write it to a file

    /*
    writeToFile(persons.toSqlInsert("persons", SqlSerializationFormat.mysql()), "./output.sql")
    writeToFile(courses.toSqlInsert("courses", SqlSerializationFormat.mysql()), "./output.sql", true) // append
    writeToFile(assignments.toSqlInsert("person_course_assignments", SqlSerializationFormat.mysql()), "./output.sql", true)
    */
}