package day03

import println
import readInput

typealias SchematicRow = List<Element>

sealed class Element

data class Number(val value: Int, val xRange: IntRange, val row: Int) : Element() {
    val expandedColumn = xRange.first - 1..xRange.last + 1
    val expandedRow = row - 1..row + 1
}

fun Number(number: String, start: Int, end: Int, row: Int): Number = Number(number.toInt(), start..end, row)
data class Symbol(val value: Char, val column: Int, val row: Int) : Element()

// The engine schematic (your puzzle input, Day03.txt) consists of a visual representation of the engine.
// Any number adjacent to a symbol, even diagonally, is a "part number" and should
// be included in your sum. (Periods (.) do not count as a symbol.)
// What is the sum of all the part numbers in the engine schematic?
fun main() {
    val input = readInput("day03/Day03")
    val d = Day03Refactor(input)

    d.part1().println()
    d.part2().println()

    // test if implementation meets criteria from the description, like:
//     val testInput = readInput("day1/Day01_test")
//     check(d.part1() == 142)
}

class Day03Refactor(private val input: List<String>) {

    fun part1(): Int {
        // Extract the elements from the input list
        val engineSchematic: List<SchematicRow> = input.mapIndexed { index, string -> extractElements(string, index) }
        return engineSchematic
            .findParts()
            .sumOf { it.value }
    }

    // Loop trough every character, if it is a digit append to currentNumber
    // if it is a Symbol or Number add the Element to the list using the buildList add
    private fun extractElements(input: String, row: Int): List<Element> = buildList {
        var numberStart = -1
        var currentNumber = ""
        for ((index, c) in input.withIndex()) {
            when {
                (c.isDigit()) -> {
                    currentNumber += c
                    if (numberStart == -1) numberStart = index
                }

                else -> {
                    if (c != '.') this.add(Symbol(value = c, column = index, row = row))
                    if (currentNumber.isNotEmpty()) {
                        this.add(Number(number = currentNumber, start = numberStart, end = index - 1, row = row))
                        currentNumber = ""
                        numberStart = -1
                    }
                }
            }
        }
        if (currentNumber.isNotEmpty()) {
            this.add(Number(number = currentNumber, start = numberStart, end = input.length - 1, row = row))
        }
    }

    private fun List<SchematicRow>.findParts(): Set<Number> {
        val parts = mutableSetOf<Number>()
        this.windowed(2).map { twoRows ->
            val symbols: List<Symbol> = twoRows.flatten().filterIsInstance<Symbol>()
            val numbers: List<Number> = twoRows.flatten().filterIsInstance<Number>()
            numbers.filter { n ->
                symbols.any { s ->
                    s.column in n.expandedColumn
                }
            }.forEach { parts.add(it) }
        }
        return parts
    }

    private fun List<Element>.findGearParts(): List<Pair<Int, Int>> {
        val parts = this.filterIsInstance<Number>()
        val potentialGears = this.filterIsInstance<Symbol>().filter { it.value == '*' }
        return potentialGears
            .map { s ->
                // list of neigbors
                parts.filter { (s.row in it.expandedRow && s.column in it.expandedColumn) }
            }
            .filter {
                it.size == 2
            }
            .map {
                it[0].value to it[1].value
            }
    }

    fun part2(): Int {
        // Extract the elements from the input list
        val engineSchematic: List<SchematicRow> = input.mapIndexed { index, string -> extractElements(string, index) }
        return engineSchematic
            .flatten()
            .findGearParts()
            .sumOf { parts -> parts.first * parts.second }
    }
}
