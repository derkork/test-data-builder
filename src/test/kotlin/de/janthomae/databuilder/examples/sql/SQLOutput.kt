package de.janthomae.databuilder.examples.sql

import de.janthomae.databuilder.expressions.*
import de.janthomae.databuilder.obj
import de.janthomae.databuilder.serialization.toSql


fun main(args: Array<String>) {

    // JSON output does allow nesting of structures.
    // Let's build some test data structure of course records

    // first 20 define persons
    var persons = (20 * obj {
        prop("id", uuid())
        // pick a random first name
        prop("firstName", oneOf("Jack", "Jamie", "John", "Joanne", "Jill", "Jessica"))
        // pick a random last name
        prop("lastName", oneOf("Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller"))
        // a random age
        prop("age", int(20, 55))
        // a random date within the last 5 months
        prop("subscriptionDate", isoDate(lastMonth(5)))
    }).materialize()  // materializes the data, so it can now be propertly referenced


    // Now that these records are materialized we can reference properties from them to build derived properties
    // in this case, augment each person with an email address derived from their first & last names
    persons = augment(persons, {
        prop("email", string(get("firstName")).toLower() + "." + string(get("lastName")).toLower() + "@" + oneOf("googlemail.com", "yahoo.com", "live.com", "outlook.com"))
    }).materialize()

    // now define a few courses
    val courses = (5 * obj {
        prop("id", uuid())
        prop("name", pick(counter(0), "Mathematics", "Physics", "Chemistry", "Astronomy", "Biology"))
        prop("startDate", date("yyyy-MM-dd", lastMonth(3)))
    }).materialize()


    // cross join persons and courses and select 40 random elements from this.
    val assignments = choose(cross(persons, courses, {
        prop("person", firstObject().get("id"))
        prop("course", secondObject().get("id"))
    }), 40)



    println(persons.toSql("persons"))
    println(courses.toSql("courses"))
    println(assignments.toSql("person_course_assignments"))


}