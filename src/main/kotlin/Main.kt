package org.example
abstract class LibraryItem(var id : Int, var name : String, val accessible : Boolean)
{

}

class Book(id : Int, name : String, accessible: Boolean, var numberOfPages : Int, var author : String) : LibraryItem(id, name, accessible)
{

}

class Newspaper(id : Int, name : String, accessible: Boolean, var issueNumber : Int) : LibraryItem(id, name, accessible)
{

}

class Disk(id : Int, name : String, accessible: Boolean, var type : Int) : LibraryItem(id, name, accessible)
{
    
}
fun main() {

}

