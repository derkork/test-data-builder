package de.janthomae.databuilder.data

import de.janthomae.databuilder.MyInt
import de.janthomae.databuilder.MyString
import de.janthomae.databuilder.expressions.oneOf
import de.svenjacobs.loremipsum.LoremIpsum
import java.util.*

val maleFirstNames = arrayOf("James", "John", "Robert", "Michael", "William", "David", "Richard", "Joseph", "Charles",
        "Thomas", "Christopher", "Daniel", "Matthew", "Donald", "Anthony", "Mark", "Paul", "Steven", "George", "Kenneth",
        "Andrew", "Edward", "Joshua", "Brian", "Kevin", "Ronald", "Timothy", "Jason", "Jeffrey", "Ryan", "Gary", "Nicholas",
        "Eric", "Jacob", "Stephen", "Jonathan", "Larry", "Frank", "Scott", "Justin", "Brandon", "Raymond", "Gregory", "Samuel",
        "Benjamin", "Patrick", "Jack", "Dennis", "Alexander", "Jerry", "Tyler", "Henry", "Douglas", "Aaron", "Peter", "Jose",
        "Walter", "Adam", "Zachary", "Nathan", "Harold", "Kyle", "Carl", "Arthur", "Gerald", "Roger", "Keith", "Lawrence",
        "Jeremy", "Terry", "Albert", "Joe", "Sean", "Willie", "Christian", "Jesse", "Austin", "Billy", "Bruce", "Ralph",
        "Bryan", "Ethan", "Roy", "Eugene", "Jordan", "Louis", "Wayne", "Alan", "Harry", "Russell", "Juan", "Dylan", "Randy",
        "Philip", "Vincent", "Noah", "Bobby", "Howard", "Gabriel", "Johnny"
)

val femaleFirstNames = arrayOf(
        "Mary", "Patricia", "Jennifer", "Elizabeth", "Linda", "Barbara", "Susan", "Margaret", "Jessica", "Sarah",
        "Dorothy", "Karen", "Nancy", "Betty", "Lisa", "Sandra", "Ashley", "Kimberly", "Donna", "Helen", "Carol",
        "Michelle", "Emily", "Amanda", "Melissa", "Deborah", "Laura", "Stephanie", "Rebecca", "Sharon", "Cynthia",
        "Kathleen", "Anna", "Shirley", "Ruth", "Amy", "Angela", "Brenda", "Virginia", "Pamela", "Catherine", "Katherine",
        "Nicole", "Christine", "Samantha", "Janet", "Debra", "Carolyn", "Rachel", "Heather", "Maria", "Diane",
        "Julie", "Joyce", "Emma", "Frances", "Evelyn", "Joan", "Martha", "Christina", "Kelly", "Lauren", "Victoria",
        "Judith", "Alice", "Ann", "Cheryl", "Jean", "Doris", "Megan", "Marie", "Andrea", "Kathryn", "Jacqueline",
        "Gloria", "Teresa", "Sara", "Janice", "Hannah", "Julia", "Rose", "Theresa", "Grace", "Judy", "Beverly",
        "Olivia", "Denise", "Marilyn", "Amber", "Danielle", "Brittany", "Diana", "Mildred", "Jane", "Madison",
        "Lori", "Tiffany", "Kathy", "Tammy", "Kayla"
)

val lastNames = arrayOf(
        "Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor", "Anderson",
        "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson", "Garcia", "Martinez", "Robinson", "Clark",
        "Rodriguez", "Lewis", "Lee", "Walker", "Hall", "Allen", "Young", "Hernandez", "King", "Wright", "Lopez", "Hill",
        "Scott", "Green", "Adams", "Baker", "Gonzalez", "Nelson", "Carter", "Mitchell", "Perez", "Roberts", "Turner",
        "Phillips", "Campbell", "Parker", "Evans", "Edwards", "Collins", "Stewart", "Sanchez", "Morris", "Rogers",
        "Reed", "Cook", "Morgan", "Bell", "Murphy", "Bailey", "Rivera", "Cooper", "Richardson", "Cox", "Howard",
        "Ward", "Torres", "Peterson", "Gray", "Ramirez", "James", "Watson", "Brooks", "Kelly", "Sanders", "Price",
        "Bennett", "Wood", "Barnes", "Ross", "Henderson", "Coleman", "Jenkins", "Perry", "Powell", "Long", "Patterson",
        "Hughes", "Flores", "Washington", "Butler", "Simmons", "Foster", "Gonzales", "Bryant", "Alexander", "Russell",
        "Griffin", "Diaz", "Hayes"
)
val allFirstNames = maleFirstNames + femaleFirstNames

/**
 * Produces a random male or female first name.
 */
public fun firstName(): MyString = oneOf(*allFirstNames)

/**
 * Produces a random male first name.
 */
public fun maleFirstName(): MyString = oneOf(*maleFirstNames)

/**
 * Produces a random female first name.
 */
public fun femaleFirstName(): MyString = oneOf(*femaleFirstNames)

/**
 * Produces a random last name.
 */
public fun lastName(): MyString = oneOf(*lastNames)

/**
 * Produces a random UUID.
 */
public fun uuid(): MyString = MyString { UUID.randomUUID().toString() }

/**
 * Produces a short random UUID.
 */
public fun uuidShort() : MyString = MyString { UUID.randomUUID().toString().replace("-", "")}

/**
 * Produces the first words of "Lorem ipsum".
 */
public fun lorem(words: MyInt): MyString = MyString { LoremIpsum().getWords(words.computeValue().toInt()) }

/**
 * Produces the first words of "Lorem ipsum".
 */
public fun lorem(words: Int): MyString = lorem(MyInt(words))

