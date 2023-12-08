package day08

import println
import readInput

// Challenge
// navigate a network of labeled nodes using left/right instructions, starting from AAA and aiming to reach ZZZ
// counting the number of steps required while considering the repetition of instructions.

// Binary Tree
// Each Tree has a Root Node (at the top, since the tree is upside down)
// A node without children is called a leaf
// The path from the Root node to a leaf is called a branch
// Each TreeNode has a label
// Each TreeNode has a left and right child
// Each TreeNode has a parent
// Each TreeNode has 0-2 children, identified as left and right with the .left and .right properties
// Each TreeNode has a value
// Each TreeNode has a depth

fun main() {
    val input = readInput("day08/Day08")
    val d = Day08(input)

    //d.part1().println()
    d.part2().println()

}

// Read Input
// Parse Input
// Implement Navigation Logic
// Main Loop trough nodes until you reach ZZZ
// Count the number of steps required
// Print or Return Result

class Day08(private val input: List<String>) {

    fun part1(): Int {
        // Parse the input into instructions and a map of nodes
        val (instructions, map) = parseInput()

        // Initialise the counter for instructions, step counter, current node and target node
        var i = 0
        var count = 0
        var current = "AAA"
        val target = "ZZZ"

        // Loop through the input until the target is reached incrementing the count each step
        while (true) {
            count++

            // Get the left and right destinations for the current node
            val (left, right) = map[current]!!

            // Determine the next node based on the current instruction
            current = if (instructions[i] == 'L') left else right

            // If the current node is the target node, break the loop
            if (current == target) break

            // increment the instruction counter, if it reaches the end of the instructions, reset it to 0
            i++
            if (i == instructions.length) i = 0
        }
        return count
    }

    /* Parse input into a Pair of instructions and a Map of nodes with a Pair of left and right destinations
     * AAA = (BBB, CCC)
     */
    private fun parseInput(): Pair<String, Map<String, Pair<String, String>>> {
        val instructions = input.first()
        // Lists are evaluated eagerly, Sequences are evaluated lazily
        // Transform the input list to a sequence as it's more suitable and performant for larger collections
        val map =  input.asSequence().drop(2).associate {
            // Destructuring declarations to map from to left and right destinations
            val (from, destinations) = it.split(" = ")
            val (left, right) = destinations.substring(1, destinations.length - 1).split(", ")

            from to (left to right)
        }
        // Creates a tuple of type pair with the instructions and the map of nodes
        return instructions to map
    }


    // Use least common multiple to find the first number that is divisible by all the steps to the final node
    fun part2(): String {
        val (instructions, map) = parseInput()

        val startNodes = map.keys.filter { it.endsWith("A") }.toMutableList()
        println(startNodes)

        // For each startnode, navigate to the final node and count the number of steps required
        // returning a list of Longs
        val stepsToFinalNode = startNodes.map {
            var current = it
            var i = 0
            var counter = 0L
            while (true) {
                counter++
                val (left, right) = map[current]!!
                val instruction = instructions[i]
                current = if (instruction == 'L') left else right
                if (current.endsWith("Z")) break

                i++
                if (i >= instructions.length) i = 0
            }
            counter
        }
        println(stepsToFinalNode)

        // Find the minimum number of steps required to reach the final node
        val min = stepsToFinalNode.min()
        var t = min

        // Find the first number that is divisible by all the steps to the final node
        // using factorisation and the least common multiple
        while (true) {
            if (stepsToFinalNode.all { t % it == 0L }) return t.toString()
            t += min
        }
    }

}
