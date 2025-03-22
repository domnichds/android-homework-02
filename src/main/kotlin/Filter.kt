package org.example

inline fun <reified T> filter(list: List<Any>) : List<T> {
    return list.filterIsInstance<T>()
}

fun main() {
    val exList: List<Any> = listOf("Kotlin", 3.14, 128, 100, "Java", "Hello world", 2.01, 65)

    val stringList: List<String> = filter(exList)
    val intList: List<Int> = filter(exList)
    val doubleList: List<Double> = filter(exList)

    println(stringList)
    println(intList)
    println(doubleList)
}