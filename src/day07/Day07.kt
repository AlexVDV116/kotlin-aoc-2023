package day07

import println
import readInput

fun main() {
    val input = readInput("day07/Day07")
    val d = Day07(input)

    //d.part1().println()
    d.part2().println()

//    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("day07/Day07_test")
//    val dt = Day07(testInput)
//    check(dt.part2() == 5905)
}

data class Card(val value: Char)

data class Hand(val cards: List<Card>, val bid: Int, var strength: Int = 0)

class Day07(private val input: List<String>) {

    fun part1(): Int {
        val hands = parseInput(input)

        // Calculate the strength of each hand
        hands.forEach { hand ->
            hand.strength = calculateStrength(hand)
        }

        // Calculate the rank of each hand and organise the list of hands by order of rank
        val rankedHands = calculateRank(hands, "AKQJT98765432")
        return calculateWinnings(rankedHands)
    }

    private fun parseInput(input: List<String>): List<Hand> {
        // Parse input into Hand data class object
        return input.map { line ->
            // Get the cardValues from the input and create a Card object for each
            val cardValues = line.substring(0, line.lastIndexOf(" ")).toCharArray()
            // Map the cardValues to a list of Card objects
            val cards = cardValues.map { Card(it) }
            // Get the bid from the input
            val bid = line.split(" ").last().toInt()
            // Create a Hand object from the cards and the bid and return it to the hands list
            Hand(cards, bid)
        }
    }

    private fun calculateStrength(hand: Hand): Int {
        // Group the cards by value and count the number of cards with the same value exluding '1' (Joker)
        val counts = hand.cards.groupBy { it.value }.mapValues { it.value.size }.filterKeys { it != '1' }.values
        println(counts)
        return when {
            5 in counts -> 7 // Five of a Kind
            4 in counts -> 6 // Four of a Kind
            3 in counts && 2 in counts -> 5 // Full House
            3 in counts -> 4 // Three of a Kind
            2 in counts && (counts - 2).contains(2) -> 3 // Two Pair
            2 in counts -> 2 // Pair
            else -> 1 // High Card
        }
    }

    /* For each hand, calculate the strength and sort the list based on the strength
     * If two hands have the same type, they are sorted by comparing corresponding cards from the first card to the
     * fifth card, prioritizing the hand with stronger cards at the earliest differing position. If all corresponding
     * cards are identical, the hands are considered equal.
     */
    private fun calculateRank(hands: List<Hand>, cardOrder: String): List<Hand> {
        val hexadecimal = "0123456789ABC"
        val comparator = cardOrder.zip(hexadecimal).toMap()
        val sortedHands = hands.sortedWith(compareBy<Hand> { it.strength }
            .thenByDescending { hand ->
                hand.cards.joinToString("") { card ->
                    comparator.getValue(card.value).toString()
                }
            }
        )
        return sortedHands
    }

    // Determine the total winnings of this set of hands by adding up the result of multiplying each hand's bid
    // with its dynamically determined rank
    private fun calculateWinnings(hands: List<Hand>): Int {
        return hands.mapIndexed { index, hand ->
            println(hand)
            hand.bid * (index + 1)
        }.sum()
    }

    // Now, J cards are jokers - wildcards that act like whatever card would make the hand the strongest type possible
    // To balance this, J cards are now the weakest individual cards, weaker even than 2
    fun part2(): Int {
        val hands = parseInput(input)

        val strengthWithJokers = getStrengthWithJokers(hands)

        // Calculate the rank of each hand and organise the list of hands by order of rank
        val rankedHands = calculateRank(strengthWithJokers, "AKQT98765432J")

        return calculateWinnings(rankedHands)
    }

    private fun getStrengthWithJokers(hands: List<Hand>): List<Hand> {
        // Replace J from each hand with '1' and calculate the strength of the hand
        val handsWithoutJ = hands.map { hand ->
            val cardsWithoutJ = hand.cards.map { card ->
                if (card.value == 'J') {
                    Card('1')
                } else {
                    card
                }
            }
            Hand(cardsWithoutJ, hand.bid)
        }
        println("handsWithoutJ: $handsWithoutJ")

        // Calculate the base strength of each hand without J
        val strengthWithoutJ = handsWithoutJ.map { hand ->
            hand.strength = calculateStrength(hand)
            hand
        }
        println("strengthWithoutJ: $strengthWithoutJ")

        // For each hand, get the size of each hand, subtract the size from 5
        strengthWithoutJ.map { hand ->
            // Get the amound of 'J' in hand by counting the number of cards with value '1'
            val upgrades = hand.cards.count { it.value == '1' }
            println(hand.cards)
        println("upgrades: $upgrades")
            // For each possible upgrade give the hand a new strength
            // When strength of a hand is 1, it is a High Card, so the strength of the hand with J is a pair (strength 2)
            // When strength of a hand is 2, it is a pair, so the strength of the hand with J is three of a kind (strength 4)
            // When strength of a hand is 3, it is a Two Pair, so the strength of the hand with J is a full house (strength 5)
            // When strength of a hand is 4, it is a Three of a Kind, so the strength of the hand with J is four of a kind (strength 6)
            // When strength of a hand is 5, it is a Full House, so the strength of the hand with J is a four of a kind (strength 6)
            // When strength of a hand is 6, it is a Four of a Kind, so the strength of the hand with J is a five of a kind (strength 7)
            // When strength of a hand is 7, it is a Five of a Kind, so the strength of the hand with J is a five of a kind (strength 7)
            repeat(upgrades) {
                when (hand.strength) {
                    1 -> hand.strength = 2
                    2 -> hand.strength = 4
                    3 -> hand.strength = 5
                    4 -> hand.strength = 6
                    5 -> hand.strength = 6
                    6 -> hand.strength = 7
                    7 -> hand.strength = 7
                }
            }
        }

        // Replace the 1 with a J again
        val newStrengthWithJ = strengthWithoutJ.map { hand ->
            val cardsWithJ = hand.cards.map { card ->
                if (card.value == '1') {
                    Card('J')
                } else {
                    card
                }
            }
            Hand(cardsWithJ, hand.bid, hand.strength)
        }
        return newStrengthWithJ
    }

}