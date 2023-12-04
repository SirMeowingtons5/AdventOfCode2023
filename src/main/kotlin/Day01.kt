import structure.Trie
import structure.TrieNode
import utils.readFromFile

object Day01 {

    //TODO: add parallel reverse lookahead from end of the string
    fun first() {
        val validNumbers = listOf(
            "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
        )
        val trie = Trie(' ')
        validNumbers.forEach { trie.addWord(it.asIterable()) }

        val textDigits = mapOf(
            "one" to "1",
            "two" to "2",
            "three" to "3",
            "four" to "4",
            "five" to "5",
            "six" to "6",
            "seven" to "7",
            "eight" to "8",
            "nine" to "9",
        )

        val res = readFromFile("/01/first.txt")
            .sumOf { input ->
                val foundDigits = StringBuilder()
                var i = 0
                while (i < input.length) {
                    lookaheadSearch(trie, input, i)?.let { endIndex ->
                        val foundNumber = input.substring(i, endIndex + 1)
                        val digit = if (foundNumber.length == 1) {
                            foundNumber
                        } else {
                            textDigits[foundNumber]
                        }
                        foundDigits.append(digit)
                    }
                    i++
                }

                foundDigits.first().digitToInt() * 10 + foundDigits.last().digitToInt()
            }

        println("Day 1: first = $res")
    }

    /**
     * @return null if [text] does not contain any word from [trie], else - index of last char of the found word
     */
    fun lookaheadSearch(trie: Trie<Char>, text: String, startingPosition: Int): Int? {
        if (startingPosition >= text.length) return null

        var index = startingPosition
        val nodes = ArrayList<TrieNode<Char>>()
        nodes.add(trie.rootNode)

        while (nodes.isNotEmpty() && index < text.length) {
            val temp = nodes
                .flatMap { it.children }
                .filter { it.value == text[index] }
            nodes.clear()
            nodes.addAll(temp)
            if (nodes.find { it.endOfWord } != null) return index
            index++
        }
        return null
    }
}