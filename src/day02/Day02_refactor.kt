package day02

import println
import readInput

fun main() {
    val input = readInput("day02/Day02")
    val d = Day02Refactor(input)

    d.part1().println()
    d.part2().println()

    // test if implementation meets criteria from the description, like:
//     val testInput = readInput("day1/Day01_test")
//     check(d.part1() == 142)
}

// Determine which games would have been possible if the bag
// had been loaded with only 12 red cubes, 13 green cubes and
// 14 blue cubes. What is the sum of the IDs of those games?
class Day02Refactor(private val input: List<String>) {
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
        //return line.split(":").first().filter { it.isDigit() }.toInt()
        return line.removePrefix("Game ").takeWhile { it.isDigit() }.toInt()
    }

    private fun String.extractColorCounts(color: String): List<Int> =
        split(":", ";", ",")
            .filter { it.contains(color) }
            .map { it.filter { it.isDigit() }.toInt() }

    private fun String.extractColorCountsInASet(color: String): List<Int> =
        substring(7)
            .split(";")
            .flatMap { set ->
                set.split(",")
                    .filter { element -> element.contains(color) }
                    .map { element -> element.filter { it.isDigit() }.toInt() }
            }

    private fun getHighestColorCountInASet(color: String, line: String): Int =
        line.extractColorCountsInASet(color).maxOrNull() ?: 0

    private fun getHighestColorCount(color: String, line: String): Int =
        line.extractColorCounts(color).maxOrNull() ?: 0

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
