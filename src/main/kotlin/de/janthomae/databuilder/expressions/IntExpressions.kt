package de.janthomae.databuilder.expressions

import de.janthomae.databuilder.MyInt
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.atomic.AtomicLong


/**
 * Produces a random int between 0 and the given int (inclusive)
 */
public fun int(to: Long): MyInt = int(0, to)

/**
 * Produces a random int between the given two ints (inclusive)
 */
public fun int(from: Long, to: Long): MyInt = MyInt { ThreadLocalRandom.current().nextLong(from, to + 1) }

/**
 * Produces an expression that will return a new integer counting up each time it is evaluated. Default start value is 1.
 */
public fun counter(start: Long = 1): MyInt = Counter(start)

/**
 * Operators
 */
operator public fun Long.plus(other: MyInt): MyInt = MyInt { this + other.computeValue() }
operator public fun Int.plus(other: MyInt): MyInt = toLong() + other
operator public fun MyInt.plus(other: MyInt): MyInt = MyInt { computeValue() + other.computeValue() }

operator public fun Long.times(other: MyInt): MyInt = MyInt { this * other.computeValue() }
operator public fun Int.times(other: MyInt): MyInt = toLong() * other
operator public fun MyInt.times(other: MyInt): MyInt = MyInt { computeValue() * other.computeValue() }

private class Counter(start: Long, val counter: AtomicLong = AtomicLong(start)) : MyInt({ counter.andIncrement })

