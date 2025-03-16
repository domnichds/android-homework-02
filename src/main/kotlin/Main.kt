package org.example

// Интерфейс для объектов, которые можно взять домой
interface Borrowable {
    fun borrowItem()
}

// Интерфейс для объектов, которые можно использовать в библиотеке
interface ReadableInLibrary {
    fun readInLibrary()
}

// Интерфейс для объектов, которые можно вернуть
// В конкретной задаче не нужен, но полезен для масштабирования
interface Returnable {
    fun returnItem()
}

abstract class LibraryItem(var id: Int, var name: String, var accessible: Boolean) {
    // Метод для получения краткой информации об объекте
    fun getOneLineInfo(): String = "$name | Доступность: ${if (accessible) "да" else "нет"}"

    // Абстрактный метод получения подробной информации
    abstract fun getInfo(): String

    // Метод для изменения доступности с true на false при взятии объекта в зал или домой
    protected fun toggleAccessibility(successMessage: String, failureMessage: String) {
        if (accessible) {
            println(successMessage)
            accessible = false
        }
        else {
            println(failureMessage)
        }
    }

    // Метод для изменения доступности объекта с false на true при возврате в библиотеку
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

class LibraryManager(private val libraryItemList: MutableList<LibraryItem>) {

    // Метод для печати списка объектов по типу
    fun printList(type: Int) {
        val list = when (type) {
            1 -> libraryItemList.filterIsInstance<Book>()
            2 -> libraryItemList.filterIsInstance<Newspaper>()
            3 -> libraryItemList.filterIsInstance<Disk>()
            else -> emptyList()
        }
        list.forEachIndexed {index, item ->
            println("${index+1}. ${item.getOneLineInfo()}")
        }
    }

    // Метод для получения объекта по типу и индексу в этом типе
    fun getItem(type: Int, index: Int): LibraryItem? {
        val list = when (type) {
            1 -> libraryItemList.filterIsInstance<Book>()
            2 -> libraryItemList.filterIsInstance<Newspaper>()
            3 -> libraryItemList.filterIsInstance<Disk>()
            else -> emptyList()
        }
        // Возвращаем null, если выбор типа или номера в типа неверен
        return if (index in list.indices) list[index] else null
    }
}

fun main() {

    // Тестовый список объектов библиотеки
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

    // Создаем объект класса LibraryManager, передавая туда список объектов
    val library = LibraryManager(libraryItems)
    println("Добро пожаловать в Library Manager!\n" +
            "Навигация в меню происходит при помощи ввода цифр")
    while (true) {
        println("1 - Показать книги\n" +
                "2 - Показать газеты\n" +
                "3 - Показать диски\n" +
                "4 - Выйти")
        val mainChoice = readlnOrNull()?.toIntOrNull() // Выбор списка, который следует показать
        if (mainChoice == null || mainChoice !in 1..4) {
            println("Неверный выбор!")
        }
        else if (mainChoice == 4) {
            break // Завершаем цикл и соответственно всю программу
        }
        else {
            library.printList(mainChoice) // Выводим список объектов
            print("Введите номер объекта: ")
            val itemChoice = readlnOrNull()?.toIntOrNull()
            val item = library.getItem(mainChoice, itemChoice?.minus(1) ?: -1)
            if (item == null) {
                println("Неверный выбор!")
            }
            else {
                while (true) {
                    println("1 - Взять домой\n" +
                            "2 - Читать в читальном зале\n" +
                            "3 - Показать подробную информацию\n" +
                            "4 - Вернуть\n" +
                            "5 - Выйти")
                    val localChoice = readlnOrNull()?.toIntOrNull()
                    if (localChoice == null || localChoice !in 1..5) {
                        println("Неверный выбор!")
                    } else {
                        when (localChoice) {
                            1 -> if (item is Borrowable) item.borrowItem()
                            else println("Этот объект нельзя взять домой")
                            2 -> if (item is ReadableInLibrary) item.readInLibrary()
                            else println("Этот объект нельзя читать в зале")
                            3 -> println(item.getInfo())
                            4 -> if (item is Returnable) item.returnItem()
                            else println("Этот объект нельзя вернуть")
                            5 -> break
                        }

                    }
                }
            }
        }
    }
}