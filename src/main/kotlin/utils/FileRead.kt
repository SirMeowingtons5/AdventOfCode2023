package utils

fun readFromFile(name: String): List<String> =
    object {}.javaClass.getResource(name).readText().split('\n')