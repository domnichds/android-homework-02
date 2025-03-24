package org.example

// Перечисление для месяцев
enum class Month(val russianName: String) {
    JANUARY("Январь"),
    FEBRUARY("Февраль"),
    MARCH("Март"),
    APRIL("Апрель"),
    MAY("Май"),
    JUNE("Июнь"),
    JULY("Июль"),
    AUGUST("Август"),
    SEPTEMBER("Сентябрь"),
    OCTOBER("Октябрь"),
    NOVEMBER("Ноябрь"),
    DECEMBER("Декабрь");

    override fun toString(): String {
        return russianName
    }
}

class InterfaceManager(private val library: LibraryManager) {
    init {
        println(
            "Добро пожаловать в Library Manager!\n" +
                    "Навигация в меню происходит при помощи ввода цифр"
        )
    }

    private fun showMainMenu() {
        println(
            "1 - Показать книги\n" +
                    "2 - Показать газеты\n" +
                    "3 - Показать диски\n" +
                    "4 - Менеджер по закупкам\n" +
                    "5 - Кабинет оцифровки\n" +
                    "6 - Выйти"
        )
    }

    private fun showItemMenu() {
        println(
            "1 - Взять домой\n" +
                    "2 - Читать в читальном зале\n" +
                    "3 - Показать подробную информацию\n" +
                    "4 - Вернуть\n" +
                    "5 - Выйти"
        )
    }

    private fun showManagerMenu() {
        println(
            "1 - Купить книгу\n" +
                    "2 - Купить диск\n" +
                    "3 - Купить газету\n" +
                    "4 - Выйти"
        )
    }

    private fun showDigitizerMenu() {
        println(
            "1 - Оцифровать книгу\n" +
                    "2 - Оцифровать газету\n" +
                    "3 - Выйти"
        )
    }

    fun mainMenu() {
        while (true) {
            showMainMenu()
            when (val mainChoice = readlnOrNull()?.toIntOrNull()) { // Выбор списка, который следует показать
                null, !in 1..6 -> println("Неверный выбор!")
                in 1..3 -> itemMenu(mainChoice)
                4 -> managerMenu()
                5 -> digitizerMenu()
                6 -> break // Завершаем цикл и соответственно всю программу
            }
        }
    }

    private fun itemMenu(type: Int) {
        library.printList(type)
        print("Введите номер объекта: ")
        val itemChoice = readlnOrNull()?.toIntOrNull()
        val item = library.getItem(type, itemChoice?.minus(1) ?: -1)
        if (item == null) {
            println("Неверный выбор!")
        } else {
            while (true) {
                showItemMenu()
                val localChoice = readlnOrNull()?.toIntOrNull()
                if (localChoice == null || localChoice !in 1..5) {
                    println("Неверный выбор!")
                } else {
                    if (handleItemChoice(localChoice, item)) break
                }
            }
        }
    }

    // Метод работает с Item и возвращает true, если нужно выйти из цикла
    private fun handleItemChoice(choice: Int, item: LibraryItem): Boolean {
        return when (choice) {
            1 -> {
                if (item is Borrowable) item.borrowItem()
                else println("Этот объект нельзя взять домой")
                false
            }
            2 -> {
                if (item is ReadableInLibrary) item.readInLibrary()
                else println("Этот объект нельзя читать в зале")
                false
            }
            3 -> {
                println(item.getInfo())
                false
            }
            4 -> {
                if (item is Returnable) item.returnItem()
                else println("Этот объект нельзя вернуть")
                false
            }
            5 -> true
            else -> false
        }
    }

    private fun managerMenu() {
        val manager = Manager()
        while (true) {
            showManagerMenu()
            val choice = readlnOrNull()?.toIntOrNull()
            when (choice) {
                1 -> {
                    val bookStore = BookStore()
                    val boughtBook = manager.buy(bookStore)
                    println("Книга успешно куплена! Подробности:\n${boughtBook.getInfo()}")
                }
                2 -> {
                    val diskStore = DiskStore()
                    val boughtDisk = manager.buy(diskStore)
                    println("Диск успешно куплен! Подробности:\n${boughtDisk.getInfo()}")
                }
                3 -> {
                    val newspaperStore = NewspaperStore()
                    val boughtNewspaper = manager.buy(newspaperStore)
                    println("Газета успешно куплена! Подробности:\n${boughtNewspaper.getInfo()}")
                }
                4 -> return
                else -> {
                    println("Неверный выбор!")
                    return
                }
            }
        }
    }

    private fun digitizerMenu() {
        while (true) {
            showDigitizerMenu()
            val choice = readlnOrNull()?.toIntOrNull()
            when (choice) {
                1 -> {
                    library.printList(1)
                    val itemChoice = readlnOrNull()?.toIntOrNull()
                    val item = library.getItem(1, itemChoice?.minus(1) ?: -1)
                    if (item == null) {
                        println("Неверный выбор")
                    } else {
                        val digitizer = Digitizer<Book>()
                        val digitalBook = digitizer.digitalize(item)
                        println("Успешно оцифрована книга ${item.name} на CD")
                    }
                }
                2 -> {
                    library.printList(2)
                    val itemChoice = readlnOrNull()?.toIntOrNull()
                    val item = library.getItem(2, itemChoice?.minus(1) ?: -1)
                    if (item == null) {
                        println("Неверный выбор")
                    } else {
                        val digitizer = Digitizer<Newspaper>()
                        val digitalBook = digitizer.digitalize(item)
                        println("Успешно оцифрована газета ${item.name} на CD")
                    }
                }
                3 -> return
            }
        }
    }
}

fun main() {
    // Тестовый список объектов библиотеки
    val libraryItems = mutableListOf<LibraryItem>(
        Book(id = 1, name = "Преступление и наказание", accessible = true, numberOfPages = 500, author = "Федор Достоевский"),
        Newspaper(id = 2, name = "Известия", accessible = true, issueNumber = 1525, monthOfPublication = Month.MAY),
        Disk(id = 3, name = "Брат", accessible = true, type = 1),
        Book(id = 4, name = "Война и мир", accessible = true, numberOfPages = 1200, author = "Лев Толстой"),
        Newspaper(id = 5, name = "Комсомольская правда", accessible = true, issueNumber = 1200, monthOfPublication = Month.JULY),
        Disk(id = 6, name = "Начало", accessible = true, type = 0),
        Book(id = 7, name = "Мастер и Маргарита", accessible = true, numberOfPages = 400, author = "Михаил Булгаков"),
        Disk(id = 8, name = "Зеркало", accessible = true, type = 1)
    )

    // Создаем объект класса LibraryManager, передавая туда список объектов
    val library = LibraryManager(libraryItems)

    // Создаем объект класса InterfaceManager, передавая туда объект типа LibraryManager
    val menu = InterfaceManager(library)
    menu.mainMenu()
}
