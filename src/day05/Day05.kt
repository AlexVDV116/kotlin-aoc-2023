package day05

import println
import readInput

fun main() {
    val input = readInput("day05/Day05_test")
    val d = Day05(input)

    d.part1().println()
    //d.part2()

    // test if implementation meets criteria from the description, like:
//     val testInput = readInput("day1/Day01_test")
//     check(d.part1() == 142)
}

class Day05(private val input: List<String>) {

    fun part1(): List<Long> {
        val seed = input[0]
            .substringAfter(":")
            .split(" ")
            .filter { it.isNotBlank() }
            .map { it.toLong() }

        data class Block(val src: Long, val dst: Long, val length: Long)
        data class SeedsMap(val from: String, val to: String, val blocks: List<Block>)

        // Drop the first 2 elements
        // Fold the input, with an initial value of mutableListOf mutableListOfString
        // If the element is blank create add a new mutableListOf
        // else add the element to the last list
        // then use map to parse each element to the data class
        val maps = input.drop(2)
            .fold(mutableListOf(mutableListOf<String>())) { acc, string ->
                if (string.isBlank())
                    acc.add(mutableListOf())
                else
                    acc.last().add(string)
                acc
            }.map {
                // use destructuring declarations to map the "seed-to-soil" block emitting the "to"
                val(from, _, to) = it.first().split("-", " ")
                val blocks = it.drop(1).map {
                    val (dst, src, length) = it.split(" ").map { it.toLong() }
                    Block(src, dst, length)
                }
                SeedsMap(from, to, blocks)
            }
        return seed
    }

    fun part2(): Int {
        return 0
    }

}
