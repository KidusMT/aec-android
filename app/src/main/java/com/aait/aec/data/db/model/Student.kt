package com.aait.aec.data.db.model

class Student {
    var row: Int? = null
    var name: String? = null
    var id: String? = null
    var score: Double? = null

    constructor(row: Int?, name: String?, id: String?, score: Double) {
        this.row = row
        this.name = name
        this.id = id
        this.score = score
    }
}