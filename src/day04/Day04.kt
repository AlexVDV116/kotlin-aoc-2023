package day04

import println
import readInput
import kotlin.math.pow

fun main() {
    val input = readInput("day04/Day04")
    val d = Day04(input)

    //d.part1().println()
    d.part2().println()

    // test if implementation meets criteria from the description, like:
//     val testInput = readInput("day1/Day01_test")
//     check(d.part1() == 142)
}

data class ScratchCard(val id: Int, val winningNumbers: Set<Int>, val myNumbers: Set<Int>)

class Day04(private val input: List<String>) {
    private val cards = lineToScratchCard(input)

    // Winning Numbers | Your numbers
    // Each match = 1 point, each subsequent match doubles the point value
    // How many points are all the cards worth
    fun part1(): Int {
        return calculatePoints(cards)
    }

    // Parse input into list of Scratchcard date type
    private fun lineToScratchCard(input: List<String>): List<ScratchCard> {
        val scratchCardsList = mutableListOf<ScratchCard>()

        input.forEach { line ->
            val id = line.split(":").first().filter { it.isDigit() }.toInt()
            // Use destructuring declaration to extract the numbers into separate lists
            val (winningNumbers, myNumbers) = line.removeRange(0, 7)
                .split("|")
                .map { it.trim().split(" ").mapNotNull { number -> number.toIntOrNull() }.toSet() }
            scratchCardsList.add(ScratchCard(id, winningNumbers, myNumbers))
        }
        return scratchCardsList
    }

    // Gets the intersecting numbers on each card and calculates the points based of an exponential formula
    private fun calculatePoints(cardList: List<ScratchCard>): Int {
        var cardPoints = 0
        cardList.forEach { card ->
            val matchedNumbers = card.winningNumbers.intersect(card.myNumbers)
            cardPoints += if (matchedNumbers.isEmpty()) 0 else 2.0.pow(matchedNumbers.size - 1).toInt()
        }
        // Return sum of all cards
        return cardPoints
    }

    fun part2(): Int {
       return cards.map { card ->
            val count = card.winningNumbers.count { it in card.myNumbers}
            card to count
        }.let { pairs ->
            val countByCard = MutableList(pairs.size) { 1 }
           pairs.mapIndexed { index, (_, count) ->
               (1..count).forEach {
                   countByCard[index + it] += countByCard[index]
               }
           }
           countByCard
       }.sum()
    }
}
