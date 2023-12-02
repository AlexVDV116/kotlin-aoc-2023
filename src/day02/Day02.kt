package day02

import println
import readInput

fun main() {
    val input = readInput("day02/Day02")
    val d = Day02(input)

    d.part1().println()
    d.part2().println()

    // test if implementation meets criteria from the description, like:
//     val testInput = readInput("day1/Day01_test")
//     check(d.part1() == 142)
}

data class Game(
    val id: Int,
    val highestRedCubeCount: Int,
    val highestBlueCubeCount: Int,
    val highestGreenCubeCount: Int,
    val lowestRedCubeCount: Int,
    val lowestBlueCubeCount: Int,
    val lowestGreenCubeCount: Int,
    val possible: Boolean,
    val power: Int
)

// Determine which games would have been possible if the bag
// had been loaded with only 12 red cubes, 13 green cubes and
// 14 blue cubes. What is the sum of the IDs of those games?
class Day02(private val input: List<String>) {
    private val maxRedCubes = 12
    private val maxGreenCubes = 13
    private val maxBlueCubes = 14

    // Function that transforms each line from the input into a Game data class ands add them to a list
    // Returns the sum of the Game IDs that were possibly to play
    fun part1(): Int {
        return input.map { convertToGame(it) }
            .filter { it.possible }
            .sumOf { it.id }
    }

    //
    fun part2(): Int {
        return input.map { convertToGame(it) }
            .sumOf { it.power }
    }

    private fun getGameIndex(line: String): Int {
        return line.split(":").first().filter { it.isDigit() }.toInt()
    }

    private fun getHighestColorCount(color: String, line: String): Int {
        var highestColorCount = 0
        line.split(":", ";", ",")
            .forEach { element ->
                if (element.contains(color)) {
                    element.filter { it.isDigit() }.also {
                        val count = it.toInt()
                        if (count > highestColorCount) highestColorCount = count
                    }
                }
            }
        return highestColorCount
    }

    private fun getHighestColorCountInASet(color: String, line: String): Int {
        var highestColorCountInASet = 0
        // Remove Game ID and split string into game sets
        line.removeRange(0, 7)
            .split(";")
            .forEach { set ->
                set.split(",")
                    .forEach { element ->
                        if (element.contains(color)) {
                            element.filter { it.isDigit() }.also {
                                val count = it.toInt()
                                if (count > highestColorCountInASet) highestColorCountInASet = count
                            }
                        }
                    }
            }
        return highestColorCountInASet
    }

    // Convert each line to a game object
    private fun convertToGame(line: String): Game {
        val gameId = getGameIndex(line)
        val highestRedCubeCount = getHighestColorCount("red", line)
        val highestBlueCubeCount = getHighestColorCount("blue", line)
        val highestGreenCubeCount = getHighestColorCount("green", line)
        val highestRedCubeCountInASet = getHighestColorCountInASet("red", line)
        val highestBlueCubeCountInASet = getHighestColorCountInASet("blue", line)
        val highestGreenCubeCountInASet = getHighestColorCountInASet("green", line)
        val possible = when {
            highestRedCubeCount > maxRedCubes -> false
            highestBlueCubeCount > maxBlueCubes -> false
            highestGreenCubeCount > maxGreenCubes -> false
            else -> true
        }
        val power = highestRedCubeCountInASet * highestBlueCubeCountInASet * highestGreenCubeCountInASet

        return Game(
            gameId,
            highestRedCubeCount,
            highestBlueCubeCount,
            highestGreenCubeCount,
            highestRedCubeCountInASet,
            highestBlueCubeCountInASet,
            highestGreenCubeCountInASet,
            possible,
            power
        )
    }
}
