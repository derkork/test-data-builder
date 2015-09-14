package de.janthomae.databuilder.expressions

import de.janthomae.databuilder.MyExpression
import de.janthomae.databuilder.MyExpressionBase
import de.janthomae.databuilder.MyInt
import de.janthomae.databuilder.MyString
import de.svenjacobs.loremipsum.LoremIpsum
import java.util.UUID
import java.util.concurrent.ThreadLocalRandom


val names = arrayOf("Horst", "Janosch", "Peter", "Manfred")


public fun name(): MyString = oneOf(*names)


public fun oneOf(vararg options: String): MyString =
        MyString { options[ThreadLocalRandom.current().nextInt(0, options.size())] }

public fun oneOf(input: MyExpression<Array<MyExpression<String>>>): MyString =
        MyString {
            val array = input.computeValue()
            val obj = array[ThreadLocalRandom.current().nextInt(0, array.size())]
            obj.computeValue()
        }

public fun string(other: String): MyString = MyString(other)

public fun uuid(): MyString = MyString { UUID.randomUUID().toString() }

public fun lorem(words: MyInt): MyString = MyString { LoremIpsum().getWords(words.computeValue()) }
public fun lorem(words: Int): MyString = MyString (LoremIpsum().getWords(words))

// concatenation of JsonStrings

public fun MyString.plus(other: MyExpressionBase<*>): MyString = MyString { computeValue() + other.computeValue() }

public fun MyString.plus(other: String): MyString = MyString { computeValue() + other }

