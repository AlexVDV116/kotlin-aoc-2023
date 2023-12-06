package day

import println
import readInput

fun main() {
    val input = readInput("day06/Day06")
    val d = Day06(input)

    d.part1().println()
    d.part2().println()

    // test if implementation meets criteria from the description, like:
//     val testInput = readInput("day1/Day01_test")
//     check(d.part1() == 142)
}

/* RACE RULES
 * Boat starting speed = 0ms p/s
 * Each whole  millisecond spend at beginning of race holding down the button, boat's speed increases by 1ms p/s
 * Time spent holding the button counts against the total race time
 * Boats don't move until the button is released
 * You can only hold the button at the start of the race
 *
 * Determine the number of ways you can beat the record in each race, multiply each number and return the result
 */

// duration = milliseconds
// record = millimeters
data class Race(val duration: Long, val record: Long)
class Day06(private val input: List<String>) {

    fun part1(): Int {
        val races =  input.chunked(2) { (timeLine, distanceLine) ->
            parseInput(timeLine).zip(parseInput(distanceLine)) { time, distance ->
                Race(time.toLong(), distance.toLong())
            }
        }.flatten()

        // Get a list of each individual race results and use reduce to accumulate the value by applying operation
        // on each element from left to right
        val raceResults = races.map { calculatePossibleWins(it) }.reduce { acc, i -> acc * i }  // returns 720
        return raceResults
    }

    // Calculate the possible wins for each possible buttonHoldDuration for a race
    private fun calculatePossibleWins(race: Race): Int {
        return (1..race.duration).count { playRace(race, it) > race.record }
    }

    // Returns the distance travelled by a boat for a specific race and buttonHoldDuration
    private fun playRace(race: Race, buttonHold: Long): Long {
        val speed = buttonHold // 3ms
        val remainingRaceDuration = race.duration - buttonHold // 7 - 3 = 4ms
        val distanceTravelled = remainingRaceDuration * speed // 4ms * 3ms = 12ms
        return distanceTravelled
    }

    private fun parseInput(line: String): List<String> =
        line.substringAfter(":").trim().split(" ", "\t").filter { it.isNotBlank() }


    fun part2(): Int {
        // Filter and format input lines
        val formattedLines = input.map { line ->
            line.filter { it.isDigit() || it.isWhitespace() }.replace(" ", "")
        }

        // Create Race objects from the formatted lines
        val races = formattedLines.chunked(2) { (timeLine, distanceLine) ->
            parseInput(timeLine).zip(parseInput(distanceLine)) { time, distance ->
                Race(time.toLong(), distance.toLong())
            }
        }.flatten()

        // Get the first race and calculate possible wins
        val race = races.first()
        return calculatePossibleWins(race)
    }
}
