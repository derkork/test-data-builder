package de.janthomae.databuilder.expressions

import de.janthomae.databuilder.MyInt
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.atomic.AtomicInteger


/**
 * Produces a random int between 0 and the given int (inclusive)
 */
public fun int(to: Int): MyInt = int(0, to)

/**
 * Produces a random int between the given two ints (inclusive)
 */
public fun int(from: Int, to: Int): MyInt = MyInt { ThreadLocalRandom.current().nextInt(from, to+1) }

/**
 * Produces an expression that will return a new integer counting up each time it is evaluated. Default start value is 1.
 */
public fun counter(start:Int = 1) : MyInt = Counter(start)


private class Counter(start:Int, val counter : AtomicInteger = AtomicInteger(start)) : MyInt({counter.getAndIncrement()})