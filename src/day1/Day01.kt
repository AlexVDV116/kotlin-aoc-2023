package day1

import convertStringToNumber
import println
import readInput

fun main() {

    // Search for first and last occurrence of a digit in a given string
    // Add these two together forming an integer, sum up the total of all integers
    fun part1(input: List<String>): Int {
        val myList = mutableListOf<Int>()

        for (line in input) {
            // Get the index of the first and last integer
            val firstIntegerIndex = line.indexOfFirst { it.isDigit() }
            val lastIntegerIndex = line.indexOfLast { it.isDigit() }

            // Convert the char to a string representation
            val firstInteger = line[firstIntegerIndex].toString()
            val lastInteger = line[lastIntegerIndex].toString()

            // Add both first and last string representations together
            val sum = firstInteger + lastInteger

            // Convert the result to an integer and add them to the list
            myList.add(sum.toInt())
        }
        // Return the sum of the list
        println(myList)
        println(myList.sum())
        return myList.sum()
    }

    // Search for the first and last occurrences in a string from list of substrings
    fun part2(input: List<String>): Int {
        val digitsSubstring = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten")
        val modifiedInput = mutableListOf<String>()

        for (line in input) {
            println("original line = $line")
            val firstSubstringOccurrence  = line.findAnyOf(digitsSubstring, 0, true)
            val lastSubstringOccurrence  = line.findLastAnyOf(digitsSubstring, line.lastIndex, true)

            // Get the indexes of both occurrences
            if (firstSubstringOccurrence != null && lastSubstringOccurrence != null) {
                val firstIndexToInsert = firstSubstringOccurrence.first
                val lastIndexToInsert = lastSubstringOccurrence.first

                // Get the string representation of the digit
                val firstCharToInsert = firstSubstringOccurrence.second
                val lastCharToInsert = lastSubstringOccurrence.second

                // Transform the string representation to an integer
                val firstIntToInsert = convertStringToNumber(firstCharToInsert)
                val lastIntToInsert = convertStringToNumber(lastCharToInsert)

                // Insert the integer at the index where the substring is found plus one
                // Causing a new string with an integer representation of the substring as the first and last occurrence
                val stringBuilder = StringBuilder(line)
                stringBuilder.insert(firstIndexToInsert + 1, firstIntToInsert)
                stringBuilder.insert(lastIndexToInsert + 2, lastIntToInsert)

                // Create a new modified line using the stringBuilder
                val modifiedLine = stringBuilder.toString()

                // Append the new modified line to a new modified input list
                println("modifiedline = $modifiedLine")
                modifiedInput.add(modifiedLine)
            } else {
                modifiedInput.add(line)
            }
        }
        // Use part1 to add the first and last occurrence of the integers together and sum up the total of the list
        return part1(modifiedInput)
    }

    // test if implementation meets criteria from the description, like:
   val testInput = readInput("Day01_test")
    check(part2(testInput) == 1031)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
