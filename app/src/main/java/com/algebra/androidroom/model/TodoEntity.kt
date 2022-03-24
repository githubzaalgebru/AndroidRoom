package com.algebra.androidroom.model

class TodoEntity(
    var id          : Int,
    var title       : String,
    var description : String
) {
    override fun toString(): String {
        return "{\"id\":$id, \"title\":\"$title\", \"description\":\"$description\" }"
    }
}