package day09

import println
import readInput

fun main() {
    val input = readInput("day09/Day09")
    val d = Day09(input)

    d.part1().println()
    d.part2().println()

//    // test if implementation meets criteria from the description, like:
//     val testInput = readInput("day1/Day01_test")
//     check(d.part1() == 142)
}

class Day09(private val input: List<String>) {
    // Generate a list of history from the input
    private val report = parseInput(input)

    fun part1(): Int {
        // For each historyLine in the report, call the predict function and sum the results
        return report.sumOf { it.predictNext() }
    }

    // Parse the input into a list of lists of ints (report) where each list of ints is a historyLine
    private fun parseInput(input: List<String>): List<List<Int>> {
        return input.map { line ->
            line.split(" ").map { it.toInt() }
        }
    }

    // Extension function on a list of ints that predicts the next number in the sequence
    private fun List<Int>.predictNext(): Int {

        // Generate a sequence where the first element is the input list this extension function is called on
        // It generates the next element by zipping current element with next element and calculating the difference
        // Sequences are lazily evaluated, so this will only be evaluated when the result is needed
        val steps = generateSequence(this) { layer ->
            layer.zipWithNext { current, next -> next - current }
            // generateSequence will create an infinite sequence, we know when to stop when all differences are 0
            // takeWhile will take elements from the sequence until the predicate is true
        }.takeWhile { layer -> layer.any { it != 0 } }

        // Get the last values in each calculated layer return the sum
        val lastValues = steps.map { it.last() }
        return lastValues.sum()
    }

    fun part2(): Int {
        // Reverse the report and call predictNext on each historyLine and sum the results
        return report.map { it.reversed() }.sumOf { it.predictNext() }
    }

    private fun List<Int>.predictPrevious(): Int {
        return 0
    }
}
