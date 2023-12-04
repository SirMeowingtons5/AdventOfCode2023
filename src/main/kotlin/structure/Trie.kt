package structure

import java.lang.IllegalStateException

class Trie<T>(rootStubValue: T) {

    val rootNode = TrieNode(rootStubValue, false)

    fun addWord(word: Iterable<T>) {
        var currentNode: TrieNode<T> = rootNode
        word.forEach { element ->
            currentNode = if (currentNode.contains(element)) {
                currentNode.get(element)
            } else {
                val newNode = TrieNode(value = element, false)
                currentNode.add(newNode)
                newNode
            }
        }
        currentNode.endOfWord = true
    }
}

class TrieNode<T> internal constructor(val value: T, var endOfWord: Boolean) {

    private val _children = HashMap<T, TrieNode<T>>()
    val children: List<TrieNode<T>> get() = _children.values.toList()

    fun add(node: TrieNode<T>) {
        if (_children.containsKey(node.value)) throw IllegalStateException("Node with value $value already has child ${node.value}")
        _children[node.value] = node
    }

    fun contains(element: T): Boolean =
        _children.containsKey(element)

    fun get(element: T): TrieNode<T> =
        _children[element]!!

    fun getOrNull(element: T): TrieNode<T>? =
        _children[element]
}