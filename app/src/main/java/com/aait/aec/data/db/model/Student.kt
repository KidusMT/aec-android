package com.aait.aec.data.db.model

class Student {
    var row: Int? = null
    var name: String? = null
    var id: String? = null

    constructor(row: Int?, name: String?, id: String?) {
        this.row = row
        this.name = name
        this.id = id
    }
}