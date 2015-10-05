package de.janthomae.databuilder.expressions

import de.janthomae.databuilder.MyExpression
import de.janthomae.databuilder.MyInt
import de.janthomae.databuilder.MyString
import de.svenjacobs.loremipsum.LoremIpsum
import java.util.*
import java.util.concurrent.ThreadLocalRandom

/**
 * Selects a random element of the given array.
 */
public fun oneOf(vararg options: String): MyString =
        MyString { options[ThreadLocalRandom.current().nextInt(0, options.size())] }

/**
 * Selects the designated element of the given array. The index will
 * be rolled over if it exceeds the bounds of the array.
 */
public fun pick(index:MyInt, vararg options:String) : MyString =
        MyString { options[index.computeValue().toInt() % options.size()] }

/**
 * Constructs a MyString from a plain string.
 */
public fun string(other: String): MyString = MyString(other)

/**
 * Constructs a MyString from any expression
 */
public fun string(other: MyExpression<*>) : MyString = MyString {other.computeValue().toString()}

public fun MyString.toLower() : MyString = MyString {computeValue().toLowerCase()}



/**
 * Produces the first words of "Lorem ipsum".
 */
public fun lorem(words: MyInt): MyString = MyString { LoremIpsum().getWords(words.computeValue().toInt()) }
public fun lorem(words: Int): MyString = MyString (LoremIpsum().getWords(words))


/**
 * Pads the result of the given expression left with the given chars.
 */
public fun padLeft(length:Int, expression:MyExpression<*>, character:Char = '0') : MyString =
        MyString { expression.computeValue().toString().padStart(length,character) }

// concatenation of JsonStrings
operator public fun MyString.plus(other: MyExpression<*>): MyString = MyString { computeValue() + other.computeValue() }
operator public fun MyString.plus(other: String): MyString = MyString { computeValue() + other }

