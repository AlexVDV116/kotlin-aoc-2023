package day1

import convertStringToNumber
import readInput

fun main() {
    val input = readInput("day1/Day01")
    val d = Day01(input)

    d.part1()
    d.part2()

    // test if implementation meets criteria from the description, like:
//     val testInput = readInput("day1/Day01_test")
//     check(d.part1() == 142)
}

class Day01(private val input: List<String>) {
    private val words = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten")
    private val digits = List(10) { "$it" }

    fun part1(): Int {
        return input.sumOf { line ->
            val firstDigit = line.first { it.isDigit() }
            val lastDigit = line.last { it.isDigit() }
            "$firstDigit$lastDigit".toInt()
        }.also(::println)
    }

    fun part2(): Int {
        return input.sumOf { line ->
            getNumbers(line)
        }.also(::println)
    }

    private fun getNumbers(line: String): Int {
        val (firstWordIdx, firstWord) = line.findAnyOf(words) ?: (Int.MAX_VALUE to "not found")
        val (firstDigitIdx, firstDigit) = line.findAnyOf(digits) ?: (Int.MAX_VALUE to "not found")
        val (secondWordIdx, secondWord) = line.findLastAnyOf(words, line.lastIndex) ?: (Int.MIN_VALUE to "not found")
        val (secondDigitIdx, secondDigit) = line.findLastAnyOf(digits, line.lastIndex) ?: (Int.MIN_VALUE to "not found")

        var result = ""
        if (firstDigitIdx < firstWordIdx) result += firstDigit else result += convertStringToNumber(firstWord)
        if (secondDigitIdx > secondWordIdx) result += secondDigit else result += convertStringToNumber(secondWord)
        return result.toInt()
    }
}
