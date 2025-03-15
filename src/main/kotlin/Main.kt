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

interface Borrowable {
    fun takeHome() : String
}

interface ReadableInLibrary {
    fun readInLibrary() : String
}

interface Returnable {
    fun returnItem() : String
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
    val libraryItems = mutableListOf<LibraryItem>(
        Book(id = 1, name = "Преступление и наказание", accessible = true, numberOfPages = 500, author = "Федор Достоевский"),
        Newspaper(id = 2, name = "Известия", accessible = true, issueNumber = 1525),
        Disk(id = 3, name = "Брат", accessible = true, type = 1),
        Book(id = 4, name = "Война и мир", accessible = true, numberOfPages = 1200, author = "Лев Толстой"),
        Newspaper(id = 5, name = "Комсомольская правда", accessible = true, issueNumber = 1200),
        Disk(id = 6, name = "Начало", accessible = true, type = 0),
        Book(id = 7, name = "Мастер и Маргарита", accessible = true, numberOfPages = 400, author = "Михаил Булгаков"),
        Disk(id = 8, name = "Зеркало", accessible = true, type = 1)
    )
    val library = LibraryManager(libraryItems)
    println("Добро пожаловть в Library Manager!\n" +
            "Навигация в меню происходит при помощи ввода цифр")
    while (true) {
        println("1 - Показать книги\n" +
                "2 - Показать газеты\n" +
                "3 - Показать диски\n" +
                "4 - Выйти")
        val mainChoice = readlnOrNull()?.toIntOrNull()
        if (mainChoice == null || mainChoice !in 1..3) {
            println("Неверный выбор!")
        }
        else if (mainChoice == 4) {
            break
        }
        else {
            while(true) {
                library.printList(mainChoice)
                println("1 - Взять домой\n" +
                        "2 - Читать в читальном зале\n" +
                        "3 - Показать подробную информацию\n" +
                        "4 - Вернуть\n" +
                        "5 - Выйти")
                }

            }
        }
}


