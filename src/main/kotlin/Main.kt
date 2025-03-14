package org.example

abstract class LibraryItem(val id: Int, var accessible: Boolean)

class Book(id: Int, accessible: Boolean, var numberOfPages: Int, var author: String) : LibraryItem(id, accessible)
{

}

class Newspaper(id: Int, accessible: Boolean, var issueNumber : Int) : LibraryItem(id, accessible)
{

}

class Disk(id: Int, accessible: Boolean, var type: Int) : LibraryItem(id, accessible)
{

}

fun main()
{

}