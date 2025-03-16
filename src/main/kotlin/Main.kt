package org.example

abstract class LibraryItem(var id: Int, var name: String, var accessible: Boolean) {
    fun getOneLineInfo(): String = "$name | Доступность: ${if (accessible) "да" else "нет"}"
    abstract fun getInfo(): String

    protected fun toggleAccessibility(successMessage: String, failureMessage: String) {
        if (accessible) {
            println(successMessage)
            accessible = false
        }
        else {
            println(failureMessage)
        }
    }

    protected fun marksAsAccessible(successMessage: String, failureMessage: String) {
        if (!accessible) {
            println(successMessage)
            accessible = true
        }
        else {
            println(failureMessage)
        }
    }
}

class Book(id: Int, name: String, accessible: Boolean, private var numberOfPages: Int, private var author: String) :
    LibraryItem(id, name, accessible), Borrowable, ReadableInLibrary, Returnable {
    override fun getInfo(): String =
        "Книга: $name ($numberOfPages стр.) автора: $author с id: $id | Доступность: ${if (accessible) "да" else "нет"}"

    override fun borrowItem() {
        toggleAccessibility("Книга $name взята домой",
            "Книга $name уже на руках")
    }

    override fun readInLibrary() {
        toggleAccessibility("Книга $name взята в читальный зал",
            "Книга $name уже на руках")
    }

    override fun returnItem() {
        marksAsAccessible("Книга $name возвращена в библиотеку",
            "Книга $name уже в библиотеке")
    }

}

class Newspaper(id: Int, name: String, accessible: Boolean, private var issueNumber: Int) :
    LibraryItem(id, name, accessible), ReadableInLibrary, Returnable {
    override fun getInfo(): String =
        "Выпуск: $issueNumber газеты $name с id: $id | Доступность: ${if (accessible) "да" else "нет"}"

    override fun readInLibrary() {
        toggleAccessibility("Газета $name №$issueNumber взята в читальный зал",
            "Газета $name №$issueNumber уже на руках")
    }

    override fun returnItem() {
        marksAsAccessible("Газета $name №$issueNumber возращена в библиотеку",
            "Газета $name №$issueNumber уже в библиотеке")
    }
}

class Disk(id: Int, name: String, accessible: Boolean, private var type: Int) :
    LibraryItem(id, name, accessible), Borrowable, Returnable {
    override fun getInfo(): String =
        "${if (type == 0) "CD" else "DVD"} $name | Доступность: ${if (accessible) "да" else "нет"}"

    override fun borrowItem() {
        toggleAccessibility("${if (type == 0) "CD" else "DVD"} диск $name взят домой",
            "${if (type == 0) "CD" else "DVD"} диск $name уже на руках")
    }

    override fun returnItem() {
        marksAsAccessible("${if (type == 0) "CD" else "DVD"} диск $name возращен в библиотеку",
            "${if (type == 0) "CD" else "DVD"} диск $name уже в библиотеке")
    }
}

interface Borrowable {
    fun borrowItem()
}

interface ReadableInLibrary {
    fun readInLibrary()
}

interface Returnable {
    fun returnItem()
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
                        TODO("Остальная логика + сделать краше!!!")
                }

            }
        }
}


