package de.janthomae.databuilder.data

import de.janthomae.databuilder.MyString
import de.janthomae.databuilder.expressions.oneOf
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

public fun firstName(): MyString = oneOf(*allFirstNames)
public fun maleFirstName(): MyString = oneOf(*maleFirstNames)
public fun femaleFirstName(): MyString = oneOf(*femaleFirstNames)
public fun lastName(): MyString = oneOf(*lastNames)

/**
 * Produces a random UUID.
 */
public fun uuid(): MyString = MyString { UUID.randomUUID().toString() }

/**
 * Produces a short random UUID.
 */
public fun uuidShort() : MyString = MyString { UUID.randomUUID().toString().replace("-", "")}