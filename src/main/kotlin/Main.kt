package org.example

abstract class LibraryItem(var id: Int, var name: String, var accessible: Boolean) {
    fun getOneLineInfo(): String = "$name | Доступность: ${if (accessible) "да" else "нет"}"
    abstract fun getInfo(): String
}

class Book(id: Int, name: String, accessible: Boolean, private var numberOfPages: Int, private var author: String) :
    LibraryItem(id, name, accessible) {
    override fun getInfo(): String =
        "Книга: $name ($numberOfPages стр.) автора: $author с id: $id | Доступность: ${if (accessible) "да" else "нет"}"
}

class Newspaper(id: Int, name: String, accessible: Boolean, private var issueNumber: Int) :
    LibraryItem(id, name, accessible) {
    override fun getInfo(): String =
        "Выпуск: $issueNumber газеты $name с id: $id | Доступность: ${if (accessible) "да" else "нет"}"
}

class Disk(id: Int, name: String, accessible: Boolean, private var type: Int) : LibraryItem(id, name, accessible) {
    override fun getInfo(): String =
        "${if (type == 0) "CD" else "DVD"} $name | Доступность: ${if (accessible) "да" else "нет"}"
}

class LibraryManager(val libraryItemList: MutableList<LibraryItem>) {
    fun printList(type: Int) {
        when (type) {
            1 -> libraryItemList.filterIsInstance<Book>().forEach({ println(it.getInfo()) })
            2 -> libraryItemList.filterIsInstance<Newspaper>().forEach({ println(it.getInfo()) })
            3 -> libraryItemList.filterIsInstance<Disk>().forEach({ println(it.getInfo()) })
        }
    }

}

fun main() {

}

