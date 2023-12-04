package day03

import readInput

typealias EngineScheme = Array<Array<Cell>>

data class Cell(
    val x: Int,
    val y: Int,
    val value: Char,
    val isSymbol: Boolean,
    val isNumber: Boolean
)

// The engine schematic (your puzzle input, Day03.txt) consists of a visual representation of the engine.
// Any number adjacent to a symbol, even diagonally, is a "part number" and should
// be included in your sum. (Periods (.) do not count as a symbol.)
// What is the sum of all the part numbers in the engine schematic?
fun main() {
    val input = readInput("day03/Day03_test")
    val d = Day03(input)

    d.part1()
    //d.part2()

    // test if implementation meets criteria from the description, like:
//     val testInput = readInput("day1/Day01_test")
//     check(d.part1() == 142)
}

class Day03(private val input: List<String>) {
    private val symbols = listOf('*', '#', '$', '+')

    /* Create a 2D array that holds the engine schematic
     * For each number check if it is adjacent to a symbol (co-ordinates around it)
     * 8 directions --> (N-W-S-E) + Diagonal (NE, SE, SW , NW)
     */

    fun part1(): Int {
        val engineScheme = createEngineScheme(input)
        val cellList = getCellList(engineScheme)
        val symbols = cellList.filter { it.isSymbol }
        val numbers = cellList.filter { it.isNumber }
        println(symbols)
        println(numbers)
        println(findAdjacentNumbers(engineScheme, numbers))
        return 0
    }

    private fun createEngineScheme(input: List<String>): EngineScheme {
        return Array(input.size) { rowIndex ->
            Array(input[rowIndex].length) { colIndex ->
                val isSymbol = input[rowIndex][colIndex] in symbols
                val isNumber = input[rowIndex][colIndex].isDigit()
                Cell(rowIndex, colIndex, input[rowIndex][colIndex], isSymbol, isNumber)
            }
        }
    }

    private fun getCellList(engineScheme: EngineScheme): List<Cell> {
        val cellList = mutableListOf<Cell>()

        engineScheme.forEach { row ->
            row.forEach { cell ->
                if (cell.isSymbol || cell.isNumber) {
                    cellList
                        .add(cell)
                }
            }
        }
        return cellList
    }


    // Finds the adjacent numbers for each symbol in the engine scheme
    // returns a map of the symbol with corresponding coordinates
    private fun findAdjacentNumbers(engineScheme: EngineScheme, numbers: List<Cell>): List<Int> {
        val parts = listOf<Int>()
        return parts
    }


    fun part2(): Int {
        return 0
    }
}