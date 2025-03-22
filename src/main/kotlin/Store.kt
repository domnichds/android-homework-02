package org.example

// Интерфейс для любого магазина, который обязывает магазин иметь метод sell
interface Store<T> {
    fun sell(): T
}

class BookStore: Store<Book> {
    override fun sell(): Book {
        return Book(12, "465 градусов по Фаренгейту", true, 350, "Рэй Бредбери")
    }
}

class DiskStore: Store<Disk> {
    override fun sell(): Disk {
        return Disk(13, "Послезавтра", true, 1)
    }
}

class NewspaperStore: Store<Newspaper> {
    override fun sell(): Newspaper {
        return Newspaper(14, "Перекресток", true, 345, Month.AUGUST)
    }
}

class Manager {
    // Метод принимающий любой тип, ограниченные сверху LibraryItem
    fun <T: LibraryItem> buy(store: Store<T>) : T {
        return store.sell()
    }
}