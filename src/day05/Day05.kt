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

data class Block(val src: Long, val dst: Long, val length: Long)
data class SeedsMap(val from: String, val to: String, val blocks: List<Block>)

class Day05(private val input: List<String>) {

    fun part1(): Int {

        // Parse the seed from the input into a list of Long
        val seeds = input[0]
            .substringAfter(":")
            .split(" ")
            .filter { it.isNotBlank() }
            .map { it.toLong() }

        // Parse the maps from the input into a list of SeedsMap
        val maps = input.drop(2)
            //
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
        println(seeds)
        println(maps)

        // seed-to-soil -> SeedsMap
        // 50 98 2 (src, dst, length) -> Block
        // 51 99


        // 52 50 48 (src, dst, length) -> Block
        // 53 51
        // ...
        // 99 98


        // For every from value in a SeedsMap, find the corresponding to value using the blocks
        fun convert(maps: List<SeedsMap>, seeds: List<Long>): List<Long> {

        }
        return 1
    }

    fun part2(): Int {
        return 0
    }

}
