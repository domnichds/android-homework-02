package org.example

// Контрвариантный параметр, ограниченный сверху LibraryManager
class Digitizer<in T: LibraryItem> {
    fun digitalize(item: LibraryItem?) : Disk {
        // Проверяем на null из-за особенностей LibraryItem
        if (item != null) {
            return Disk(item.id + 100, item.name, true, 0)
        }
        else {
            return Disk(0, "N/A", true, 0)
        }
    }
}